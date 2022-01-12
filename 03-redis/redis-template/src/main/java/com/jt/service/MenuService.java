package com.jt.service;

import com.jt.pojo.Menu;

public interface MenuService {

    /**
     * 基于id查找菜单对象
     * @param id
     * @return
     */
    Menu selectById(Long id);

    /**
     * 新增菜单信息
     * @param menu
     * @return
     */
    Menu insertMenu(Menu menu);

    /**
     * 修改菜单信息
     * @param menu
     * @return
     */
    Menu updateMenu(Menu menu);
    //....

}
