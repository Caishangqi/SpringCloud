package com.jt;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolTests {

    /**
     * Jedis连接池应用测试
     */
    @Test
    public void testJedisPool() {

        //创建池

        //连接池配置(可选，具体配置多少有业务决定)
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(128);
        config.setMaxIdle(16);
        JedisPool jedisPool = new JedisPool(config, "192.168.126.129", 6379);

        //池中获取连接 (池只会在内存中存在一份)
        Jedis resource = jedisPool.getResource();

        //读写redis数据
        resource.set("db", "redis");
        resource.expire("db", 10);
        String String = resource.get("db");
        System.out.println(String);

        //释放资源
        resource.close();
        jedisPool.close();
    }

}
