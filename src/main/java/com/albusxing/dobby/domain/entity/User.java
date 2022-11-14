package com.albusxing.dobby.domain.entity;

import com.albusxing.dobby.common.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

/**
 * 用户信息
 * @author liguoqing
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_user")
public class User extends BaseEntity {

    private String username;
    private String password;
    private Integer gender;
    private Integer age;
    private String mobile;
    private String address;
    private String email;
    private Date registerTime;
    

}

