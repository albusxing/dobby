package com.albusxing.dobby.excel;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;


/**
 * @author Albusxing
 * @created 2022/11/10
 */
@Data
public class UserImportResult {

    private Integer total;
    private Integer success;
    private Integer fail;
    private List<UserImportData> failList = Lists.newArrayList();

    public Integer getFail() {
        return failList.size();
    }
}
