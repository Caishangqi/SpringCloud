package com.jt.provider.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderController {

    //@Value默认读取项目配置文件中配置的内容
    //8080为没有读到server.port的值时,给定的默认值
    @Value("${server.port:8080}")
    private String server;

    //http://localhost:8081/provider/echo/tedu
    //在sca-consumer服务中对这个服务进行访问
    @GetMapping("/provider/echo/{msg}")
    public String doRestEcho1(@PathVariable("msg") String msg) throws InterruptedException {

//        Thread.sleep(5000);
        return server + "say hello " + msg;
    }
}
