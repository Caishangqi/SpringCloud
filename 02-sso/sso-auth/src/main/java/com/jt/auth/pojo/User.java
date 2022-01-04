package com.jt.auth.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 通过此对象封装从远端服务(sso-system)响应的用户数据
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String username;
    private String password;
    private String status;

}
