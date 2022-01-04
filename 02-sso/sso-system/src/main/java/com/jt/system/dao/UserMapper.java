package com.jt.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.system.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Mapper 注解由Mybatis官方提供，用于告诉mybatis底层为此注解描述的接口
 * 创建其实现类及对象，然后将对象交给spring管理。假如我们自己写实现类，
 * 可以在类中由spring为我们注入一个SqlSession对象，然后通过SqlSession
 * 实现与数据库会话(交互)。
 * 注意：@Mapper描述的数据层接口，要默认放在项目启动类所在包或子包中。
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 基于用户名查询用户信息。
     *
     * @param username
     * @return
     */
    @Select(" select id,username,password,status" +
            " from tb_users" +
            " where username=#{username}")
    User selectUserByUsername(String username);

    @Select(" select distinct m.permission " +
            " from tb_user_roles ur join tb_role_menus rm on ur.role_id=rm.role_id " +
            " join tb_menus m on rm.menu_id=m.id " +
            " where ur.user_id=#{userId}")
    List<String> selectUserPermissions(Long userId);

}
