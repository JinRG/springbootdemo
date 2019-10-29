package com.jrg.demo.aop;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

/**
 * 定义一个关于打印日志的切面
 * @author huyj
 *
 */
/*@Aspect
@Component*/
@Slf4j
public class WebLogAspect {
	 /*定义切点：方法名任意 没啥特殊含义就是取个名字
	  * 
	  * execution表达式解释：
	  * 第一个*表示方法返回值的类型任意.
	   第二个中间的一连串包名表示路径.
	   包名后面的表示..表示当前包及子包.
	   第二个星号表示的是所有的类.
	 .*(..)表示的是任何方法名,括号里面表示参数,两个点表示的是任意参数类型*/
	 @Pointcut("execution(public * com.jrg.demo.Controller..*.*(..))")//切入点描述 这个是controller包的切入点
	 public void controllerLog(){}//签名，可以理解成这个切入点的一个名称，这样我们就可以用这个签名代替切入点，可以联系下面的Before注解
	 
	 
	 @Before("controllerLog()") //在切入点的方法run之前要干的,执行的就是括号里面指定的方法
    public void logBeforeController(JoinPoint joinPoint) {
        
        
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();//这个RequestContextHolder是Springmvc提供来获得请求的东西
        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
        
         // 记录下请求内容
        log.info("################请求URL : " + request.getRequestURL().toString());
        log.info("################请求方法 : " + request.getMethod());
        log.info("################IP地址 : " + request.getRemoteAddr());
        log.info("################请求入参 : " + Arrays.toString(joinPoint.getArgs()));
        
        //下面这个getSignature().getDeclaringTypeName()是获取包+类名的   然后后面的joinPoint.getSignature.getName()获取了方法名
        log.info("################controller方法名 : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
    }
	 
}
