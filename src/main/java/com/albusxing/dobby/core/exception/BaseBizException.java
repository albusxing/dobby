package com.albusxing.dobby.core.exception;

import lombok.Data;

import java.text.MessageFormat;

/**
 * @author Albusxing
 * @created 2022/8/15
 */
@Data
public class BaseBizException extends RuntimeException {

    private Integer errorCode;

    private String errorMsg;


    public BaseBizException(Integer errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }


    public BaseBizException(Integer errorCode, String errorMsg, Object... arguments) {
        super(MessageFormat.format(errorMsg, arguments));
        this.errorCode = errorCode;
        this.errorMsg = MessageFormat.format(errorMsg, arguments);
    }

}

