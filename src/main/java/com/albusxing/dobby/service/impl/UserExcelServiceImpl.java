package com.albusxing.dobby.service.impl;

import com.albusxing.dobby.core.exception.BaseBizException;
import com.albusxing.dobby.domain.entity.User;
import com.albusxing.dobby.excel.UserExportData;
import com.albusxing.dobby.excel.UserImportData;
import com.albusxing.dobby.excel.UserImportListener;
import com.albusxing.dobby.excel.UserImportResult;
import com.albusxing.dobby.service.UserExcelService;
import com.albusxing.dobby.service.UserService;
import com.albusxing.dobby.service.converter.UserConverter;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @author Albusxing
 * @created 2022/11/10
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserExcelServiceImpl implements UserExcelService {

    private final UserService userService;
    private final UserConverter userConverter;

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
    public void exportData(HttpServletResponse response) {
        try {
            String fileName = "用户数据";
            String sheetName = "用户数据";

            String enFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-Disposition", "attachment;filename=" + enFileName + ".xlsx");
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");

            List<UserExportData> dataList = this.getExportData();
            EasyExcel.write(response.getOutputStream(), UserExportData.class)
                    .autoCloseStream(Boolean.FALSE)
                    .sheet(sheetName)
                    .doWrite(dataList);
        } catch (Exception e) {
            log.error("下载文件异常",e);
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            throw new BaseBizException("导出excel异常");
        }
    }

    private List<UserExportData> getExportData() {
        List<User> users = userService.listAll();
        return userConverter.toExportData(users);
    }

}
