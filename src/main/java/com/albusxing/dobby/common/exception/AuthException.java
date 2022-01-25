package com.albusxing.dobby.common.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * 认证异常
 * @author liguoqing
 */
@Data
public class AuthException extends RuntimeException {

    private Integer code =  HttpStatus.UNAUTHORIZED.value();
    private String message;

    public AuthException(String message) {
        super(message);
        this.message = message;
    }


}
