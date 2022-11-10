package com.albusxing.dobby.service.impl;

import com.albusxing.dobby.core.exception.BaseBizException;
import com.albusxing.dobby.excel.UserImportData;
import com.albusxing.dobby.excel.UserImportListener;
import com.albusxing.dobby.excel.UserImportResult;
import com.albusxing.dobby.service.UserExcelService;
import com.albusxing.dobby.service.UserService;
import com.alibaba.excel.EasyExcel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Albusxing
 * @created 2022/11/10
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserExcelServiceImpl implements UserExcelService {

    private final UserService userService;
    @Override
    public UserImportResult importData(MultipartFile file) {
        UserImportResult importResult = new UserImportResult();
        try {
            EasyExcel.read(file.getInputStream(), UserImportData.class,
                            new UserImportListener(userService, importResult)).sheet().doRead();
            log.info("解析导入完成");
        } catch (Exception e) {
            log.error("出现异常", e);
            throw new BaseBizException("导入excel异常");
        }
        return importResult;
    }

    @Override
    public void exportData() {

    }
}
