package com.albusxing.dobby.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Albusxing
 * @created 2022/11/9
 */
@AllArgsConstructor
@Getter
public enum GenderEnum {

    /**
     * 0未知 1男 2女
     */
    UNKNOWN(0, "未知"),
    MALE(1, "男"),
    FEMALE(2, "女");

    private Integer code;
    private String desc;


    public static Map<Integer, String> toMap() {
        Map<Integer, String> map = new HashMap<>(16);
        for (GenderEnum element : GenderEnum.values()) {
            map.put(element.getCode(), element.getDesc());
        }
        return map;
    }

    public static GenderEnum getByCode(Integer code) {
        for (GenderEnum element : GenderEnum.values()) {
            if (code.equals(element.getCode())) {
                return element;
            }
        }
        return null;
    }

}
