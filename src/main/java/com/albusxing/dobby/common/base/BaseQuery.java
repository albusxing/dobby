package com.albusxing.dobby.common.base;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Albusxing
 * @created 2022/11/8
 */
@Data
public class BaseQuery implements Serializable {

    private String keyword;
    private Long pageNo = 1L;
    private Long pageSize = 10L;
}
