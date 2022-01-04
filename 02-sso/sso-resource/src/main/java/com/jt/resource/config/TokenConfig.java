package com.jt.resource.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class TokenConfig {

    /**
     * 创建令牌信息存储对象，用户带着令牌来要创建这个类来解析它
     */

    /**
     * 配置令牌的存储策略,对于oauth2规范中提供了这样的几种策略
     * 1)JdbcTokenStore(这里是要将token存储到关系型数据库)
     * 2)RedisTokenStore(这是要将token存储到redis数据库-key/value)
     * 3)JwtTokenStore(这里是将产生的token信息存储客户端，并且token
     * 中可以以自包含的形式存储一些用户信息)
     * 4)....
     */

    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 配置令牌的创建及验签方式
     * 基于此对象创建的令牌信息会封装到OAuth2AccessToken类型的对象中
     * 然后再存储到TokenStore对象，外界需要时，会从tokenStore进行获取。
     */

    /**JWT令牌增强器，可以通过此对象创建JWT令牌，是普通令牌的增强版，
     * 普通令牌是不可以存储用户信息的*/
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter jwtAccessTokenConverter=
                new JwtAccessTokenConverter();
        //设置签名key，对JWT令牌进行签名时使用，这个key千万不能让客户端知道
        jwtAccessTokenConverter.setSigningKey(signingKey);
        return jwtAccessTokenConverter;
    }
    /**签名key，对JWT令牌签名时使用，这个值可以自己随意定义，一般是比较复杂的一个字符串*/
    private String signingKey="auth";

}
