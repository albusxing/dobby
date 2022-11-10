package com.albusxing.dobby.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * JwtToken生成的工具类
 * JWT token的格式：header.payload.signature
 * header的格式（算法、token的类型）：
 * {"alg": "HS512","typ": "JWT"}
 * payload的格式（用户名、创建时间、生成时间）：
 * {"sub":"wang","created":1489079981393,"exp":1489684781}
 * signature的生成算法：
 * HMACSHA256(base64UrlEncode(header) + "." +base64UrlEncode(payload),secret)
 * @author liguoqing
 */
@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "jwt.token")
public class JwtTokenUtil {

    /** 颁发人 */
    private String issuer;
    /** 授权标识key */
    private String authKey;
    /** 授权参数名称 */
    private String headerName;
    /** 密钥key */
    private String secretKey;


    /**
     * 生成JWT的token
     */
    public String generateToken(String client, Long expiration) {
        Date expireDate = new Date(System.currentTimeMillis() + expiration * 1000);
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        SecretKey secretKey = Keys.hmacShaKeyFor(keyBytes);
        return Jwts.builder()
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .setSubject(client)
                .setExpiration(expireDate)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * 从token中获取JWT中的负载
     */
    public Claims getClaimsFromToken(String token) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        SecretKey secretKey = Keys.hmacShaKeyFor(keyBytes);
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("JWT格式验证失败:{}", token, e);
        }
        return null;
    }


    /**
     * token是否过期
     * @return  true：过期
     */
    public boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }

}
