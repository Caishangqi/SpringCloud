package com.jt.service.impl;

import com.jt.dao.MenuMapper;
import com.jt.pojo.Menu;
import com.jt.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    //    @Autowired 基于类型注入
    //    private RedisTemplate redisTemplate;

    //    基于构造注入为valueOperations属性赋值
    //    @Autowired (只有一个构造方法这个@Autowired可以省略)
    //    public MenuServiceImpl(RedisTemplate redisTemplate){
    //       this.valueOperations=redisTemplate.opsForValue();
    //    }

    @Resource(name = "redisTemplate")
    private ValueOperations valueOperations;

    private static final Logger log =
            LoggerFactory.getLogger(MenuServiceImpl.class);

    @Override
    public Menu selectById(Long id) {
        //从redis缓存查询数据，假如有则直接返回缓存中数据
//        ValueOperations vo = redisTemplate.opsForValue();
        String key = String.valueOf(id);
        Object o = valueOperations.get(key);
        if (o != null) {
            log.info("(!) data from redis");
            return (Menu) o;
        }
        //redis缓存没有则查询关系数据库
        Menu menu = menuMapper.selectById(id);
        log.info("(!) data from rdbms");
        //将数据存储到redis缓存
        valueOperations.set(key, menu, Duration.ofSeconds(180));
        return menu;
    }

    @Override
    public Menu insertMenu(Menu menu) {
        log.info("insert.before: {}" + menu);
        menuMapper.insert(menu);
        log.info("insert.after: {}" + menu);
        //写入redis
//        ValueOperations vo = redisTemplate.opsForValue();
        valueOperations.set(String.valueOf(menu.getId()), menu);
        return menu; //这里返回的menu会相对于参数多个id值
    }

    @Override
    public Menu updateMenu(Menu menu) {
        //更新mysql
        menuMapper.updateById(menu);
        //更新redis
//        ValueOperations vo = redisTemplate.opsForValue();
        valueOperations.set(String.valueOf(menu.getId()), menu);
        return null;
    }
}
