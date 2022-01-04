package com.jt.provider.controller;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
/**
 * 定义一个请求解析器，从请求中获取我们需要的数据
 * 例如:现有一访问路径
 * http://ip:port/path?origin=app1
 * 我们要获取请求url中origin参数的值，然后将这个值与黑白名单进行比较
 */
@Component
public class DefaultRequestOriginParser implements RequestOriginParser {



    /**
     * 此方法用于获取请求数据，但是你要获取请求中的什么数据，由业务决定。
     * 这个方法的返回值会与授权规则中的黑白名单值进行比对，假如这个方法
     * 的返回值，在授权规则的黑名单中，则请求不可以去访问我们系统资源。
     * @param request
     * @return
     */
    @Override
    public String parseOrigin(HttpServletRequest request) {
        //基于请求参数，进行黑白名单设计
        //String origin = request.getParameter("origin");
        //return origin;

        //基于ip地址，进行黑白名单设计
        String remoteAddr = request.getRemoteAddr();
        return remoteAddr;
        //......
    }
}
