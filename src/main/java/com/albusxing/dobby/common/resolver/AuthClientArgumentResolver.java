package com.albusxing.dobby.common.resolver;

import com.albusxing.dobby.common.annotation.AuthInfo;
import com.albusxing.dobby.common.exception.AuthException;
import com.albusxing.dobby.entity.AuthClient;
import com.albusxing.dobby.service.AuthClientService;
import com.albusxing.dobby.util.JwtTokenUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 给有 @AuthClient 注解的方法，注册入当前授权信息
 * @author Albusxing
 */
@Component
public class AuthClientArgumentResolver implements HandlerMethodArgumentResolver {

    @Resource
    private AuthClientService authClientService;
    @Resource
    private JwtTokenUtil jwtUtil;

    // 授权信息本地缓存
    private static final Cache<String, AuthClient> AUTH_SECRET_CACHE = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .concurrencyLevel(100)
            .build();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthInfo.class) && parameter.getParameterType().isAssignableFrom(AuthClient.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        Object attribute = webRequest.getAttribute(jwtUtil.getAuthKey(), RequestAttributes.SCOPE_REQUEST);
        if (Objects.isNull(attribute)) {
            return null;
        }
        String client = ((String) attribute);
        AuthClient cacheIfPresent = AUTH_SECRET_CACHE.getIfPresent(client);
        if (Objects.isNull(cacheIfPresent)) {
            AuthClient authSecret = authClientService.findByClient(client);
            if (Objects.isNull(authSecret)) {
                throw new AuthException(jwtUtil.getHeaderName() + "已经失效，请重新申请");
            }
            AUTH_SECRET_CACHE.put(client, authSecret);
            return authSecret;
        }
        return cacheIfPresent;
    }
}
