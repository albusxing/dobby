package com.albusxing.dobby.core.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * 认证异常
 * @author liguoqing
 */
@Data
public class ThirdAuthException extends RuntimeException {

    private Integer code =  HttpStatus.UNAUTHORIZED.value();
    private String message;

    public ThirdAuthException(String message) {
        super(message);
        this.message = message;
    }


}
