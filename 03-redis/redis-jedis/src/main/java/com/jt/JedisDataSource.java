package com.jt;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 创建一个数据源对象，此对象要对外提供
 * 一个Jedis连接池(JedisPool)对象，
 * 且只能是一个？
 * 请问你该如何设计这个DataSource对象。
 */
public class JedisDataSource {

    // 方案1：类加载时则创建池对象
    //    private static JedisPool jedisPool;
    //    static{
    //        //1.1构建连接池的配置
    //        JedisPoolConfig config=new JedisPoolConfig();
    //        config.setMaxTotal(128);
    //        config.setMaxIdle(16);
    //        //1.2创建连接池(将来这个池在内存中有一份就够了)
    //        jedisPool= new JedisPool(config,"192.168.126.129", 6379);
    //    }
    //    public static JedisPool getJedisPool(){
    //        return jedisPool;
    //    }

    //方案2:何时应用jedis连接，何时创建这个连接池，但是这个连接池只能创建一次，
    //请问如何设计
    //volatile用于修饰属性，可以：
    //1)禁止指令重排序
    //2)保证多线程之间可见性(一个线程对这个变量的值修改后，其它的线程立刻可见。)
    //3)但不保证原子性。
    private static volatile JedisPool jedisPool;
    public static Jedis getJedisPool(){
        if(jedisPool==null) {
            synchronized (JedisDataSource.class) {
                if(jedisPool==null) {
                    //1.1连接池配置(可选，
                    // 具体配置多少有业务决定)
                    JedisPoolConfig config = new JedisPoolConfig();
                    config.setMaxTotal(128);
                    config.setMaxIdle(16);
                    //1.2创建连接池(将来这个池在内存中有一份就够了)
                    jedisPool = new JedisPool(config, "192.168.126.129", 6379);
                    //47行创建对象过程分析(理论上是如下这个步骤，但是JVM内部会有指令重排序 不一定按照 1,2,3,4 执行)
                    //1.分配内存
                    //2.属性初始化
                    //3.调用构造方法
                    //4.为jedisPool变量赋值

                    /*如果不添加volatile，一个线程进入创建JedisPool时内部会对创建过程进行优化，比如先执行1，4则有
                    * 变量值，其他线程进入判断时会判定该对象的属性有值，但是这个对象内部还没有进行属性初始化和调用构造
                    * 方法，所以在执行 getResource 时会发生错误。*/
                }
            }
        }
        return jedisPool.getResource();
    }


}
