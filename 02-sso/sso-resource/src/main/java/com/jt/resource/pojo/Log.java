package com.jt.resource.pojo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * 基于此对象封装用户行为日志?
 * 谁在什么时间执行了什么操作,访问了什么方法,传递了什么参数,访问时长是多少.
 */
@Data
public class Log implements Serializable {
    private static final long serialVersionUID = 3054471551801044482L;
    private Long id;
    private String username;
    private String operation;
    private String method;
    private String params;
    private Long time;
    private String ip;
    private Date createdTime;
    private Integer status;
    private String error;
}