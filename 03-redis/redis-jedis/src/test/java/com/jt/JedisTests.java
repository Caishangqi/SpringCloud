package com.jt;

import org.junit.Test;
import redis.clients.jedis.Jedis;

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

}
