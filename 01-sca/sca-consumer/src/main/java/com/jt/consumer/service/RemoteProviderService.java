package com.jt.consumer.service;

import com.jt.consumer.service.factory.ProviderFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @FeignClient 注解用于描述远程服务调用接口，其value属性值有两个层面的含义：
 * 第一个含义就是要你要的远程服务名，第二个含义就是当前bean的名字,假如不使用
 * 这个名字还可以使用contextId指定bean的名字。
 */
@FeignClient(value = "sca-provider",contextId = "RemoteProviderService",fallbackFactory = ProviderFallbackFactory.class)
public interface RemoteProviderService {

    @GetMapping("/provider/echo/{string}")
    String echoMessage(@PathVariable("string") String string);

}
