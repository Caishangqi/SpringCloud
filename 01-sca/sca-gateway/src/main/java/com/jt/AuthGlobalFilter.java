package com.jt;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 自定义全局过滤器
 */
//@Component 将过滤器交给spring管理，实验过后，注释掉，否则后续每次请求，都需要传指定参数
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    /**
     * 请求过滤方法
     *
     * @param exchange 基于此对象获取请求和响应对象
     * @param chain    过滤链
     * @return 这里的返回值代表一个Publisher对象(消息的发布者)，
     * 可以由此对象帮你进行请求传递。
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {
        //1.获取请求对象
        ServerHttpRequest request = exchange.getRequest();
        //2.获取请求数据
        String username =
                request.getQueryParams().getFirst("username");
        //3.进行认证分析
        //3.1认证失败则拒绝请求继续传递
        if (!"admin".equals(username)) {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        //3.2认证成功继续处理(传递)
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
