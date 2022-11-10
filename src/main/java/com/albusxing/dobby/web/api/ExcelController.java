package com.albusxing.dobby.web.api;
import com.albusxing.dobby.common.base.BaseResult;
import com.albusxing.dobby.common.enums.ResultCodeEnum;
import com.albusxing.dobby.excel.UserImportResult;
import com.albusxing.dobby.service.UserExcelService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Albusxing
 * @creaed 2022/11/10
 */
@Slf4j
@Api(tags = "Excel")
@RestController
@RequiredArgsConstructor
public class ExcelController {

    private final UserExcelService userExcelService;

    @PostMapping("/excel/import")
    public BaseResult<UserImportResult> importData(@RequestParam(value = "file") MultipartFile file) {
        BaseResult<UserImportResult> baseResult = BaseResult.success();
        try {
            UserImportResult importResult = userExcelService.importData(file);
            return BaseResult.success(importResult);
        } catch (Exception e) {
            baseResult.setCode(ResultCodeEnum.FAIL.getCode());
            baseResult.setMessage(e.getMessage());
        }
        return baseResult;
    }
}
