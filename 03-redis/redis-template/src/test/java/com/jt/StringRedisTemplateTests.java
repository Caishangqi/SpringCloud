package com.jt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Map;

@SpringBootTest
public class StringRedisTemplateTests {

    /**
     * StringRedisTemplate 对象是一个特殊RedisTemplate，
     * 默认序列化方式改为了string方式。
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void testStringOper01() {
        //获取数据操作对象
        ValueOperations<String, String> vo = stringRedisTemplate.opsForValue();
        vo.set("color", "red");
        String color = vo.get("color");
        System.out.println(color);

    }

    @Test
    void testHashOper02() {

        //获取数据操作对象
        HashOperations<String, Object, Object> ho = stringRedisTemplate.opsForHash();
        ho.put("blog", "id", "1001");
        ho.put("blog", "title", "spring boot");

        Map<Object, Object> blog = ho.entries("blog");
        System.out.println(blog);

    }

    @Test
    void testPojoOper() throws JsonProcessingException {

        ValueOperations<String, String> vo = stringRedisTemplate.opsForValue();
        Blog blog = new Blog();
        blog.setId(100l);
        blog.setTitle("spring...");

        vo.set("blog-arcane", new ObjectMapper().writeValueAsString(blog));
        String jsonStr = vo.get("blog-arcane");
        System.out.println(jsonStr);


    }

}
