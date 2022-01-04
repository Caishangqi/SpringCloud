package com.jt;

import com.jt.system.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void testSelectUserPermissions() {
        List<String> list = userService.selectUserPermissions(1L);
        System.out.println(list);
        list = userService.selectUserPermissions(1L);
        System.out.println(list);
    }

}
