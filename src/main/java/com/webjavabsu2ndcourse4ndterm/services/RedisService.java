package com.webjavabsu2ndcourse4ndterm.services;

import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisClientConfig;

public class RedisService {
    private final Jedis jedis;

    public RedisService() {
        DefaultJedisClientConfig defaultJedisClientConfig = DefaultJedisClientConfig.builder().password("vboxredis001").build();
        this.jedis = new Jedis("localhost", 6379, defaultJedisClientConfig);
    }

    public void saveSession(String sessionId, String attributeName, String attributeValue) {
        String key = "session:" + sessionId;
        jedis.hset(key, attributeName, attributeValue);
        // Устанавливаем время жизни сессии (например, 30 минут)
        jedis.expire(key, 1800);
    }

    public String getSessionAttribute(String sessionId, String attributeName) {
        String key = "session:" + sessionId;
        return jedis.hget(key, attributeName);
    }
}