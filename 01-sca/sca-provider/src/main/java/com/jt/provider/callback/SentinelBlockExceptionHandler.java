package com.jt.provider.callback;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
@Lazy //需要时创建
@Component
public class SentinelBlockExceptionHandler implements BlockExceptionHandler {

    public SentinelBlockExceptionHandler(){
        System.out.println("(+) SentinelBlockExceptionHandler created");
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
        // Return 429 (Too Many Requests) by default.
        response.setStatus(429);
        //设置响应数据编码

        response.setCharacterEncoding("utf-8");

        //告诉浏览器向它响应的内容类型，以及编码方式
        response.setContentType("text/html;charset=utf-8");
        response.setHeader("异常", "type Error");
        PrintWriter out = response.getWriter();

        Map<String, Object> map = new HashMap<>();
        map.put("status", 429);
        String message = "Blocked by Sentinel (flow limiting)";
        if (e instanceof DegradeException) {
            message = "服务暂不稳定，稍等片刻";
        } else if (e instanceof FlowException) {
            message = "服务访问太频繁，稍等片刻";
        }

        // out.print("<h1>请求被限流</h1>");
        map.put("message", message);
        //将map对象转换为json格式字符串
        //这个ObjectMapper对象属于jackson中的api，
        //这组api在spring-boot-starter-web中自动加载了
        String jsonResult = new ObjectMapper().writeValueAsString(map);
        out.print(jsonResult);
        out.flush();
        out.close();

    }
}
