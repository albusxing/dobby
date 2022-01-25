package com.albusxing.dobby.service.impl;

import com.albusxing.dobby.common.constant.DataStatusEnum;
import com.albusxing.dobby.dto.UserReq;
import com.albusxing.dobby.dto.UserResp;
import com.albusxing.dobby.entity.User;
import com.albusxing.dobby.mapper.UserMapper;
import com.albusxing.dobby.service.UserService;
import com.albusxing.dobby.service.mapping.UserMapping;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liguoqing
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapping userMapping;

    @Override
    public void save(UserReq userReq) {
        User user = userMapping.toUser(userReq);
        this.save(user);
    }

    @Override
    public void update(Long userId, UserReq userReq) {
        User user = this.getById(userId);
        userMapping.updateToUser(userReq, user);
        this.updateById(user);
    }

    @Override
    public List<UserResp> list(String username, Page<User> page) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>()
                .eq("status", DataStatusEnum.NORMAL.getCode())
                .like(StringUtils.isNotEmpty(username), "username", username);
        Page<User> pageResult = this.page(page, queryWrapper);
        List<User> records = pageResult.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return Lists.newArrayList();
        }
        return records.stream().map(userMapping::toUserResp).collect(Collectors.toList());
    }

    @Override
    public User findByNameAndPwd(String username, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>()
                .eq("status", DataStatusEnum.NORMAL.getCode())
                .eq("username", username)
                .eq("password", password);
        return this.getOne(queryWrapper, false);
    }

    @Override
    public UserResp getUserResp(Long userId) {
        User user = this.getById(userId);
        return userMapping.toUserResp(user);
    }
}
