package com.albusxing.dobby.dto;

import com.albusxing.dobby.common.base.BaseQuery;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Albusxing
 * @created 2022/11/8
 */
@Data
public class UserQuery extends BaseQuery {

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("性别 0未知 1男 2女")
    private Integer gender;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("开始时间")
//    @JSONField(format="yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    private Date startDate;


    @ApiModelProperty("结束时间")
//    @JSONField(format="yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    private Date endDate;
}
