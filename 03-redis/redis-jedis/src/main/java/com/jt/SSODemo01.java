package com.jt;

import redis.clients.jedis.Jedis;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 单点登录Demo
 * 1)完成用户身份认证
 * 2)完成用户资源访问的授权。
 * SSO系统具体实现
 * 1)用户登录成功后将登录状态信息存储到redis,要返回给用户一个token。(UUID)
 * 2)用户登录成功，可携带token访问资源，资源服务器检查token有效性，假如无效则提示重新登录。
 * 3)判定用户是否有资源的访问权限，有权限则授权访问。
 */

public class SSODemo01 {
    private static Jedis jedis = JedisDataSource.getJedisPool();

    /**
     * 执行登录逻辑
     */
    public static String login(String username, String password) {
        //判定参数的有效性
        if (username == null || "".equals(username))
            throw new IllegalArgumentException("(x) username can not be empty");
        //判断用户是否存在
        if (!username.equals("caizii"))
            throw new IllegalArgumentException("(x) user is not exist");
        //密码是否正确 一般是要加密后对比
        if (!password.equals("123456"))
            throw new IllegalArgumentException("(x) password wrong!");
        //将用户信息写入到redis服务器 (key为一个随机字符串，值为用户对象信息)
        jedis = JedisDataSource.getJedisPool();
        String token = UUID.randomUUID().toString();
        jedis.hset(token, "username", username);
        jedis.hset(token, "permissions", "sys:res:view,sys:res:update");

        jedis.expire(token, 10); //设置十秒有效期

        //返回token(UUID)
        return token;
    }

    /**
     * 执行资源访问逻辑
     */
    public static Object doGetResource(String token) {
        //校验token是否为空
        if (token == null || "".equals(token))
            throw new IllegalArgumentException("(!) please login first");
        //基于token查询用户信息
        Map<String, String> map = jedis.hgetAll(token);
        //判定用户是否以登录
        if (map == null || map.size() == 0)
            throw new IllegalArgumentException("(!) 用户登录超时，请重新登录");

        //判断用户是否有资源访问的权限
        String permissions = map.get("permissions");
        if (permissions == null || permissions.equals(""))
            throw new RuntimeException("(x) 您无权访问这个资源");
        String[] permsArray = permissions.split(",");
        List<String> permsList = Arrays.asList(permsArray);
        if (!permsList.contains("sys:res:view"))
            throw new RuntimeException("(x) 您无权访问这个资源");

        return "your resource";
    }

    public static void main(String[] args) {
        //执行登录操作
        String username = "caizii";
        String password = "123456";
        String token = login(username, password);
        System.out.println("(+) login success, token is " + token);

        //携带令牌去访问资源
        Object resource = doGetResource(token);
        System.out.println(resource);
    }
}
