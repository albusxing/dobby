package com.albusxing.dobby.web.api;
import com.albusxing.dobby.core.annotation.AuthInfo;
import com.albusxing.dobby.core.annotation.RequestLog;
import com.albusxing.dobby.common.base.BasePage;
import com.albusxing.dobby.common.base.BaseResult;
import com.albusxing.dobby.common.enums.ResultCodeEnum;
import com.albusxing.dobby.domain.entity.AuthClient;
import com.albusxing.dobby.dto.UserCmd;
import com.albusxing.dobby.dto.UserQuery;
import com.albusxing.dobby.dto.UserResp;
import com.albusxing.dobby.domain.entity.User;
import com.albusxing.dobby.service.UserService;
import com.alibaba.fastjson.JSON;
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


    @ApiOperation(value = "用户新增")
    @PostMapping("/users")
    public BaseResult<Void> save(@Valid @RequestBody UserCmd userCmd) {
        log.info("用户管理 -> 新增，userCmd={}", JSON.toJSONString(userCmd));
        BaseResult<Void> baseResult = BaseResult.success();
        try {
            userService.save(userCmd);
            return BaseResult.success();
        } catch (Exception e) {
            baseResult.setCode(ResultCodeEnum.FAIL.getCode());
            baseResult.setMessage(e.getMessage());
        }
        return baseResult;
    }


    @RequestLog(func = "用户修改", durable = true, console = true)
    @ApiOperation(value = "用户修改")
    @PutMapping("/users/{userId}")
    public BaseResult<Void> update(@NotNull(message = "用户id不能为空") @PathVariable("userId") Long userId,
                                   @Valid @RequestBody UserCmd userCmd) {
        log.info("用户管理 -> 修改，userCmd={}", JSON.toJSONString(userCmd));
        BaseResult<Void> baseResult = BaseResult.success();
        try {
            userService.update(userId, userCmd);
            return BaseResult.success();
        } catch (Exception e) {
            baseResult.setCode(ResultCodeEnum.FAIL.getCode());
            baseResult.setMessage(e.getMessage());
        }
        return baseResult;
    }

    @RequestLog(func = "用户查询", durable = true, console = true)
    @ApiOperation(value = "用户查询")
    @GetMapping("/users")
    public BaseResult<BasePage<UserResp>> list(@Valid UserQuery userQuery) {
        log.info("用户管理 -> 查询，userQuery={}", JSON.toJSONString(userQuery));
        BaseResult<BasePage<UserResp>> baseResult = BaseResult.success();
        try {
            Page<User> page = new Page<>(userQuery.getPageNo(), userQuery.getPageSize());
            List<UserResp> respList = userService.list(userQuery, page);
            return BaseResult.success(BasePage.init(respList, page.getTotal(), page.getCurrent(), page.getSize()));
        } catch (Exception e) {
            baseResult.setCode(ResultCodeEnum.FAIL.getCode());
            baseResult.setMessage(e.getMessage());
        }
        return baseResult;
    }

    @RequestLog(func = "用户查询V2", durable = true, console = true)
    @ApiOperation(value = "用户查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字"),
            @ApiImplicitParam(name = "pageNo", value = "当前页,从1开始", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", defaultValue = "10")
    })
    @GetMapping("/users/v2")
    public BaseResult<BasePage<UserResp>> listV2(@RequestParam(value = "keyword", required = false) String keyword,
                                                 @RequestParam(value = "pageNo", defaultValue = "1") Long pageNo,
                                                 @RequestParam(value = "pageSize", defaultValue = "10") Long pageSize) {
        log.info("用户管理 -> 查询，keyword={}, pageNo={}, pageSize={}", keyword, pageNo, pageSize);
        BaseResult<BasePage<UserResp>> baseResult = BaseResult.success();
        try {
            Page<User> page = new Page<>(pageNo, pageSize);
            List<UserResp> list = userService.list(keyword, page);
            return BaseResult.success(BasePage.init(list, page.getTotal(), page.getCurrent(), page.getSize()));
        } catch (Exception e) {
            baseResult.setCode(ResultCodeEnum.FAIL.getCode());
            baseResult.setMessage(e.getMessage());
        }
        return baseResult;
    }


    @RequestLog(func = "用户详情", durable = true, console = true)
    @ApiOperation(value = "用户详情")
    @GetMapping("/users/{userId}")
    public BaseResult<UserResp> get(@NotNull(message = "id不能为空") @PathVariable("userId") Long userId) {
        log.info("用户管理 -> 详情，userId={}", userId);
        BaseResult<UserResp> baseResult = BaseResult.success();
        try {
            UserResp user = userService.getUserDetail(userId);
            return BaseResult.success(user);
        } catch (Exception e) {
            baseResult.setCode(ResultCodeEnum.FAIL.getCode());
            baseResult.setMessage(e.getMessage());
        }
        return baseResult;
    }

    @RequestLog(func = "用户删除", durable = true, console = true)
    @ApiOperation(value = "删除")
    @DeleteMapping("/users/{userId}")
    public BaseResult<Void> delete(@NotNull(message = "id不能为空") @PathVariable("userId") Long userId) {
        log.info("用户管理 -> 删除，userId={}", userId);
        BaseResult<Void> baseResult = BaseResult.success();
        try {
            userService.remove(userId);
            return BaseResult.success();
        } catch (Exception e) {
            baseResult.setCode(ResultCodeEnum.FAIL.getCode());
            baseResult.setMessage(e.getMessage());
        }
        return baseResult;
    }


}
