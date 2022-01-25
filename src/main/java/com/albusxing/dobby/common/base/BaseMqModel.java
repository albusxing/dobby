package com.albusxing.dobby.common.base;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liguoqing
 */
@Data
public class BaseMqModel<T> implements Serializable {

    private T data;
    private Boolean status;
    private String command;
    private String desc;
    private String indexNo;


}
