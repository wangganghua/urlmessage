package com.url.urlmessage.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.List;

@Service
public class RedisServiceImpl implements RedisService {
    @Value("${spring.redis.host}")
    private String redis_host;
    @Value("${spring.redis.port}")
    private int redis_port;

    /**
     * 批量注入redis
     * @param list: 使用pipline批量快速注入 list 集合
     * @param keyName： redis key
     * @return 消耗时长
     * @throws Exception:抛出错误信息
     */
    public Object addListString(List<String> list, String keyName) throws Exception {
        long begin = System.currentTimeMillis();
        Jedis jedis = new Jedis(redis_host, redis_port);
        Pipeline pipeline = jedis.pipelined();
        list.forEach(item -> {
            pipeline.lpush(keyName, item);
        });
        pipeline.sync();
        jedis.close();

        long end = System.currentTimeMillis();
        long d = end - begin;
        return d + "毫秒";
    }
}
