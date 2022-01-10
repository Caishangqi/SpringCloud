package com.jt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.UUID;

@SpringBootTest
public class RedisTemplateTests {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 测试是否能够连通redis
     */
    @Test
    void testGetConnection() {
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        String ping = connection.ping();
        System.out.println(ping);
    }

    /**
     * 此对象为操作redis的一个模板对象,基于此对象进行数据存储时，
     * 数据会进行序列化，序列化方式默认为JDK自带的序列化机制。
     */
    @Test
    void testHashOper01() {
        //获取字符串操作对象(ValueOperations)
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //操作redis数据
        valueOperations.set("x", "100");
        Object x = valueOperations.get("x");
        System.out.println(x);
        //vo.increment("x");//不可以
        Long y = valueOperations.increment("y");
        y = valueOperations.increment("y");
        //Object y = vo.get("y");//不可以
        System.out.println(y);
        //存储key/value，设置key的有效期
        valueOperations.set("z", "100", Duration.ofSeconds(10));
    }

    @Test
    void testStringOper02() {
        //获取字符串操作对象(ValueOperations)
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //按默认序列化方式存储数据
        String token = UUID.randomUUID().toString();
        valueOperations.set(token, "admin");

        //指定序列方式进行数据存储
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(RedisSerializer.string());

        valueOperations.set(token, "Caizii");

        //更新数据(假如有对应的key则直接进行覆盖)
        valueOperations.set(token, "ABBA"); //按照改过序列化储存之后的数据
        Object value = valueOperations.get(token);
        System.out.println(value);

        //删除数据(存数据时给定有效期-生产环境必须设置)
        valueOperations.set("permissions", "sys:res:update", Duration.ofSeconds(5));

    }

}
