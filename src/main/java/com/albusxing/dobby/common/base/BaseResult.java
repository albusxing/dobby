package com.albusxing.dobby.common.base;


import com.albusxing.dobby.common.constant.ResultCodeEnum;
import lombok.Data;

/**
 * 通用返回结构
 * @param <T>
 */
@Data
public class BaseResult<T> {

    private Integer code;
    private String message;
    private T data;

    public BaseResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> BaseResult<T> success() {
        return new BaseResult<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage());
    }

    public static <T> BaseResult<T> success(T data) {
        return new BaseResult<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), data);
    }

    public static <T> BaseResult<T> success(T data, String message) {
        return new BaseResult<>(ResultCodeEnum.SUCCESS.getCode(), message, data);
    }

    public static <T> BaseResult<T> fail() {
        return new BaseResult<>(ResultCodeEnum.FAIL.getCode(), ResultCodeEnum.FAIL.getMessage());
    }

    public static <T> BaseResult<T> fail(String message) {
        return new BaseResult<>(ResultCodeEnum.FAIL.getCode(), message);
    }

    public static <T> BaseResult<T> fail(Integer code, String message) {
        return new BaseResult<>(code, message);
    }

}
