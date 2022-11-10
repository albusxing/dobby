package com.albusxing.dobby.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.alibaba.excel.enums.BooleanEnum;
import com.alibaba.excel.enums.poi.BorderStyleEnum;
import com.alibaba.excel.enums.poi.FillPatternTypeEnum;
import lombok.Data;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.io.Serializable;

/**
 * @author Albusxing
 * @created 2022/11/10
 */
@Data
@ColumnWidth(16)
@HeadFontStyle(bold = BooleanEnum.FALSE)
@HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 4)
public class UserExportData implements Serializable {

    @ColumnWidth(10)
    @ExcelProperty("用户名")
    private String username;

    @ExcelProperty("性别")
    private Integer gender;

    @ExcelProperty("年龄")
    private Integer age;

    @ContentStyle(borderLeft = BorderStyleEnum.THIN, borderRight= BorderStyleEnum.THIN, borderTop= BorderStyleEnum.THIN, borderBottom= BorderStyleEnum.THIN)
    @ExcelProperty("手机")
    private String phone;

    @ExcelProperty("地址")
    private String address;
}
