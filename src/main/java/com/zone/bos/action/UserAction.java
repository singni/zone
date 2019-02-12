package com.zone.bos.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;
import com.zone.bos.domain.User;
import com.zone.bos.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller//("abc")
@Scope("prototype")
public class UserAction extends BaseAction<User>{
	//通过属性驱动接收验证码
	private String checkcode;
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	
	/**
	 * 使用shiro提供的方式进行认证
	 * @return
	 */
	public String login(){
		//生成的验证码
		String key = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		
		//判断用户输入的验证码是否正确
		if(StringUtils.isNotBlank(checkcode) && checkcode.equals(key)){
			//验证码正确
			//获得当前用户对象
			Subject subject = SecurityUtils.getSubject();//状态为“未认证”
			String password = tClass.getPassword();
			password = MD5Utils.md5(password);
			//构造一个用户名密码令牌
			AuthenticationToken token = new UsernamePasswordToken(tClass.getUsername(), password);
			try{
				subject.login(token);
			}catch (UnknownAccountException e) {
				e.printStackTrace();
				//设置错误信息
				this.addActionError(this.getText("usernamenotfound"));
				return "login";
			}catch (Exception e) {
				e.printStackTrace();
				//设置错误信息
				this.addActionError(this.getText("loginError"));
				return "login";
			}
			//获取认证信息对象中存储的User对象
			User user = (User) subject.getPrincipal();
			ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
			return "home";
		}else{
			//验证码错误,设置错误提示信息，跳转到登录页面
			this.addActionError(this.getText("validateCodeError"));
			return "login";
		}
	}
	
	public String login_back(){
		ValueStack valueStack = ActionContext.getContext().getValueStack();
		
		//生成的验证码
		String key = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		
		//判断用户输入的验证码是否正确
		if(StringUtils.isNotBlank(checkcode) && checkcode.equals(key)){
			//验证码正确
			User user = userService.login(tClass);
			if(user != null){
				//登录成功,将User放入session域，跳转到系统首页
				ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
				return "home";
			}else{
				//登录失败，设置错误提示信息，跳转到登录页面
				this.addActionError(this.getText("loginError"));
				return "login";
			}
		}else{
			//验证码错误,设置错误提示信息，跳转到登录页面
			this.addActionError(this.getText("validateCodeError"));
			return "login";
		}
	}
	
	/**
	 * 用户退出
	 */
	public String logout(){
		//销毁session
		ServletActionContext.getRequest().getSession().invalidate();
		return "login";
	}
	
	/**
	 * 修改当前登录用户密码
	 * @throws IOException 
	 */
	public String editPassword() throws IOException {
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
		String password = tClass.getPassword();//新密码
		password = MD5Utils.md5(password);
		String flag = "1";
		try{
			userService.editPassword(password,user.getId());
		}catch (Exception e) {
			//修改密码失败
			flag = "0";
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(flag);
		return NONE;
	}
	
	/**
	 * 用户分页查询
	 * @throws IOException 
	 */
	public String pageQuery() throws IOException{
		userService.pageQuery(pageBean);
		this.writePageBean2Json(pageBean );
		return NONE;
	}
	
	//接收角色数据
	private String[] roleIds;
	
	/**
	 * 添加用户
	 */
	public String add(){
		userService.save(tClass,roleIds);
		return "list";
	}

	public String[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}
}
