package com.jt.system.dao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.system.pojo.Log;
import org.apache.ibatis.annotations.Mapper;
/**
 * 用户行为日志的数据访问逻辑对象(DAO),通过这个对象
 * 完成用户行为日志的持久化操作。
 */
@Mapper
public interface LogMapper extends BaseMapper<Log>{
}
