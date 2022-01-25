package com.albusxing.dobby.common.aspect;


import com.albusxing.dobby.common.annotation.RequestLog;
import com.albusxing.dobby.common.base.BaseResult;
import com.albusxing.dobby.common.constant.ResultCodeEnum;
import com.albusxing.dobby.entity.ApiRequestLog;
import com.albusxing.dobby.service.ApiRequestLogService;
import com.albusxing.dobby.util.ServletUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * @author liguoqing
 */
@Slf4j
@Component
@Aspect
public class RequestLogAspect {


    @Resource
    private ApiRequestLogService apiRequestLogService;

    @Pointcut("@annotation(com.albusxing.dobby.common.annotation.RequestLog)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long time = System.currentTimeMillis() - beginTime;
        handleLog(joinPoint, result, time);
        return result;

    }

    private void handleLog(ProceedingJoinPoint joinPoint, Object result, Long time) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        HttpServletRequest request = ServletUtil.getRequest();
        String requestURI = request.getRequestURI();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = method.getAnnotation(RequestLog.class);
        String func = requestLog.func();
        ApiRequestLog log = ApiRequestLog.builder()
                .func(func)
                .methodName(method.getName())
                .reqUrl(requestURI)
                .reqParams(JSON.toJSONString(args[0]))
                .reqDate(LocalDateTime.now())
                .reqDuration(time)
                .build();
        if (result instanceof BaseResult) {
              BaseResult baseResult = (BaseResult) result;
              if (baseResult.getCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
                log.setExecResult(0);
              } else {
                log.setExecResult(1);
              }
        }else{
            log.setExecResult(0);
        }
        log.setExecMessage(JSON.toJSONString(result));
        apiRequestLogService.saveLog(log);
    }

}
