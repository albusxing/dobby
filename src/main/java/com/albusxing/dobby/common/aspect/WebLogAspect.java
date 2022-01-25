package com.albusxing.dobby.common.aspect;


import com.albusxing.dobby.util.LogUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * 接口调用日志切面
 * @author liguoqing
 */
@Slf4j
@Aspect
@Component
public class WebLogAspect {

    /**
     * 配置织入点
     */
    @Pointcut("execution(* com.albusxing.dobby.web..*.*(..))")
    public void controllerPointCut() {
    }

    @Before(value = "controllerPointCut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        handleLog(joinPoint, null, null);
    }


    /**
     * 处理完请求后执行
     */
    @AfterReturning(pointcut = "controllerPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
        handleLog(joinPoint, null, jsonResult);
    }

    /**
     * 拦截异常操作
     */
    @AfterThrowing(value = "controllerPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ApiOperation syslogAnno = method.getAnnotation(ApiOperation.class);
        String methodDesc = "";
        if (syslogAnno != null) {
            // 注解上的描述
            methodDesc = syslogAnno.value();
        }
        // 请求的方法
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        String requestMethod = className + "." + methodName + "()";
        //
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (null == attributes) {
            return;
        }
        ServletRequestAttributes request = (ServletRequestAttributes) attributes;
        String requestURL = request.getRequest().getRequestURL().toString();
        String requestParams = getMethodRequestParams(joinPoint);
        Object parameter = getParameter(method, joinPoint.getArgs());
        String returnInfo = JSON.toJSONString(jsonResult);
        String exceptionInfo = Objects.nonNull(e) ? e.getMessage() : null;
        LogUtil.report(requestURL, requestMethod, methodDesc, requestParams, returnInfo, exceptionInfo);
    }


    private String getMethodRequestParams(JoinPoint joinPoint) {
        List<Object> params = Lists.newArrayList();
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (!isFilterObject(arg)) {
                params.add(arg);
            }
        }
        return StringUtils.substring(JSON.toJSONString(params), 0, 500);
    }

    public boolean isFilterObject(final Object o) {
        return o instanceof MultipartFile || o instanceof HttpServletRequest
                || o instanceof HttpServletResponse || o instanceof BindingResult;
    }

    /**
     * 根据方法和传入的参数获取请求参数
     */
    private Object getParameter(Method method, Object[] args) {
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
                String key = parameters[i].getName();
                if (!StringUtils.isEmpty(requestParam.value())) {
                    key = requestParam.value();
                }
                map.put(key, args[i]);
                argList.add(map);
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

}
