package com.albusxing.dobby.common.constant;
/**
 * 数据状态
 * 0 正常 1删除
 * @author liguoqing
 */
public enum DataStatusEnum {

    /**
     * 标记数据行记录是否为删除的
     */
    NORMAL(0, "正常"),
    DELETED(1, "删除");

    private Integer code;
    private String message;

    DataStatusEnum(Integer code, String message) {
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
