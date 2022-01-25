package com.albusxing.dobby.service.impl;

import com.albusxing.dobby.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;


/**
 * @author liguoqing
 */
@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final StringRedisTemplate stringRedisTemplate;
    private final RedisTemplate redisTemplate;

    @Override
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean expire(String key, long expire) {
        return stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    @Override
    public void remove(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public Long increment(String key, long delta) {
        return stringRedisTemplate.opsForValue().increment(key, delta);
    }

    @Override
    public void set(String key, String value, long expire) {
        stringRedisTemplate.opsForValue()
                .set(key, value);
        stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }


    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    @Override
    public boolean exists(final Serializable key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * read cache
     *
     * @param key
     * @return
     */
    @Override
    public Object getStr(final Serializable key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * add into canche
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean setStr(final Serializable key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * add into cache with expiration time
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean setStr(final Serializable key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
