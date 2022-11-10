package com.albusxing.dobby.excel;

import com.albusxing.dobby.service.UserService;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.enums.RowTypeEnum;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Albusxing
 * @created 2022/11/10
 */
@Slf4j
public class UserImportListener implements ReadListener<UserImportData> {

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    /**
     * 缓存的数据
     */
    private List<UserImportData> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    private UserService userService;
    private UserImportResult importResult;
    private AtomicInteger success = new AtomicInteger(0);

    public UserImportListener(UserService userService, UserImportResult importResult) {
        this.userService = userService;
        this.importResult = importResult;
    }


    @Override
    public void invoke(UserImportData data, AnalysisContext context) {
        if (Objects.isNull(importResult.getTotal())) {
            Integer totalRowNumber = context.readSheetHolder().getApproximateTotalRowNumber();
            importResult.setTotal(totalRowNumber-1);
        }
        context.readWorkbookHolder().setIgnoreEmptyRow(Boolean.FALSE);
        if (RowTypeEnum.EMPTY.equals(context.readRowHolder().getRowType())) {
            return;
        }
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        List<UserImportData> userWhitelistResults = validateData(data, context);
        if (CollectionUtils.isEmpty(userWhitelistResults)) {
            cachedDataList.add(data);
        } else {
            importResult.getFailList().addAll(userWhitelistResults);
        }
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    private List<UserImportData> validateData(UserImportData data, AnalysisContext context) {
        return null;

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        if (Objects.isNull(importResult.getTotal())) {
            Integer totalRowNumber = context.readSheetHolder().getApproximateTotalRowNumber();
            importResult.setTotal(totalRowNumber-1);
        }
        saveData();
        log.info("所有数据解析完成！");
    }

    @Override
    public boolean hasNext(AnalysisContext context) {
        if (RowTypeEnum.EMPTY.equals(context.readRowHolder().getRowType())) {
            doAfterAllAnalysed(context);
            return false;
        }
        return ReadListener.super.hasNext(context);
    }


    /**
     * 加上存储数据库
     */
    private void saveData() {
        success.addAndGet(cachedDataList.size());
        importResult.setSuccess(success.get());
        if (CollectionUtils.isEmpty(cachedDataList)) {
            return;
        }
        log.info("{}条数据，开始存储数据库！", cachedDataList.size());
        userService.batchSave(cachedDataList);
        log.info("存储数据库成功！");
    }
}
