package com.jrg.demo.aop;

import java.lang.reflect.Method;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;
@Aspect
@Component
@Slf4j
public class TestAspect {
	 @Pointcut("@annotation(TestAnnotation)") //与注解TestAnnotation绑定一起
	 public void annotationPointcut() {
	 }
	 @Before("annotationPointcut()")
	 public void beforePointcut(JoinPoint joinPoint) {
		 //从JoinPoint对象可以获取到绑定方法的各种参数，包括方法名、类名、入参等等
        MethodSignature methodSignature =  (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        TestAnnotation annotation = method.getAnnotation(TestAnnotation.class);
        String value = annotation.value();//注解里面传的入参
        System.out.println("准备"+value);
        //这个RequestContextHolder是Springmvc提供来获得请求的东西
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
         // 记录下请求内容
        log.info("################请求URL : " + request.getRequestURL().toString());
        log.info("################请求方法 : " + request.getMethod());
        log.info("################IP地址 : " + request.getRemoteAddr());
        log.info("################请求入参 : " + Arrays.toString(joinPoint.getArgs()));//注意这是获取方法里面存在的参数，如果页面传过来了参数方法没有参数接收是获取不到的
        
        //下面这个getSignature().getDeclaringTypeName()是获取包+类名的   然后后面的joinPoint.getSignature.getName()获取了方法名
        log.info("################controller方法名 : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
	 }

}
