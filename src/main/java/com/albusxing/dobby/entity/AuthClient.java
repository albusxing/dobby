package com.albusxing.dobby.entity;


import com.albusxing.dobby.common.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author liguoqing
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_auth_client")
public class AuthClient extends BaseEntity {

    private String client;
    private String secret;
    private Long expiration;

}
