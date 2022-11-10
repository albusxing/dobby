package com.albusxing.dobby.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liguoqing
 */
@Data
@ApiModel(description = "用户返回对象")
public class UserResp implements Serializable {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "性别 0未知 1男 2女")
    private Integer gender;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "手机")
    private String phone;
}
