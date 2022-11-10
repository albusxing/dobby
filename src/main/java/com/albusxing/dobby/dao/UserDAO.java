package com.albusxing.dobby.dao;

import com.albusxing.dobby.common.base.BaseDAO;
import com.albusxing.dobby.domain.entity.User;
import com.albusxing.dobby.domain.mapper.UserMapper;
import org.springframework.stereotype.Repository;

/**
 * @author Albusxing
 * @created 2022/11/7
 */
@Repository
public class UserDAO extends BaseDAO<UserMapper, User> {
}
