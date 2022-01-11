package com.jt;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.net.UnknownHostException;

/**
 * 在配置类中自定义RedisTemplate的配置
 */
@Configuration
public class RedisConfig {
    //配置JSON序列化
    public RedisSerializer<Object> jsonSerializer() {
        //定义JSON序列化,反序列化对象
        Jackson2JsonRedisSerializer<Object> jsonSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        //定义序列化规则(序列化规则由ObjectMapper定义)

        //创建ObjectMapper对象
        ObjectMapper objectMapper = new ObjectMapper();

        //指定按什么规则进行序列化 -->
        //按什么方法规则进行序列化(例如,任意访问修饰符的get方法)
        objectMapper.setVisibility(PropertyAccessor.GETTER, //类中的get方法
                JsonAutoDetect.Visibility.ANY); //任意访问修饰符

        //假如属性值为null则不再进行此属性的序列化
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        //将对象类型写入到序列化的字符串中
        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), //多态校验
                ObjectMapper.DefaultTyping.NON_FINAL, //非final对象允许序列化
                JsonTypeInfo.As.EXISTING_PROPERTY); //类型以属性形式进行存储

        /*如果不储存类型则就找不到对象，默认储存到linked hashmap*/

        jsonSerializer.setObjectMapper(objectMapper);

        return jsonSerializer;
    }

    //方案2：高级定制(改变默认json序列化方式)
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object, Object> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        //修改默认的序列化方式(默认为JDK的序列化方式)
        //设置key的序列化方式
        template.setKeySerializer(RedisSerializer.string());//大key
        template.setHashKeySerializer(RedisSerializer.string());//小key(Value为hash类型时)
        //设置值的序列化方式
        template.setValueSerializer(jsonSerializer());
        template.setHashValueSerializer(jsonSerializer());
        //官方建议，只要改变了默认配置要执行一下如下语句,更新一下属性配置。
        template.afterPropertiesSet();
        return template;
    }

    /*
    * 方案1 简单定制
    * */
//    @Bean
//    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
//        RedisTemplate<Object, Object> template = new RedisTemplate();
//        template.setConnectionFactory(redisConnectionFactory);
//        //修改默认的序列化方式(默认为JDK的序列化方式)
//        //设置key的序列化方式
//        template.setKeySerializer(RedisSerializer.string());//大key
//        template.setHashKeySerializer(RedisSerializer.string());//小key(Value为hash类型时)
//        //设置值的序列化方式
//        template.setValueSerializer(RedisSerializer.json());
//        template.setHashValueSerializer(RedisSerializer.json());
//        return template;
//    }
}
