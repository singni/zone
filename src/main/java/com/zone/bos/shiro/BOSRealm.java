package com.zone.bos.shiro;


import com.zone.bos.dao.FunctionDao;
import com.zone.bos.dao.UserDao;
import com.zone.bos.domain.Function;
import com.zone.bos.domain.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.List;

/**
 * 自定义realm
 * 
 *
 */
public class BOSRealm extends AuthorizingRealm {
	@Resource
	private UserDao userDao;
	@Resource
	private FunctionDao functionDao;

	/**
	 * 认证方法
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();// 从令牌中获得用户名

		User user = userDao.findUserByUsername(username);
		if (user == null) {
			// 用户名不存在
			return null;
		} else {
			// 用户名存在
			String password = user.getPassword();// 获得数据库中存储的密码
			// 创建简单认证信息对象
			/***
			 * 参数一：签名，程序可以在任意位置获取当前放入的对象
			 * 参数二：从数据库中查询出的密码
			 * 参数三：当前realm的名称
			 */
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,
					password, this.getClass().getSimpleName());
			return info;//返回给安全管理器，由安全管理器负责比对数据库中查询出的密码和页面提交的密码
		}
	}

	/**
	 * 授权方法
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//根据当前登录用户查询其对应的权限，授权
		User user = (User) principals.getPrimaryPrincipal();
		List<Function> list = null;
		if(user.getUsername().equals("admin")){
			//当前用户是超级管理员，查询所有权限，为用户授权
			list = functionDao.findAll();
		}else{
			//普通用户，根据用户id查询对应的权限
			list = functionDao.findListByUserid(user.getId());
		}
		for (Function function : list) {
			info.addStringPermission(function.getCode());
		}
		return info;
	}

}
