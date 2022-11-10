package com.albusxing.dobby.service;
import com.albusxing.dobby.domain.entity.User;
import com.albusxing.dobby.dto.UserCmd;
import com.albusxing.dobby.dto.UserQuery;
import com.albusxing.dobby.dto.UserResp;
import com.albusxing.dobby.excel.UserImportData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @author liguoqing
 */
public interface UserService {

    /**
     * 新增
     */
    void save(UserCmd userCmd);

    /**
     * 修改
     */
    void update(Long userId, UserCmd userCmd);

    /**
     * 删除
     */
    void remove(Long userId);

    /**
     * 查询
     */
    List<UserResp> list(UserQuery userQuery, Page<User> page);

    List<UserResp> list(String keyword, Page<User> page);

    List<User> listAll();

    /**
     * 用户详情
     */
    UserResp getUserDetail(Long userId);

    /**
     * 批量保存
     */
    void batchSave(List<UserImportData> importDataList);

}
