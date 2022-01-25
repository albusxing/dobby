package com.albusxing.dobby.config;

import com.albusxing.dobby.common.interceptor.JwtAuthTokenInterceptor;
import com.albusxing.dobby.common.resolver.AuthClientArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;

/**
 * MVC 配置
 * @author liguoqing
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private JwtAuthTokenInterceptor jwtAuthTokenInterceptor;
    @Resource
    private AuthClientArgumentResolver authClientArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtAuthTokenInterceptor)
                .addPathPatterns("/api/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(authClientArgumentResolver);
    }
}