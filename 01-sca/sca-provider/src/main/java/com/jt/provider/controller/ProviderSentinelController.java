package com.jt.provider.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.jt.provider.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;


@RestController
@RequestMapping("/provider")
public class ProviderSentinelController {


    @GetMapping("/sentinel01")
    public String deSentinel01() {

        return "test sentinel 01";

    }

    @GetMapping("/sentinel02")
    public String deSentinel02() {

        return "test sentinel 02";

    }

    @Autowired
    private ResourceService resourceService;

    @GetMapping("/sentinel03")
    public String deSentinel03() { //链路 /provider/sentinel03-->doGetResource

        return resourceService.doGetResource();


    }

    @GetMapping("/sentinel04")
    public String doSentinel04() { //链路 /provider/sentinel04-->doGetResource

        resourceService.doGetResource();
        return "test sentinel 04";

    }

    /**
     * 通过此方法模拟慢调用，或不稳定资源(经常出异常)
     *
     * @return AtomicXxx是Jdk1.5以后推出的一些原子操作，
     * 底层基于CAS算法(乐观锁方式)P保证了线程安全
     */

    private AtomicInteger atomicInteger = new AtomicInteger(1);

    @GetMapping("/sentinel05")
    public synchronized String deSentinel05() throws InterruptedException {
        int count = atomicInteger.getAndIncrement(); //先取值再递增
        if (count % 2 == 0) {
            Thread.sleep(200); //模拟耗时
        }
        return "test sentinel 05";
    }

    //http://localhost:8081/provider/doFindById?id=10
    @GetMapping("/doFindById")
    @SentinelResource("resource")
    public String doFindById(@RequestParam Integer id) {
        return "Get Resource by " + id;
    }

}
