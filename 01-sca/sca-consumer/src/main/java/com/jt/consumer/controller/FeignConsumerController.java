package com.jt.consumer.controller;

import com.jt.consumer.service.RemoteProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/consumer/")
@RestController
public class FeignConsumerController {

    @Autowired
    private RemoteProviderService remoteProviderService; //没有实现类因为系统底层帮我们构建实现类

    @GetMapping("/echo/{msg}")
    public String doFeignEcho(@PathVariable("msg") String msg){
        return remoteProviderService.echoMessage(msg);
    }

}
