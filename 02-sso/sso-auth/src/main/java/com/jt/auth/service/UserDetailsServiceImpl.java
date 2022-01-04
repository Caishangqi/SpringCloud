package com.jt.auth.service;

import com.jt.auth.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 构建UserDetailsService接口实现类，在此类中基于RemoteUserService接口进行
 * 远程服务调用，调用sso-system服务，获取用户数据。
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private RemoteUserService remoteUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //1.基于用户名获取用户信息(远程feign方式的服务调用)
        com.jt.auth.pojo.User user = remoteUserService.selectUserByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("User is not exist");

        //2.基于用户id获取用户权限信息
        List<String> permissions = remoteUserService.selectUserPermissions(user.getId());
        log.debug("(!) 权限为 {}", permissions);

        //3.封装用户信息并返回 (这里就给token叫做authorities的内容)
        org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(username, user.getPassword(), AuthorityUtils.createAuthorityList(permissions.toArray(new String[]{})));
        return userDetails;//交给spring security的认证中心，进行认证分析(比对)

        //交给了 -->
        //AuthenticationManager (认证管理器)

    }
}
