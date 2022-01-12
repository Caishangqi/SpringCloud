package com.jt.controller;

import com.jt.pojo.Menu;
import com.jt.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CachePut;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu")
public class MenuController {

    //    @Autowired
    //    @Qualifier("aopCacheMenuServiceImpl")
    private MenuService menuService; //final 描述的属性只能用构造器赋值

    @Autowired
    public MenuController(@Qualifier("menuServiceImpl") MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/{id}")
    public Menu doSelectById(@PathVariable("id") Long id) {
        return menuService.selectById(id);
    }


    @PostMapping
    public String doInsert(@RequestBody Menu menu){
        menuService.insertMenu(menu);
        return "(!) insert ok";
    }

    @PutMapping
    public String doUpdate(@RequestBody Menu menu){
        menuService.updateMenu(menu);
        return "update ok";
    }




}
