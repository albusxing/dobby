package com.albusxing.dobby.service;

import com.albusxing.dobby.excel.UserImportResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Albusxing
 * @created 2022/11/10
 */
public interface UserExcelService {

    UserImportResult importData(MultipartFile file);

    void exportData(HttpServletResponse response);
}
