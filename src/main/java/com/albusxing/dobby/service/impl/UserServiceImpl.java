package com.albusxing.dobby.service.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.albusxing.dobby.common.enums.DataStatusEnum;
import com.albusxing.dobby.dao.UserDAO;
import com.albusxing.dobby.dto.UserCmd;
import com.albusxing.dobby.dto.UserQuery;
import com.albusxing.dobby.dto.UserResp;
import com.albusxing.dobby.domain.entity.User;
import com.albusxing.dobby.excel.UserImportData;
import com.albusxing.dobby.service.UserService;
import com.albusxing.dobby.service.converter.UserConverter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author liguoqing
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserConverter userConverter;
    private final UserDAO userDAO;

    @Override
    public void save(UserCmd userCmd) {
        User user = userConverter.toUser(userCmd);
        userDAO.save(user);
    }

    @Override
    public void update(Long userId, UserCmd userCmd) {
        User user = userDAO.getById(userId);
        Validator.validateNotNull(user, "用户数据不存在");
        userConverter.updateToUser(userCmd, user);
        userDAO.updateById(user);
    }

    @Override
    public void remove(Long userId) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<User>()
                .set("status", DataStatusEnum.DELETED.getCode())
                .eq("id", userId);
        userDAO.update(updateWrapper);
    }


    @Override
    public List<UserResp> list(UserQuery userQuery, Page<User> page) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 关键字
        String keyword = userQuery.getKeyword();
        queryWrapper.like(StringUtils.isNotEmpty(keyword), "username", keyword);
        // 性别
        Integer gender = userQuery.getGender();
        if (Objects.nonNull(gender)) {
            queryWrapper.eq("gender", gender);
        }
        // 地址
        String address = userQuery.getAddress();
        if (StrUtil.isNotBlank(address)) {
            queryWrapper.like("address", address);
        }
        // 创建时间
        Date startDate = userQuery.getStartDate();
        Date endDate = userQuery.getEndDate();
        if (Objects.nonNull(startDate) && Objects.nonNull(endDate)) {
            queryWrapper.between("create_time", startDate, endDate);
        }

        Page<User> pageResult = userDAO.page(page, queryWrapper);
        List<User> records = pageResult.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return Lists.newArrayList();
        }
        return records.stream().map(userConverter::toUserResp).collect(Collectors.toList());
    }

    @Override
    public List<UserResp> list(String keyword, Page<User> page) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 关键字
        queryWrapper.like(StringUtils.isNotEmpty(keyword), "username", keyword);
        Page<User> pageResult = userDAO.page(page, queryWrapper);
        List<User> records = pageResult.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return Lists.newArrayList();
        }
        return records.stream().map(userConverter::toUserResp).collect(Collectors.toList());
    }


    @Override
    public List<User> listAll() {
        return userDAO.list();
    }

    @Override
    public UserResp getUserDetail(Long userId) {
        User user = userDAO.getById(userId);
        return userConverter.toUserResp(user);
    }

    @Override
    public void batchSave(List<UserImportData> importDataList) {
        List<User> users = userConverter.toUsers(importDataList);
        userDAO.saveBatch(users);
    }
}
