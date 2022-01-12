package com.jt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching //开启AOP缓存
@SpringBootApplication
public class RedisApplication {

    public static void main(String[] args) {

        SpringApplication.run(RedisApplication.class, args);

    }
}
