package com.jt.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.Menu;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    //.....这里先使用BaseMapper中提供的方法即可

}
