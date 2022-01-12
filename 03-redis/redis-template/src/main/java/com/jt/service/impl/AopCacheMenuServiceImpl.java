package com.jt.service.impl;

import com.jt.dao.MenuMapper;
import com.jt.pojo.Menu;
import com.jt.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class AopCacheMenuServiceImpl implements MenuService {

    private static Logger logger = LoggerFactory.getLogger(AopCacheMenuServiceImpl.class);

    @Autowired
    private MenuMapper menuMapper;

    /**
     * @param id
     * @return
     * @Cacheable 注解描述的方法为spring中的缓存切入点方法
     * 方法执行之前会先查缓存，缓存没有查询mysql，然后将查询
     * 结果存储到缓存。
     */
    @Cacheable(value = "menuCache", key = "#id")
    @Override
    public Menu selectById(Long id) {
        logger.info("Get data from RDBMS");
        return menuMapper.selectById(id);
    }

    @Override
    public Menu insertMenu(Menu menu) {

        logger.info("insert.before: {}", menu);
        menuMapper.insert(menu);
        logger.info("insert.after: {}", menu);

        return menu;
    }

    /**
     * @param menu
     * @return
     * @CachePut 注解描述的方法同样是一个缓存切入段方法，
     * 主要用于更新缓存中的数据
     */

    @CachePut(value = "menuCache", key = "#menu.id")
    @Override
    public Menu updateMenu(Menu menu) {
        menuMapper.updateById(menu);
        return menu; //CachePut原理 key 是menu.id 值是方法的返回值
    }
}
