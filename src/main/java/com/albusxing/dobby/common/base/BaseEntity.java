package com.albusxing.dobby.common.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础公共实体对象
 * @author liguoqing
 */
@Data
public class BaseEntity implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    protected Integer status = 0;
    protected LocalDateTime createTime = LocalDateTime.now();
    protected LocalDateTime updateTime = LocalDateTime.now();
}
