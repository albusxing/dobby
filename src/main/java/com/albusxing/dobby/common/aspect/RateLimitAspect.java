package com.albusxing.dobby.common.aspect;

import cn.hutool.crypto.digest.MD5;
import com.albusxing.dobby.common.annotation.RateLimit;
import com.albusxing.dobby.common.base.BaseResult;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author liguoqing
 */
@Slf4j
@Component
@Aspect
public class RateLimitAspect {

    private Cache<String, RateLimiter> rateLimiterCache = CacheBuilder.newBuilder()
            .expireAfterWrite(3, TimeUnit.MINUTES)
            .build();


    @Pointcut("@annotation(com.albusxing.dobby.common.annotation.RateLimit)")
    public void pointcut() {
    }


    @Around("pointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        RateLimit annotation = method.getDeclaredAnnotation(RateLimit.class);
        double limit = annotation.value();
        if (limit <= 0) {
            return joinPoint.proceed();
        }
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String requestURI = request.getRequestURI();
        String userToken = request.getHeader("token");
        String uniqueKey = getUniqueKey(requestURI, userToken);
        RateLimiter rateLimiter = rateLimiterCache.getIfPresent(uniqueKey);
        if (Objects.isNull(rateLimiter)) {
            rateLimiter = RateLimiter.create(limit);
            rateLimiterCache.put(uniqueKey,  rateLimiter);
        }
        if (rateLimiter.tryAcquire()) {
            log.info("==> success get acquire at:" + LocalDateTime.now().toString());
            return joinPoint.proceed();
        }
        log.info("==> error get acquire at:" + LocalDateTime.now().toString());
        return BaseResult.fail("请勿点击过快");
    }


    public String getUniqueKey(String url, String token) {
        return  new MD5().digestHex(url + token, "UTF-8");
    }

}
