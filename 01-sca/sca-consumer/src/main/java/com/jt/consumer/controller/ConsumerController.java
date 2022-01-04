package com.jt.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {
    /**
     * 由spring帮我们为RestTemplate属性注入一个值，
     * 然后我们通过此对象进行远端服务调用。
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 负载均衡客户端对象，基于此对象可以获取nacos中的服务实例，
     * 并且通过负载均衡算法从所有实例中取一个实例。
     */
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Value("${spring.application.name:sca-consumer}")
    private String appName;

    /**
     * 当客户端(浏览器)访问
     * http://localhost:8090/consumer/doRestEcho1
     * 时，在此方法内部基于RestTemplate对象进行远端服务调用
     *
     * @return
     */
    @GetMapping("/consumer/doRestEcho1")
    public String doRestEcho1() {
        //你要调用的远端服务的url
        String url = "http://localhost:8081/provider/echo/{msg}";
        //执行远端服务调用
        return restTemplate.getForObject(
                url,
                String.class, //远端url对应的方法的返回值类型(响应数据类型)
                "consumer"
        );
    }

    @GetMapping("/consumer/doRestEcho2")
    public String doRestEcho2() {
        //从nacos获取服务实例(这里的choose方法内置了负载均衡算法)
        ServiceInstance serviceInstance = loadBalancerClient.choose("sca-provider");

        String ip = serviceInstance.getHost();
        int port = serviceInstance.getPort();

        //String url = "http://" + ip + ":" + port + "/provider/echo/" + appName;
        //String 的整理格式api
        String url = String.format("http://%s:%s/provider/echo/%s",ip,port,appName);
        //执行远端服务调用
        return restTemplate.getForObject(
                url,
                String.class //远端url对应的方法的返回值类型(响应数据类型)
        );
    }

    @Autowired
    private RestTemplate loadBalancedRestTemplate;

    @GetMapping("/consumer/doRestEcho3")
    public String doRestEcho3() {
        //定义远程调用的url
        String url = String.format("http://sca-provider/provider/echo/%s",appName);
        //执行远端服务调用
        return loadBalancedRestTemplate.getForObject(
                url,
                String.class //远端url对应的方法的返回值类型(响应数据类型)
        );
    }
}
