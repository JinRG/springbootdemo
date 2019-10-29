package com.jrg.demo.conf;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jrg.demo.shiro.MyRealm;

/*配置shiro 需要注册三个bean
1、定义Realm，最为核心类，认证授权规则定义，数据源比对的来源
2、定义SecurityManager
3、配置ShiroFilterFactoryBean
关系 ：  ShiroFilterFactoryBean依赖SecurityManager依赖Realm
注：此配置类别忘了加上@Configuration注解，否则@Bean注册无效
*/
@Configuration
public class ShiroConfig {
	//自定义realm，默认下@Bean注册名称与类名相同，但首字母变小写,为了明确点还是标识下名字吧
	@Bean(name="myRealm")
	public MyRealm getRealm(){
		return new MyRealm();
	}
	//定义SecurityManager,此类构造需要关联realm,可以在参数中传入(加上注解@Qualifier,即可拿到对应的bean)
	@Bean(name="securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("myRealm") MyRealm myRealm){
		DefaultWebSecurityManager securityManager  = new DefaultWebSecurityManager();
		//关联realm
		securityManager.setRealm(myRealm);
		return securityManager;
	}
	/**
	 * ShiroFilterFactoryBean 此类需要关联管理器SecurityManager
	 * 说白了shiro就是利用了拦截器进行权限管理，在这里我们可以使用一些shiro的一些内置的拦截器
	 * 		anon:可匿名访问
	 *  	authc：必须经过认证 ，一般都是登录认证，因此我们会在controller登录方法里面调用shiro的登录方式
	 *  	user:如果使用了rememberMe功能可以直接访问
	 *      
	 * 		perms:必须得到资源权限才能访问
	 * 		role:必须得到角色权限才能访问
	 * 		最后两个是权限管理设置，需要指定shiro控制访问该路径所需要的权限，
	 * shiro实际工作原理：是否匿名访问?->是则不拦截请求直接通过，否则判断是否需要认证->认证通过再判断是否有权限，否则拦截返回
	 * 注意：不要忘了加@Bean,否则shiro无法找到这个bean，也就无法起到拦截效果
	 */
	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager defaultWebSecurityManager){
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		//关联管理器SecurityManager
		shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
		//注意拦截顺序从上往下，千万千万注意带有通配符的即范围越大的放在越后面，比如
		//比如你将/user/*放在最前面，那么user下有所路径都会匹配到这个拦截器，后面就不会匹配了，这就导致后面范围小的拦截配置失效
		Map<String,String> filters = new LinkedHashMap<>();
		filters.put("/index", "anon");//访问/index可匿名访问，即不需要认证和权限
		filters.put("/loginindex", "anon");
		filters.put("/doLogin", "anon");
		filters.put("/loginout", "anon");
	
		//授权拦截（实际项目中，授权的配置不会再这里硬编码，因为访问路径实在是多，在这里一一配置未免太多了）
		//当用户访问/user/add,需要进行授权判断有无权限：/user/add，具体逻辑：
		//在realm里面的授权方法里面查询出来用户所有权限是否包含/user/add，若没有则会自动跳转至未授权页面(可以自己指定)
		filters.put("/user/add", "perms[/user/add]");
		filters.put("/user/update", "perms[/user/update]");
		filters.put("/user/deleteajax", "perms[/user/deleteajax]");
		
		filters.put("/user/*", "authc");//访问/user/路径下所有路径则需要认证了，否则被拦截,然后自动跳转至/login.jsp(可以自己配置这个路径)
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filters);
		
		shiroFilterFactoryBean.setLoginUrl("/loginindex");//未认证成功则跳转至登录页面（controller路径）
		shiroFilterFactoryBean.setUnauthorizedUrl("/unauth");//未授权页面（controller路径）
		return shiroFilterFactoryBean;
	}
	
}
