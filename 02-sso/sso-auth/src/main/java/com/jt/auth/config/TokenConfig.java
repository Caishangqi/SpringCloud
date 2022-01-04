package com.jt.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
@Configuration
public class TokenConfig {

    /**
     * 创建令牌信息存储对象
     */
    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }
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
