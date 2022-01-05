package com.jt.system.service.impl;

import com.jt.system.dao.LogMapper;
import com.jt.system.pojo.Log;
import com.jt.system.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogMapper logMapper;

    /**
     * 写日志的逻辑，并且希望这个写日志的动作通过
     * 一个异步线程去执行(为什么呢，应为用户不关心底层日志记录)，
     * 这个线程不占用web服务(tomcat)的线程资源。
     * @param log
     * @Async 注解描述方法时，此方法为一个异步切入点方法，此方法
     * 会由一个spring内置的线程池中的线程调用执行。
     * 注意：当使用了 @Async注解描述方法时，需要使用这个注解@EnableAsync
     * 对项目的启动类或者配置类进行描述，启动异步机制。
     */

    @Async
    @Override
    public void insertLog(Log log) {
        logMapper.insert(log);
    }
}
