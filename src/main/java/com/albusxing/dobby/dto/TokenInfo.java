package com.albusxing.dobby.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@ApiModel(description = "Token信息")
public class TokenInfo implements Serializable {

    @ApiModelProperty(value = "token")
    private String token;
    @ApiModelProperty(value = "失效时间")
    private Long expiration;
}
