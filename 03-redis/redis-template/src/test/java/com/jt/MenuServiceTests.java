package com.jt;

import com.jt.pojo.Menu;
import com.jt.service.MenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class MenuServiceTests {

    @Autowired //基于类型注入
    @Qualifier("aopCacheMenuServiceImpl")
    private MenuService menuService;

    //写三个单元测试方法，对menuService中的方法进行单元测试
    @Test
    void testSelectById() {
        Menu menu = menuService.selectById(4L);
        System.out.println(menu);
    }

    @Test
    void testInsertMenu() {
        Menu menu = new Menu();
        menu.setName("daoru resource");
        menu.setPermission("sys:res:daoru");
        menuService.insertMenu(menu);
    }

    @Test
    void testUpdateMenu(){
        Menu menu = menuService.selectById(5L);
        menu.setName("delete resource");
        menu.setPermission("sys:res:caizii");
        menuService.updateMenu(menu);
    }
}
