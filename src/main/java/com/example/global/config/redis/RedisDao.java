package com.example.global.config.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.time.Duration;
import java.util.*;

@Component
@RequiredArgsConstructor
public class RedisDao {
    private final RedisTemplate<String, String> redisTemplate;
    private final JedisPool jedisPool;

    public void persistKey(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.persist(key);
        }
    }

    public void setValues(String key, String data) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(key, data);
    }

    public void increaseViews(String key) {
        redisTemplate.opsForValue().increment(key);
    }

    public Long getViews(String key) {
        String views = redisTemplate.opsForValue().get(key);
        if (views != null) {
            return Long.parseLong(views);
        }
        return 0L;
    }
    public void setValuesList(String key, String data) {
        redisTemplate.opsForList().rightPushAll(key,data);
    }

    public List<String> getValuesList(String key) {
        Long len = redisTemplate.opsForList().size(key);
        return len == 0 ? new ArrayList<>() : redisTemplate.opsForList().range(key, 0, len-1);
    }

    public void setValues(String key, String data, Duration duration) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(key, data, duration);
    }

    public String getValues(String key) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(key);
    }

    public void deleteValues(String key) {
        redisTemplate.delete(key);
    }
}