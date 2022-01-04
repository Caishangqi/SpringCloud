package com.jt.provider.service;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
@Slf4j
@Component
/**
 * 基于ResourceBlockHandler中的相关方法(例如call方法，但方法必须是静态)，
 * 处理@SentinelResource注解描述的方法上出现的限流异常
 */
public class ResourceBlockHandler {

    /**
     * 方法中异常的类型必须为BlockException类型
     * @param exception
     * @return
     */
    public static String call(BlockException exception) {
        log.error("exception {} ", "@SentinelResource 描述的方法执行时被限流了");
        return "访问过于频繁";
    }

}
