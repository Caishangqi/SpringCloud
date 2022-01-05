package com.jt.resource.aspect;

import com.jt.resource.annotation.RequiredLog;
import com.jt.resource.pojo.Log;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 自定义用户行为日志切面
 */
@Slf4j
@Component
@Aspect
public class LogAspect {

    /**
     * 切入点的定义，采用了注解方式的切入点表达式，也就是说使用
     * RequiredLog注解描述方法时，被描述的方法就是切入点方法，
     * 我们要在这样的方法上进行功能增强-锦上添花。
     * 这里的doLog方法，只是启动了承载@Pointcut注解的作用，方法
     * 体中不需要写任何内容。
     */
    @Pointcut("@annotation(com.jt.resource.annotation.RequiredLog)")
    public void doLog() {
    }

    /**
     * 通知方法，在方法内可以手动调用目标方法执行链，在执行
     * 链执行过程中获取用户行为日志，并进行封装和记录。
     *
     * @param joinPoint ProceedingJoinPoint为连接点对象，这个类型对象
     *                  只能应用在@Around注解描述的方法上，并且可以通过
     *                  这个连接点对象获取目标方法信息，调用目标方法执行链。
     * @return 目标方法(切入点方法)执行结果
     * @throws Throwable
     */
    @Around("doLog()")
    public Object doAround(ProceedingJoinPoint jp) throws Throwable {
        long time = -1l;
        int status = 1;//ok
        String error = "";
        //获取目标方法执行时间
        long t1 = System.currentTimeMillis();
        System.out.println("Before:" + t1);
        try {
            //调用目标执行链，这个执行链的终端是你的切入点方法
            Object result = jp.proceed();
            //获取目标方法执行结束的时间
            long t2 = System.currentTimeMillis();
            System.out.println("After:" + t2);
            time = t2 - t1;
            return result;
        } catch (Throwable e) {
            long t3 = System.currentTimeMillis();
            System.out.println("Exception:" + t3);
            time = t3 - t1;
            status = 0;
            error = e.getMessage();
            throw e;
        } finally {
            //无论目标方法执行是否成功，我们都要记录日志
            saveLog(time, status, error, jp);
        }
    }

    private void saveLog(long time, int status, String error, ProceedingJoinPoint jp) throws NoSuchMethodException, IOException {
        //1.获取用户行为日志(...)
        //1.1获取登录用户名
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //1.2获取当前请求的ip(这个ip需要通过HttpServletRequest对象进行获取)
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String ip = requestAttributes.getRequest().getRemoteAddr();

        //1.3获取当前用户的操作
        //1.3.1获取目标对象类型(切入点方法所在类的类型)
        Class<?> targetClass = jp.getTarget().getClass();

        //1.3.2获取目标方法(切入点方法)
        //通过连接点对象获取方法签名对象(此对象中包含了目标方法信息)
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method targetMethod =
        targetClass.getDeclaredMethod(signature.getName(),signature.getParameterTypes());
        //1.3.3获取目标方法上的RequiredLog注解
        RequiredLog requiredLog = targetMethod.getAnnotation(RequiredLog.class);
        //1.3.4获取注解中定义的操作名
        String operation = requiredLog.value();
        //1.4获取用户访问了什么方法(全类名+方法名)
        String targetMethodName = targetClass.getName() + "." + targetMethod.getName();
        //1.5获取执行方法时传递的实际参数
        Object[] args = jp.getArgs();
        String params = new ObjectMapper().writeValueAsString(args);
        //2.封装用户行为日志(Log)
        Log userLog = new Log();
        //谁
        userLog.setUsername(username);
        userLog.setIp(ip);
        //在什么时间
        userLog.setCreatedTime(new Date());
        //执行了什么操作
        userLog.setOperation(operation);
        //访问了什么方法
        userLog.setMethod(targetMethodName);
        //传递什么参数
        userLog.setParams(params);
        //耗时是多少
        userLog.setTime(time);
        //执行状态以及错误信息
        userLog.setStatus(status);
        userLog.setError(error);

        //3.记录用户行为日志(控制台，文件，数据库)
        log.debug("log {}", userLog);

    }


}
