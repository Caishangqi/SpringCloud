package com.jt.consumer.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "sca-provider",contextId = "RemoteOtherService")
public interface RemoteOtherService {

    @GetMapping("/aaa")
    String doSome();

}
