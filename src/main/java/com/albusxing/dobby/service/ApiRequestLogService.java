package com.albusxing.dobby.service;


import com.albusxing.dobby.domain.entity.ApiRequestLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * api接口请求日志 服务类
 * @author lgq
 * @since 2020-12-07
 */
public interface ApiRequestLogService extends IService<ApiRequestLog> {

    void saveLog(ApiRequestLog apiRequestLog);

}
