package com.albusxing.dobby.core.aspect;


import com.albusxing.dobby.common.util.LogUtil;
import com.albusxing.dobby.core.annotation.RequestLog;
import com.albusxing.dobby.common.base.BaseResult;
import com.albusxing.dobby.common.enums.ResultCodeEnum;
import com.albusxing.dobby.domain.entity.ApiRequestLog;
import com.albusxing.dobby.service.ApiRequestLogService;
import com.albusxing.dobby.common.util.ServletUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author liguoqing
 */
@Slf4j
@Component
@Aspect
public class RequestLogAspect {

    @Resource
    private ApiRequestLogService apiRequestLogService;

    @Pointcut("@annotation(com.albusxing.dobby.core.annotation.RequestLog)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("---------------- 进入Around通知 ----------------");
        long beginTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long time = System.currentTimeMillis() - beginTime;
        handleAroundLog(joinPoint, result, time, null);
        return result;

    }

    /**
     * 根据方法和传入的参数获取请求参数
     */
    private Object getRequestParams(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                argList.add(args[i]);
            }
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (requestParam != null) {
                Map<String, Object> map = new HashMap<>();
                // 默认arg0、arg1
                String key = parameters[i].getName();
                if (StringUtils.isNotBlank(requestParam.value())) {
                    key = requestParam.value();
                }
                map.put(key, args[i]);
                argList.add(map);
            }
            PathVariable pathVariable = parameters[i].getAnnotation(PathVariable.class);
            if (pathVariable != null) {
                Map<String, Object> map = new HashMap<>();
                // 默认arg0、arg1
                String key = parameters[i].getName();
                if (StringUtils.isNotBlank(pathVariable.value())) {
                    key = pathVariable.value();
                }
                map.put(key, args[i]);
                argList.add(map);
            }
            // 处理请求参数是对象，并且没有添加注解的参数
            if (CollectionUtils.isEmpty(argList)) {
                argList.add(args[i]);
            }
        }
        if (argList.size() == 0) {
            return null;
        } else if (argList.size() == 1) {
            return argList.get(0);
        } else {
            return argList;
        }
    }


    private String getRequestUrl() {
        HttpServletRequest request = ServletUtil.getRequest();
        return request.getRequestURL().toString();
    }

    private String getRequestMethod(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        return className + "." + methodName + "()";
    }

    private void handleAroundLog(JoinPoint joinPoint, Object result, Long time, Exception e) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        // 请求url
        String requestUrl = this.getRequestUrl();
        // 请求方法
        String requestMethod = this.getRequestMethod(joinPoint);
        // 请求参数
        Object requestParams = this.getRequestParams(method, joinPoint.getArgs());
        // 获取注解
        RequestLog requestLog = method.getAnnotation(RequestLog.class);
        String func = requestLog.func();
        // 持久化
        boolean durable = requestLog.durable();
        if (durable) {
            ApiRequestLog.ApiRequestLogBuilder logBuilder = ApiRequestLog.builder()
                    .func(func)
                    .requestMethod(requestMethod)
                    .requestUrl(requestUrl)
                    .requestParams(JSON.toJSONString(requestParams))
                    .requestTime(LocalDateTime.now())
                    .requestDuration(time);
            if (result instanceof BaseResult) {
                BaseResult<?> baseResult = (BaseResult<?>) result;
                logBuilder.executeResult(baseResult.getCode());
            } else {
                // 非指定返回类型，默认成功
                logBuilder.executeResult(ResultCodeEnum.SUCCESS.getCode());
            }
            logBuilder.executeMessage(JSON.toJSONString(result));
            apiRequestLogService.saveLog(logBuilder.build());
        }
        String exceptionInfo = Objects.nonNull(e) ? e.getMessage() : null;
        // 打印控制台
        boolean console = requestLog.console();
        if (console) {
            LogUtil.report(requestUrl, requestMethod, func, JSON.toJSONString(requestParams), JSON.toJSONString(result), exceptionInfo);
        }
    }

}
