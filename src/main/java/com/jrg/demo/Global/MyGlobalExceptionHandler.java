package com.jrg.demo.Global;

import java.util.HashMap;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 增强版Controller注解
 * @ControllerAdvice的应用（全局数据处理、全局数据绑定、全局异常处理）
 * 全局异常处理
 * @author huyj
 *
 */
@ControllerAdvice
public class MyGlobalExceptionHandler {
	//所有controller抛出的Exception都会被这个捕捉到,注意是controller抛出的异常
	@ExceptionHandler(Exception.class)
	public ModelAndView customException(Exception e){
		ModelAndView model = new ModelAndView();
		model.setViewName("/error/cutomerror.html");
		model.addObject("message",e.getMessage() );
		return model;
	}
	//因为如果是ajax访问，未授权不会自动跳转到未授权页面，因此我们在这捕获未授权的异常
	/*@ExceptionHandler(Exception.class)
	@ResponseBody
	public String UnauthorizedException(Exception e){
		System.out.println("捕获到未授权异常:");
		e.printStackTrace();
		return "您未授权";
	}*/
	//所有controller都可以访问到这里定义的数据（接收方式：用Model作为入参）
	@ModelAttribute(name = "md")
	public HashMap<String, Object> globalData(){
	  	HashMap<String, Object> map = new HashMap<>();
        map.put("age", 99);
        map.put("gender", "男");
        return map;
	}
	//与controller中以@ModelAttribute进行注解的参数类型进行绑定，参数对应的类型的所有属性会加上user.前缀
	//于是要映射user对象的属性，也必须加上user.前缀进行映射
	@InitBinder("user")
	public void b(WebDataBinder binder) {
	    binder.setFieldDefaultPrefix("user.");
	}
	@InitBinder("person")
	public void a(WebDataBinder binder) {
	    binder.setFieldDefaultPrefix("person.");
	}
}
