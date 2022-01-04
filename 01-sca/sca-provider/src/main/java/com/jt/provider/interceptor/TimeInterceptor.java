package com.jt.provider.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalTime;

public class TimeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        System.out.println("(!) preHandle");
        LocalTime now = LocalTime.now();
        int hour = now.getHour();
        if (hour<6||hour>23) {
            throw new RuntimeException("请在6~23时间范围内访问");
        }
        return true;

    }




}
