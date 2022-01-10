package com.jt;

import com.google.gson.Gson;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JedisTests {

    /*测试连接Redis*/
    @Test
    public void testGetConnection() {
        //假如不能连通,要注释掉redis.conf中 bind 127.0.0.1,
        //并将protected-mode的值修改为no,然后重启redis再试
        Jedis jedis = new Jedis("192.168.126.129", 6379);
        //jedis.auth("123456");//假如在redis.conf中设置了密码
        String ping = jedis.ping();
        System.out.println(ping);
    }

    /**
     * 测试字符串操作 01
     */
    @Test
    public void testStringOper01() {
        //建立连接
        Jedis jedis = new Jedis("192.168.126.129", 6379);
        //执行redis数据操作
        jedis.set("id", "100");
        jedis.set("name", "Caizii");

        //修改数据
        jedis.incr("id");
        jedis.incrBy("id", 100); //201

        //查询数据
        String id = jedis.get("id");
        String name = jedis.get("name");
        System.out.println("id=" + id + ";name=" + name);

        //删除数据
        jedis.del("name");

        //释放资源
        jedis.close();

    }

    /**
     * 测试字符串操作 01
     */

    @Test
    public void testStringOper02() {
        //建立连接
        Jedis jedis = new Jedis("192.168.126.129", 6379);

        //数据操作 - 将Map对象转换为json字符串 存入 redis

        //构建Map
        Map<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("id", "100");
        hashMap.put("name", "Aba");

        //将Map转换为字符串
        Gson gson = new Gson();
        String json = gson.toJson(hashMap);

        //把字符串写入 redis
        String key = UUID.randomUUID().toString();
        jedis.set(key, json);


        //释放资源
        jedis.close();
    }

    /**
     * 课堂练习：
     * 基于hash类型将testStringOper02中对象写入到redis，
     * 并且尝试进行查询，修改，删除等操作。
     */
    @Test
    public void testHash01() {
        //建立连接
        Jedis jedis = new Jedis("192.168.126.129", 6379);
        //执行hash数据操作

        //新增数据
        String key = UUID.randomUUID().toString();

        jedis.hset(key,"id","500");
        jedis.hset(key, "name", "Jack");

        //修改数据数据
        jedis.hset(key, "name", "Jim");

        //查询数据
        Map<String, String> map = jedis.hgetAll(key);
        System.out.println(map);

        //删除数据
        //jedis.del(key);

        jedis.expire(key,10);

        jedis.close();
    }

}
