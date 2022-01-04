package com.jt.provider.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RefreshScope
@RestController
public class ProviderCacheController {

    @Value("${useLocalCache:false}")
    private boolean useLocalCache;

    @RequestMapping("/provider/cache01")
    public String doUseLocalCache01() {
        return "useLocalCache'value is   " + useLocalCache;
    }


    //目标：设计1个cache，我们要将从数据库取到数据存储cache中，下次取数据从cache取。
    //1)要保证数据的正确(线程安全)
    //2)要保证效率
    private List<String> cache = new CopyOnWriteArrayList<>();

    @RequestMapping("/provider/cache02")
    public List<String> doUseLocalCache02() {
        //1.配置中心useLocalCache值为false，则直接从数据库取

        if (!useLocalCache) {
            System.out.println("(!) 不使用缓存配置直接读取数据库");
            return Arrays.asList("A", "B", "C");
        }
        //2.配置中心useLocalCache值为true，则执行如下步骤

        if (cache.isEmpty()) {

            synchronized (this) {
                if (cache.isEmpty()) {
                    System.out.println("(!) 使用本地缓存，但是本地缓存是空的，查找数据库");
                    //查找数据库
                    List<String> list = Arrays.asList("A", "B", "C");
                    //将查询到的数据存储缓存中
                    cache.addAll(list);

                }

            }
        }

        //2.1.从cache取数据，假如有则直接返回。
        System.out.println("(!) 使用缓存输出数据库内容");
        return cache;
    }


}
