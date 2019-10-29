package com.jrg.demo.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.jrg.demo.dao.PersonMapper;
import com.jrg.demo.entity.Person;

/**
 * 权限验证：doGetAuthorizationInfo ：暂未定义
 * 登录验证： doGetAuthenticationInfo：只有username为javaboy才有权限登录
 * 
 * 是否需要认证？->认证通过-》是否需要权限验证-》权限验证通过
 * @author huyj
 *
 */
public class MyRealm extends AuthorizingRealm {
	
	@Autowired
	public PersonMapper perosnMapper;
    /**
     * 此方法直接返回null，shiro底层直接会返回
     * 在controller里面的登录方法，需要执行login方法来完成token的传入(即用户名密码或其他一些代表用户的东西)，这个方法的入参即是传入的token
     *  Subject subject = SecurityUtils.getSubject()
      * subject.login(new UsernamePasswordToken(username, password));
      * 
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("登录认证");
    	String username = (String) token.getPrincipal();//点进去看源码，就是获取到UsernamePasswordToken类的username，我们在做登录方法的时候，可以将通过UsernamePasswordToken将请求url的username放进去
        //模拟数据库查询出来用户名密码
    	Person person =perosnMapper.findPersonByName(username);
    	
    	//接下来就是比对一下subject里面的数据与数据库的用户信息是否一致了(用户名和密码)
    	//1、校验账户
    	if (person==null) {
        	//return null,在shiro底层会默认去抛出UnknownAccountException异常
            return null;
        }
 
        /**
         * 2、校验密码
         * 第二个参数(传数据库查询出来的密码)，必传项，其他参数可为空
         * 第一个参数代表凭证，可以传代表用户唯一性的值，这里传username更好一点，如果你之后没有授权也可以不传，直接为空
         * 之后就会自行校验密码正确性
         */
        return new SimpleAuthenticationInfo(username, person.getAge().toString(), getName());
    }
    /**
	 * 权限控制方法 
	 * 主体认证之后(即身份验证通常为登录验证通过)，系统进行访问控制
	 */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    	return null;
    	/*System.out.println("登录验证通过，权限验证开始");
    	SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();    	
    	Subject subject =SecurityUtils.getSubject();
    	//此时想到SimpleAuthenticationInfo的第一个参数就是Principal
    	String username= (String) subject.getPrincipal();//此处能获取信息，需要认证的时候SimpleAuthenticationInfo传入的第一个参数，当初传入的是username，我们可以根据这个去查询用户所有权限
    	//模拟查询得到权限
    	String permission1 = "/user/add";
    	//String permission2 = "/user/deleteajax";
    	simpleAuthorizationInfo.addStringPermission(permission1);//将数据库查询出来的真实权限与在shiroconfig里面配置的权限比较
    	//simpleAuthorizationInfo.addStringPermission(permission2);
    	return simpleAuthorizationInfo;*/
    }
}