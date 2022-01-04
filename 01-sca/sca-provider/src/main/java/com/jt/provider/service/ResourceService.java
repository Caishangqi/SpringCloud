package com.jt.provider.service;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.stereotype.Service;

@Service
public class ResourceService {


    /**
     * @SentinelResource 用于描述链路中的资源方法，
     * 其中
     * 1)value属性的值用于作为链路节点的名称(自己随意定义)
     * 2)blockHandlerClass 用于指定处理链路限流异常的类型
     * 3)blockHandler用于指定blockHandlerClass属性指定的类型中的异常处理方法
     * @return
     */
    @SentinelResource(value = "deGetResource", blockHandlerClass = ResourceBlockHandler.class,blockHandler = "call")
    public String doGetResource() {
        //TODO 访问数据库资源
        return "do get resource";
    }

}
