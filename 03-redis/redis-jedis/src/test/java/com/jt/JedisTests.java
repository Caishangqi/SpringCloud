package com.jt;

import org.junit.Test;
import redis.clients.jedis.Jedis;

public class JedisTests {

    @Test
    public void testGetConnection(){
        //假如不能连通,要注释掉redis.conf中 bind 127.0.0.1,
        //并将protected-mode的值修改为no,然后重启redis再试
        Jedis jedis=new Jedis("192.168.126.129",6379);
        //jedis.auth("123456");//假如在redis.conf中设置了密码
        String ping = jedis.ping();
        System.out.println(ping);
    }

}
