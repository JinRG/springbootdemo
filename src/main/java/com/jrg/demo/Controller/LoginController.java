package com.jrg.demo.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jrg.demo.aop.TestAnnotation;

@Controller
public class LoginController {
	@TestAnnotation("测试注解TestAnnotation")
	@GetMapping("/doLogin")
    public String doLogin(String username, String password,Model model) {
        Subject subject = SecurityUtils.getSubject();
        try {
        	System.out.println("访问参数："+username+password);
        	//执行login之后，会回调realm里面的认证方法
            subject.login(new UsernamePasswordToken(username, password));
            //登录成功则跳转至首页
            return "redirect:index";
            
        } catch (UnknownAccountException e) {
        	model.addAttribute("errormsg", "用户名不存在");
            return "login";
        }catch (IncorrectCredentialsException e) {
        	model.addAttribute("errormsg", "密码不正确");
            return "login";
        }catch(AuthenticationException e){
        	e.printStackTrace();
        	model.addAttribute("errormsg", "未知错误");
            return "login";
        }
    }
	@GetMapping("/user/deleteajax")
	@ResponseBody
    public String todelete(String name) {
		System.out.println("执行删除");
		return "删除成功";
    }
	@GetMapping("/user/add")
	public String add() {
	    return "user/add";
	}
	@GetMapping("/user/update")
	public String update() {
		System.out.println("执行更新");
	    return "/user/update";
	}
	@TestAnnotation("测试注解TestAnnotation")
	@GetMapping("/loginindex")
	public String tologin() {
	    return "login";
	}
	@GetMapping("/index")
	public String toindex() {
	    return "index";
	}
	@GetMapping("/unauth")
    public String unauthorizedurl() {
		return "unauth";
    }
	@GetMapping("/login")
    public String login(String name) {
		return "login";
    }
	@GetMapping("/errortest")
    public String errortest(String name) {
		int i = 1/0;
		return "login";
    }
}
