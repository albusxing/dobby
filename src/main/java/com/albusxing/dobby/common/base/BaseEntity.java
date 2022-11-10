package com.albusxing.dobby.common.base;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 基础公共实体对象
 * @author liguoqing
 */
@Data
public class BaseEntity implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableLogic
    protected Integer status = 0;

    @TableField(fill = FieldFill.INSERT)
    protected Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected Date updateTime;
}
