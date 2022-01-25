package com.albusxing.dobby.service;


import com.albusxing.dobby.dto.UserReq;
import com.albusxing.dobby.dto.UserResp;
import com.albusxing.dobby.entity.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author liguoqing
 */
public interface UserService extends IService<User> {

    /**
     * 新增
     * @param userReq
     */
    void save(UserReq userReq);

    /**
     * 修改
     * @param userId
     * @param userReq
     */
    void update(Long userId, UserReq userReq);

    /**
     * 查询
     * @param username
     * @param page
     * @return
     */
    List<UserResp> list(String username, Page<User> page);

    /**
     * 根据用户名和密码查询用户
     * @param username
     * @param password
     * @return
     */
    User findByNameAndPwd(String username, String password);

    /**
     * 查询用户信息
     * @param userId
     * @return
     */
    UserResp getUserResp(Long userId);

}
