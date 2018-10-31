package com.url.urlmessage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RedisService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${spring.redis.host}")
    private String redis_host;
    @Value("${spring.redis.port}")
    private int redis_port;

    public Object addListString(List<String> list, String keyName) throws Exception {
        Long begin = System.currentTimeMillis();
        Jedis jedis = new Jedis(redis_host, redis_port);
        Pipeline pipeline = jedis.pipelined();
        list.forEach(item -> {
            pipeline.lpush(keyName, item);
        });
        pipeline.sync();
        jedis.close();

        Long end = System.currentTimeMillis();
        Long d = end - begin;
        return d + "毫秒";
    }
}