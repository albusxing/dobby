package com.albusxing.dobby.common.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @author Albusxing
 * @created 2022/11/7
 */
public class BaseDAO<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {
}
