package com.jhc.csbot.utils;

import cn.hutool.json.JSONUtil;
import com.jhc.csbot.common.domain.enums.ErrorStatus;
import com.jhc.csbot.common.exception.ThrowUtils;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Description: Redis 工具类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/12
 */
@Component
public class RedisUtils {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 设置有过期时间的键值对
     * @param key
     * @param value
     * @param time
     * @param timeUnit
     */
    public void setWithExpireTime(String key, Object value, Integer time, TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value), time, timeUnit);
    }

    /**
     * 设置键值对
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value));
    }

    /**
     * 获取键值对
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(String key, Class<T> clazz) {
        String value = stringRedisTemplate.opsForValue().get(key);
        if (value == null) {
            return null;
        }
        // 如果 value 不是 JSON 格式, 则默认为普通字符串
        if (!value.startsWith("{") && !value.startsWith("[")) {
            ThrowUtils.throwIf(clazz != String.class, ErrorStatus.SYSTEM_ERROR, "RedisUtils.get() 传入的 clazz 参数不是 String 类型");
            return (T) value;
        }
        return JSONUtil.toBean(value, clazz);
    }

    /**
     * 生成自增 id
     * @param key
     * @return
     */
    public Long getIncId(String key) {
        return stringRedisTemplate.opsForValue().increment(key);
    }
}
