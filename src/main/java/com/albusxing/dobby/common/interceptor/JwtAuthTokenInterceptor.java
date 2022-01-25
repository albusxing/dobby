package com.albusxing.dobby.common.interceptor;


import com.albusxing.dobby.common.exception.AuthException;
import com.albusxing.dobby.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liguoqing
 */
@Component
public class JwtAuthTokenInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private JwtTokenUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取token
        String token = request.getHeader(jwtUtil.getHeaderName());
        //token为空
        if(StringUtils.isBlank(token)){
            throw new AuthException(jwtUtil.getHeaderName() + "不能为空");
        }
         Claims claims = jwtUtil.getClaimsFromToken(token);
        if (null == claims || jwtUtil.isTokenExpired(claims.getExpiration())) {
            throw new AuthException(jwtUtil.getHeaderName() + "已经失效，请重新申请");
        }
        // 将授权信息放入请求中
        request.setAttribute(jwtUtil.getAuthKey(), claims.getSubject());
        return true;
    }
}
