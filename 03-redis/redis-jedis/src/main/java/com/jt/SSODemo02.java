package com.jt;

import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * 基于某个活动id的简易投票系统设计及实现
 * 1)定义活动
 * 2)基于活动id进行投票(投票结果存储到redis中)
 * 3)同一个人基于同一个活动id不能重复投票，假如已参与过投票，再点击投票则取消投票
 * 4)可以获取投票人数
 */
public class SSODemo02 {

    public static void main(String[] args) {
        //定义活动
        String activityId = "201";
        String user1 = "1001";
        String user2 = "1002";
        //基于活动
        boolean flag = doVote(activityId, user1); //true
        flag = doVote(activityId, user2); //false
        //获取总票数
        Long count = doCount(activityId);
        //查看哪些人参与了投票
        System.out.println(doGetUsers(activityId));
    }

    /**
     * 执行投票逻辑
     *
     * @param activityId 活动id
     * @param userId     用户id
     * @return 是否投票成功
     */
    private static boolean doVote(String activityId, String userId) {

        //参数校验
        //执行投票逻辑
        //连接redis
        Jedis jedis = JedisDataSource.getJedisPool();
        //检查是否已存于过投票，基于检查结果执行投票或取消投票
        Boolean flag = jedis.sismember(activityId, userId);//set类型
        if (flag) { //true表示已经参与过投票，此时可以取消投票
            jedis.srem(activityId, userId);
            jedis.close();
            return false;
        }
        //假如没有参与过投票，则直接投票
        jedis.sadd(activityId, userId);
        jedis.close();
        return true;

    }

    /**
     * 基于活动id统计投票总数
     *
     * @param activityId
     * @return
     */
    static Long doCount(String activityId) {
        //参数校验
        //获取投票总数
        //连接redis
        Jedis jedis = JedisDataSource.getJedisPool();
        //获取总数
        Long count = jedis.scard(activityId);
        //释放资源
        jedis.close();
        //返回投票总数
        return count;
    }

    /**
     * 获取投票的用户
     * @param activityId
     * @return
     */
    static Set<String> doGetUsers(String activityId) {

        //参数校验
        Jedis jedis = JedisDataSource.getJedisPool();
        //获取投票用户
        Set<String> members = jedis.smembers(activityId);
        jedis.close();
        return members;

    }

}
