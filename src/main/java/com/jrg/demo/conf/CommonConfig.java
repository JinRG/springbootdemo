package com.jrg.demo.conf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ServletComponentScan //配置这个注解就可以扫描@WebServlet、@WebFilter和@WebListener，使其生效
@MapperScan(basePackages = "com.jrg.demo.dao")//扫描mapper接口，无需在mapper接口上增加@Mapper注解
public class CommonConfig {
	
}
