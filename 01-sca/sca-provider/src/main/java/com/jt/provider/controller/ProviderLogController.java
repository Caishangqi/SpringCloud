package com.jt.provider.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope//此注解描述类时，用于告诉系统底层，当配置中心内容发生变化，此对象要重新重建
@RestController
public class ProviderLogController {

    @Value("${logging.level.com.jt:debug}")
    //读的是配置中心的，但是配置中心刷新不会刷新属性值，需要加注解
    private String logLevel;

    //import org.slf4j.Logger; //slf4j是基于门面模式设计的一套API
    //import org.slf4j.LoggerFactory;
    //构建日志对象
    private static final Logger log = LoggerFactory.getLogger(ProviderLogController.class);

    //http://localhost:8081/provider/log/doLog01
    @GetMapping("/provider/log/doLog01")
    public String doLog01() {

        System.out.println("==doLog01==");
        //trace<debug<info<warn<error
        log.trace("==trace==");
        log.debug("==debug==");
        log.info("==info=="); //默认
        log.warn("==warn==");
        log.error("==error==");
        return "log config test 01";
    }

    @GetMapping("/provider/log/getLogLevel")
    public String getLogLevel() {
        return "log level is " + logLevel;
    }

    @GetMapping("/provider/log/doLog02")
    public String doLog02() {
        log.info("log level is  {}",logLevel);//内部感知
        return "log level is "+logLevel;//外部感知
    }
}
