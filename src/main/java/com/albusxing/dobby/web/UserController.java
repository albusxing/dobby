package com.albusxing.dobby.web;

import com.albusxing.dobby.common.annotation.AuthInfo;
import com.albusxing.dobby.common.annotation.RequestLog;
import com.albusxing.dobby.common.base.BasePage;
import com.albusxing.dobby.common.base.BaseResult;
import com.albusxing.dobby.common.constant.DataStatusEnum;
import com.albusxing.dobby.common.constant.ResultCodeEnum;
import com.albusxing.dobby.dto.UserReq;
import com.albusxing.dobby.dto.UserResp;
import com.albusxing.dobby.entity.AuthClient;
import com.albusxing.dobby.entity.User;
import com.albusxing.dobby.service.UserService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * @author liguoqing
 */
@Slf4j
@Validated
@Api(tags = "用户管理")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    /**
     *  @Validated 和 @Valid 有什么区别？
     * @param userReq
     * @return
     */
    @ApiOperation(value = "新增")
    @ApiImplicitParam(paramType = "header", name = "token", dataType = "String", required = true, value = "token")
    @PostMapping("/users")
    @RequestLog(func = "用户新增")
    public BaseResult<Void> save(@Valid @RequestBody UserReq userReq,
                                 @ApiIgnore @AuthInfo AuthClient authClient) {
        log.info("用户管理 -> 新增，authClient={}", JSON.toJSONString(authClient));
        BaseResult<Void> baseResult = BaseResult.success();
        try {
            userService.save(userReq);
            return BaseResult.success();
        } catch (Exception e) {
            baseResult.setCode(ResultCodeEnum.FAIL.getCode());
            baseResult.setMessage(e.getMessage());
        }
        return baseResult;
    }


    @ApiOperation(value = "修改")
    @ApiImplicitParam(paramType = "header", name = "token", dataType = "String", required = true, value = "token")
    @PutMapping("/users/{userId}")
    @RequestLog(func = "用户修改")
    public BaseResult<Void> update(@Valid @NotNull(message = "用户id不能为空") @PathVariable("userId") Long userId,
                                   @Valid @RequestBody UserReq userReq,
                                   @ApiIgnore @AuthInfo AuthClient authClient) {
        log.info("用户管理 -> 修改，authClient={}", JSON.toJSONString(authClient));
        BaseResult<Void> baseResult = BaseResult.success();
        try {
            userService.update(userId, userReq);
            return BaseResult.success();
        } catch (Exception e) {
            baseResult.setCode(ResultCodeEnum.FAIL.getCode());
            baseResult.setMessage(e.getMessage());
        }
        return baseResult;
    }

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
        try {
            Page<User> page = new Page<>(pageNo, pageSize);
            List<UserResp> respList = userService.list(username, page);
            return BaseResult.success(BasePage.init(respList, page.getTotal(), page.getCurrent(), page.getSize()));
        } catch (Exception e) {
            baseResult.setCode(ResultCodeEnum.FAIL.getCode());
            baseResult.setMessage(e.getMessage());
        }
        return baseResult;
    }

    @ApiOperation(value = "详情")
    @ApiImplicitParam(paramType = "header", name = "token", dataType = "String", required = true, value = "token")
    @GetMapping("/users/{userId}")
    @RequestLog(func = "用户详情")
    public BaseResult<UserResp> get(@Valid @NotNull(message = "id不能为空") @PathVariable("userId") Long userId,
                                @ApiIgnore @AuthInfo AuthClient authClient) {
        log.info("用户管理 -> 详情，authClient={}", JSON.toJSONString(authClient));
        BaseResult<UserResp> baseResult = BaseResult.success();
        try {
            UserResp user = userService.getUserResp(userId);
            return BaseResult.success(user);
        } catch (Exception e) {
            baseResult.setCode(ResultCodeEnum.FAIL.getCode());
            baseResult.setMessage(e.getMessage());
        }
        return baseResult;
    }


    @ApiOperation(value = "删除")
    @ApiImplicitParam(paramType = "header", name = "token", dataType = "String", required = true, value = "token")
    @DeleteMapping("/users/{userId}")
    @RequestLog(func = "用户删除")
    public BaseResult<Void> delete(@Valid @NotNull(message = "id不能为空") @PathVariable("userId") Long userId,
                                   @ApiIgnore @AuthInfo AuthClient authClient) {
        log.info("用户管理 -> 删除，authClient={}", JSON.toJSONString(authClient));
        BaseResult<Void> baseResult = BaseResult.success();
        try {
            UpdateWrapper<User> updateWrapper = new UpdateWrapper<User>()
                    .set("status", DataStatusEnum.DELETED.getCode())
                    .eq("id", userId);
            userService.update(updateWrapper);
            return BaseResult.success();
        } catch (Exception e) {
            baseResult.setCode(ResultCodeEnum.FAIL.getCode());
            baseResult.setMessage(e.getMessage());
        }
        return baseResult;
    }


}
