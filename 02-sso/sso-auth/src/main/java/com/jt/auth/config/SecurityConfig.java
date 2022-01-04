package com.jt.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.message.ObjectMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * 此对象负责完成密码的加密
     *
     * @return
     */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //方法返回的对象为后续的oauth2的配置提供服务
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        //.......
        return super.authenticationManagerBean();
    }



    /**
     * 此方法为http请求配置方法，可以在此方法中配置：
     * 1)哪些资源放行(不用登录即可访问)，假如不做任何配置默认所有资源都匿名访问
     * 2)哪些资源必须认证(登录)后才可访问。
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //禁用跨域攻击?(假如没有禁用，使用postman,httpclient这些工具进行登录测试会403异常)
        http.csrf().disable();

        //super.configure(http);
        //所有必须认证才可访问
        //http.authorizeRequests().antMatchers("/**").authenticated(); //status=403 没有权限
        //所有资源都放行(在资源服务中去授权)
        http.authorizeRequests().anyRequest().permitAll();

        //配置需要认证的，例如default.html，其它的都放行
        //        http.authorizeRequests()
        //                .antMatchers("/default.html")
        //                .authenticated()
        //                .anyRequest().permitAll();

        //登录配置(去哪里认证，认证成功或失败的处理器是谁)
        //http.formLogin().defaultSuccessUrl("/index.html");//redirect index.html 不走controller
        //http.formLogin().successForwardUrl("/doIndex");//forward 转发还是POST请求
        //前后端分离中的一种做法，是登录成功要返回json数据！

        http.formLogin().successHandler(successHandler())
                .failureHandler(failureHandler());
    }

    /**
     * 登录成功处理器
     */
    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

                PrintWriter writer = httpServletResponse.getWriter();
                //一种返回JSON格式的写法
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("status", 200);
                map.put("message", "login success!");

                writeJsonToClient(httpServletResponse, map);
            }
        };
    }

    /**
     * 登录失败处理器
     */
    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

                //一种返回JSON格式的写法
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("status", 500);
                map.put("message", "login failure");
                writeJsonToClient(httpServletResponse, map);
            }
        };
    }

    private void writeJsonToClient(HttpServletResponse response, Map<String, Object> map) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        String jsonStr = new ObjectMapper().writeValueAsString(map);
        writer.println(jsonStr);
        writer.flush();
    }
}
