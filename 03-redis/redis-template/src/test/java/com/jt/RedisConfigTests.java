package com.jt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
public class RedisConfigTests {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void testBlogOper() {
        ValueOperations vo = redisTemplate.opsForValue();
        Blog blog = new Blog();
        blog.setTitle("Demo Title");
        blog.setId(100L);
        vo.set("blog-redis", blog);

    }

    @Test
    void testBlogOper02() {
        HashOperations ho = redisTemplate.opsForHash();
        ho.put("blog-template", "id", "200L");
        ho.put("blog-template", "title", "hello Abba");
    }
}
