package com.jt.auth.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * Oauth2是一种协议或规范，它定义了完成用户身份认证和授权的方式，例如基于账号密码进行认证，
 * 基于第三方令牌认证(例如QQ,微信登录)等，但具体完成过程需要有一组对象，这些对象的构成，一般
 * 有如下几个部分：
 * 0)系统数据资源(类似数据库，文件系统，。。。)
 * 1)资源服务器(负责访问资源，商品，订单，库存，会员,.....)
 * 2)认证服务器(负责完成用户身份的认证)
 * 3)客户端对象(表单,令牌，....)
 * 4)资源拥有者(用户)
 * <p>
 * 思考：在Oauth2这种规范下，如何对用户什么进行认证?
 * 1)认证的地址？(让用户去哪里认证，例如结婚要去民政局办理结婚登记)
 * 2)用户需要携带什么信息去认证？(去民政局办理结婚登记需要携带什么证件)
 * 3)具体完成认证的对象是谁？(民政局有办理结婚的，也有办理离婚的，比方说具体由谁帮你办理结婚证书)
 * 这些规范，在Oauth2中有默认定义，但是业务不同时，我们需要基于业务进行重写。
 */
@AllArgsConstructor //全参构造器在构建方法时会自动Autowired
@Configuration
@EnableAuthorizationServer
public class Oauth2Config extends AuthorizationServerConfigurerAdapter {

    @Autowired

    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 在此方法中公开认证地址？(客户端认证时输入的url中path是谁？)
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //super.configure(security);

        security
                //公开认证地址(/oauth/token),permitAll()允许所有客户端请求在这个地址进行认证
                .tokenKeyAccess("permitAll()")
                //公开校验令牌的地址(/oauth/check_token)
                .checkTokenAccess("permitAll()")//permitAll()不需要权限 令牌检验不需要任何权限就可以进入
                //允许通过表单方式进行认证
                .allowFormAuthenticationForClients();

    }


    /**
     * 定义用户去认证时，需要携带什么信息？
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //super.configure(clients);

        clients.inMemory()
                //客户端标识
                .withClient("gateway-client")
                //客户端携带秘钥 123456 验证是否被篡改过
                .secret(passwordEncoder.encode("123456")) //客户端携带的密钥必须是 123456
                //定义认证类型(允许对什么哪些数据进行认证)
                .authorizedGrantTypes("password","refresh_token")
                //作用域，满足如上条件的所有客户端都可以到这里来进行认证。
                .scopes("all");

    }


    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    /**
     * 定义由谁完成认证，认证成功后以怎样形式颁发令牌
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //super.configure(endpoints);

        //定义由谁完成认证
        endpoints.authenticationManager(authenticationManager);
        //定义令牌业务处理对象(自己指定令牌规则)
        endpoints.tokenServices(tokenServices());
        //允许客户端认证的请求方式(默认只支持post)
        endpoints.allowedTokenEndpointRequestMethods(HttpMethod.GET,HttpMethod.POST);


    }

    /**
     * tokenservice对象，用于处理token业务
     * @return
     */
    @Bean
    public AuthorizationServerTokenServices tokenServices(){
        DefaultTokenServices tokenServices=new DefaultTokenServices();
        //设置令牌存储对象
        tokenServices.setTokenStore(tokenStore);
        //设置令牌增强(由普通令牌改为jwt令牌)
        tokenServices.setTokenEnhancer(jwtAccessTokenConverter);
        //设置访问令牌有效期(访问令牌就是访问资源时要携带的令牌)
        tokenServices.setAccessTokenValiditySeconds(3600);
        //设置刷新令牌(在访问令牌即将到期时，还可以使用刷新令牌再去请求一个新的令牌)
        tokenServices.setSupportRefreshToken(true);
        //设置刷新令牌有效期(这个时间要比访问令牌长一些)
        tokenServices.setRefreshTokenValiditySeconds(7200);
        return tokenServices;
    }
}
