package com.jt.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基于此对象封装用户行为日志?何为用户行为日志？
 * 记录谁在什么时间执行了什么操作,访问了什么方法,
 * 传递了什么参数,访问时长是多少，最终状态如何，假如出错了错误信息是什么。
 */
@Data
@TableName("tb_logs")
public class Log implements Serializable {
    private static final long serialVersionUID = 3054471551801044482L;
    /**自增长id*/
    @TableId(type = IdType.AUTO)
    private Long id;
    /**登录成功后的用户*/
    private String username;
    /**用户执行的操作*/
    private String operation;
    /**用户访问了什么方法*/
    private String method;
    /**访问方法时传递的参数*/
    private String params;
    /**访问时长*/
    private Long time;
    /**客户端用户ip地址*/
    private String ip;
    /**访问资源的时间，也可以理解为创建日志的时间*/
    @TableField("createdTime")
    private Date createdTime;//created_time
    /**访问资源是否成功*/
    private Integer status;
    /**假如访问资源失败，这个error记录的就是异常信息*/
    private String error;
}