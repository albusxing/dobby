package com.albusxing.dobby.domain.entity;

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
    private String requestMethod;

    /**
     * 请求地址
     */
    private String requestUrl;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 请求时间
     */
    private LocalDateTime requestTime;

    /**
     * 请求时长
     */
    private Long requestDuration;

    /**
     * 执行结果：1成功 0失败
     */
    private Integer executeResult;

    /**
     * 执行返回信息
     */
    private String executeMessage;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 更新时间
     */
    private LocalDateTime updateDate;


}
