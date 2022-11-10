package com.albusxing.dobby.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Albusxing
 * @created 2022/11/10
 */
@Data
public class UserImportData implements Serializable {

    @ExcelProperty("用户名")
    private String username;

    @ExcelProperty("性别")
    private Integer gender;

    @ExcelProperty("年龄")
    private Integer age;

    @ExcelProperty("手机")
    private String phone;

    @ExcelProperty("地址")
    private String address;
}
