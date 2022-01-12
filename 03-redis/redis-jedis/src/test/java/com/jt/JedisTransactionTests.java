package com.jt;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;

public class JedisTransactionTests {
    @Test
    public void testTransaction() {
        Jedis jedis = JedisDataSource.getJedisPool();
        jedis.set("Caizii", "500");
        jedis.set("Abba", "100");
        Transaction transaction = jedis.multi();

        try {
            transaction.decrBy("Abba", 100);
            transaction.incrBy("Caizii", 100);
            transaction.exec();
        } catch (Exception exception) {
            exception.printStackTrace();
            transaction.discard();
        } finally {
            jedis.close();
            List<String> mget = jedis.mget("Caizii", "Abba");
            System.out.println(mget);
        }



    }
}
