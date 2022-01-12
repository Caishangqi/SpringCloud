package com.jt;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class CacheManagerConfig {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {

        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        //指定key的序列化方式(默认是jdk的序列化方式)
        //指定key的序列化方式(默认是string方式)
        cacheConfiguration.serializeKeysWith(RedisSerializationContext.
                SerializationPair.fromSerializer(RedisSerializer.string()));
        //指定value的序列化方式(默认是jdk的序列化方式)
        cacheConfiguration.serializeValuesWith(RedisSerializationContext.
                SerializationPair.fromSerializer(RedisSerializer.json())); //换成json格式

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(cacheConfiguration)
                .build();

    }

}
