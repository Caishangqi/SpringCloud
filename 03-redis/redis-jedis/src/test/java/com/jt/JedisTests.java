package com.jt;

import org.junit.Test;
import redis.clients.jedis.Jedis;

public class JedisTests {

    @Test
    public void testGetConnection(){
        Jedis jedis = new Jedis("192.168.126.129",6379);
        String result = jedis.ping();
        System.out.println(result);
    }
}
