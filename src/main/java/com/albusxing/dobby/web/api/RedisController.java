package com.albusxing.dobby.web.api;


import com.albusxing.dobby.common.base.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author liguoqing
 */
@Slf4j
@Api(tags = "Redis")
@RestController
@RequiredArgsConstructor
public class RedisController {

    private final RedisTemplate redisTemplate;

    @ApiOperation(value = "Redis锁")
    @GetMapping("/redis/lock")
    public BaseResult<Boolean> redisLock() {
        log.info(">>>>>>> 进入方法");
        String key = "redis-key";
        String val = UUID.randomUUID().toString();
        RedisCallback<Boolean> redisCallback = connection -> {
            RedisStringCommands.SetOption setOption = RedisStringCommands.SetOption.ifAbsent();
            Expiration expiration = Expiration.seconds(30);
            byte[] redisKey = redisTemplate.getKeySerializer().serialize(key);
            byte[] redisVal = redisTemplate.getValueSerializer().serialize(val);
            Boolean result = connection.set(redisKey, redisVal, expiration, setOption);
            return result;
        };
        Boolean lock = (Boolean) redisTemplate.execute(redisCallback);
        if (lock) {
            log.info(">>>>> 获取到锁");
            try {
                log.info(">>>>> 执行业务逻辑");
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                String script = "if redis.call(\"get\", KEYS[1]) == ARGV[1] then return redis.call(\"del\", KEYS[1]) else return 0 end";
                RedisScript<Boolean> redisScript = RedisScript.of(script, Boolean.class);
                List<String> keys = Collections.singletonList(key);
                Boolean result = (Boolean) redisTemplate.execute(redisScript, keys, val);
                if (result) {
                    log.info(">>>>>>> 释放了锁");
                }
            }
        }
        log.info(">>>>> 方法执行完成");
        return BaseResult.success();
    }





}
