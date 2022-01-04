package com.jt.system.service.impl;

import com.jt.system.dao.UserMapper;
import com.jt.system.pojo.User;
import com.jt.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectUserByUsername(String username) {
        Assert.notNull(username, "username can not be null");
        return userMapper.selectUserByUsername(username);
    }


    /**
     * 此注解描述的方法为缓存切入点方法，从数据库查询到数据后，可以将数据存储到
     * 本地的一个缓存对象中(底层是一个map对象)
     * @param userId
     * @return
     */
    @Cacheable(value = "permissionsCache")
    @Override
    public List<String> selectUserPermissions(Long userId) {

        return userMapper.selectUserPermissions(userId);
    }
}
