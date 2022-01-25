package com.albusxing.dobby.common.base;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author liguoqing
 */
@Data
@Builder
public class BasePage<T> {

    /**
     * 总数
     */
    private Long total;

    /**
     * 页码
     */
    private long pageNo;

    /**
     * 每页大小
     */
    private long pageSize;

    /**
     * 结果数据
     */
    private List<T> data;

    public static <T> BasePage<T> init(List dataList, long count, long pageNo, long pageSize) {
        return BasePage.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .total(count)
                .data(dataList).build();
    }

}
