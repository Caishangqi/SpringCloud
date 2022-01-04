package com.jt.auth.service;

import com.jt.auth.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "sso-system", contextId = "remoteUserService")
public interface RemoteUserService {

    /**
     * 基于用户名查询用户基本信息
     *
     * @param username
     * @return
     */
    @GetMapping("/user/login/{username}")
    User selectUserByUsername(@PathVariable("username") String username);

    /**
     * 基于用户id查询用户权限
     *
     * @param userId
     * @return
     */
    @GetMapping("/user/permission/{userId}")
    List<String> selectUserPermissions(@PathVariable("userId") Long userId);

}
