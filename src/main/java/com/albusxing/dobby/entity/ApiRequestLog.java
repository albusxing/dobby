package com.albusxing.dobby.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * api接口请求日志
 * @author lgq
 * @since 2020-12-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_api_request_log")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiRequestLog implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 功能
     */
    private String func;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 请求地址
     */
    private String reqUrl;

    /**
     * 请求参数
     */
    private String reqParams;

    /**
     * 请求时间
     */
    private LocalDateTime reqDate;

    private Long reqDuration;

    /**
     * 执行结果：1成功 0失败
     */
    private Integer execResult;

    /**
     * 执行返回信息
     */
    private String execMessage;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 更新时间
     */
    private LocalDateTime updateDate;


}
