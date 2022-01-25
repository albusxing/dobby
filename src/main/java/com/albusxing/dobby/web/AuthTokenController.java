package com.albusxing.dobby.web;

import com.albusxing.dobby.common.base.BaseResult;
import com.albusxing.dobby.common.constant.ResultCodeEnum;
import com.albusxing.dobby.dto.TokenInfo;
import com.albusxing.dobby.service.AuthClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * @author liguoqing
 */
@Slf4j
@Api(tags = "认证授权")
@RestController
@Validated
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthTokenController {

    private final AuthClientService authClientService;


    @ApiOperation( value = "获取token")
    @GetMapping("/token")
    public BaseResult<TokenInfo> token(@NotBlank(message = "code不能为空") @RequestParam("code") String code,
                                       @NotBlank(message = "secret不能为空") @RequestParam("secret") String secret){
        BaseResult<TokenInfo> result = BaseResult.success();
        try {
            TokenInfo tokenInfo = authClientService.genToken(code, secret);
            return BaseResult.success(tokenInfo);
        } catch (Exception e) {
            result.setCode(ResultCodeEnum.FAIL.getCode());
            result.setMessage(e.getMessage());
        }
        return result;
    }
}
