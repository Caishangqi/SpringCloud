package com.jt.system.service;

import com.jt.system.pojo.User;

import java.util.List;

public interface UserService {

    User selectUserByUsername(String username);
    List<String> selectUserPermissions(Long userId);

}
