package com.albusxing.dobby.common.constant;
/**
 * 通用返回码枚举
 * 0 成功
 * 1 失败
 * @author liguoqing
 */
public enum ResultCodeEnum {

    SUCCESS(0, "成功"),
    FAIL(1, "失败");

    private Integer code;
    private String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
