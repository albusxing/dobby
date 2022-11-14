package com.albusxing.dobby.web;

import com.alibaba.cola.dto.Response;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Albusxing
 * @created 2022/11/11
 */
@Slf4j
@Validated
@Api(tags = "用户管理")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    @ApiOperation(value = "用户新增")
    @PostMapping("/users")
    public Response save() {

        return Response.buildSuccess();
    }


}
