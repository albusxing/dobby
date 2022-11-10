package com.albusxing.dobby.web.api2;

import com.albusxing.dobby.core.annotation.AuthInfo;
import com.albusxing.dobby.core.annotation.RequestLog;
import com.albusxing.dobby.common.base.BasePage;
import com.albusxing.dobby.common.base.BaseResult;
import com.albusxing.dobby.domain.entity.AuthClient;
import com.albusxing.dobby.dto.UserResp;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author Albusxing
 * @created 2022/11/8
 */
@Slf4j
@Validated
@Api(tags = "第三方接口")
@RestController
@RequestMapping("/api/third")
@RequiredArgsConstructor
public class ThirdController {

    @ApiOperation(value = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(name = "username", value = "用户名"),
            @ApiImplicitParam(name = "pageNo", value = "当前页,从1开始", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", defaultValue = "10")
    })
    @GetMapping("/users")
    @RequestLog(func = "用户查询")
    public BaseResult<BasePage<UserResp>> list(@RequestParam(value = "username", required = false) String username,
                                               @RequestParam(value = "pageNo", defaultValue = "1") Long pageNo,
                                               @RequestParam(value = "pageSize", defaultValue = "10") Long pageSize,
                                               @ApiIgnore @AuthInfo AuthClient authClient) {
        log.info("用户管理 -> 查询，authClient={}", JSON.toJSONString(authClient));
        BaseResult<BasePage<UserResp>> baseResult = BaseResult.success();
        return baseResult;
    }
}
