package com.jt.resource.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer //启动资源服务器的默认配置
@EnableGlobalMethodSecurity(prePostEnabled = true) //启动方法上的授权机制
public class ResourceConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        http.csrf().disable();
        http.authorizeRequests()
                //配置需要认证的资源
                .antMatchers("/resource/**").authenticated()
                //除了需要认证的资源，其它资源全部放行。
                .anyRequest().permitAll();
    }
}
