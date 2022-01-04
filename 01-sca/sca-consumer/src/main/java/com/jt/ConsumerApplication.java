package com.jt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
/**
 * @EnableFeignClients 注解用于描述一些配置类，告诉系统底层
 * 启动时为@FeignClient注解描述的接口创建实现类及对象，然后交给
 * Spring管理。
 */
@SpringBootApplication
@EnableFeignClients
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    /**
     * 由spring创建RestTemplate对象，
     * 我们可以基于此对象进行远端服务调用，
     * 例如sca-consumer调用sca-provider
     *
     * @return
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * 当我们使用 @LoadBalanced注解描述RestTemplate对象时，
     * 系统底层再基于RestTemplate进行远程服务调用时，会被一个
     * 拦截器(LoadBalancerInterceptor)拦截到，然后进行功能增强，这里的功能
     * 增强指的是，基于loadBalancerClient对象进行服务实例获取，
     * 而这个服务实例获取的过程，底层会采用负载均衡。
     *
     * @return
     */

    @Bean
    @LoadBalanced
    public RestTemplate loadBalancedRestTemplate() {
        return new RestTemplate();
    }

    /**
     * 指定默认的负载均衡策略,但是这种配置方式不够灵活，
     * 将来项目上线后，我们所有的类都会打包成一个jar包，
     * 假如项目在运行过程中，我们想调整负载均衡，就不太
     * 方便了，我们可以将这些配置写到配置文件，最后更新
     * 到配置中心(Config Center)
     */

    //    @Bean
    //    public IRule rule(){
    //        return new RandomRule();
    //    }

    //底层默认逻辑实现，@ConditionalOnMissingBean注解配合@Bean注解一起使用时，
    // 会在没有指定Bean时，才去创建这个Bean对象。
    //    @Bean
    //    @ConditionalOnMissingBean(IRule.class)
    //    public IRule rule(){
    //        return new BestAvailableRule();
    //    }
}
