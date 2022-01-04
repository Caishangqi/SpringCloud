package com.jt.system.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 定义Pojo对象，基于此对象存储用户信息。
 * 记住：
 * Java中所有用于存储数据的对象，都建议实现序列化接口，并且添加一个序列化id。
 * 可参考：String,Integer,ArrayList,HashMap,...
 */

@Data //lombok
//@TableName("tb_users") 假如sql语句自己写不需要通过@TableName指定表名
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String username;
    private String password;
    private String status;
}


