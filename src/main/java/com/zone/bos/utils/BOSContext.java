package com.zone.bos.utils;


import com.zone.bos.domain.User;
import org.apache.struts2.ServletActionContext;

public class BOSContext {
	public static User getLoginUser(){
		return (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
	}
}
