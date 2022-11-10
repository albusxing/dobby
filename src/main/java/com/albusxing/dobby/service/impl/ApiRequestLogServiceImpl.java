package com.albusxing.dobby.service.impl;


import com.albusxing.dobby.domain.entity.ApiRequestLog;
import com.albusxing.dobby.domain.mapper.ApiRequestLogMapper;
import com.albusxing.dobby.service.ApiRequestLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * api接口请求日志 服务实现类
 * @author lgq
 * @since 2020-12-07
 */
@Service
public class ApiRequestLogServiceImpl extends ServiceImpl<ApiRequestLogMapper, ApiRequestLog> implements ApiRequestLogService {

    @Async("asyncExecutor")
    @Override
    public void saveLog(ApiRequestLog apiRequestLog) {
        this.save(apiRequestLog);
    }
}
