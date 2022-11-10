package com.albusxing.dobby.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(description = "用户命令对象")
public class UserCmd implements Serializable {

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "性别 0未知 1男 2女")
    @NotNull(message = "性别不能为空")
    private Integer gender;

    @ApiModelProperty(value = "年龄")
    @NotNull(message = "年龄不能为空")
    @Range(min = 1, max = 100, message = "年龄在1~100之内")
    private Integer age;

    @ApiModelProperty(value = "手机")
    @NotBlank(message = "手机不能为空")
    private String phone;

    @ApiModelProperty(value = "地址")
    private String address;


}
