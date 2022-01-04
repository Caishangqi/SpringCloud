package com.jt;

import com.jt.system.dao.UserMapper;
import com.jt.system.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
public class UserMapperTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    void testSelectUserByUsername() {
        User user = userMapper.selectUserByUsername("admin");
//        System.out.println(user);
//        断言测试(结果不正确抛出异常)
//        例如：测试user对象是否不为null,为null则抛出异常
//
//        org.springframework.util.Assert
//         Assert.notNull(user,"user is not exist");
//        具体业务中是这样的
//                if(user==null)
//                    throw new IllegalArgumentException("user is not exits");
//        org.junit.jupiter.api.Assertions
        Assert.notNull(user, "user是空的");

    }

    @Test
    void testSelectUserPermissions() {
        List<String> permissions =
                userMapper.selectUserPermissions(1L);
        System.out.println(permissions);
    }
}
