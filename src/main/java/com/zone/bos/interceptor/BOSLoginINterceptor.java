package com.zone.bos.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.zone.bos.domain.User;
import org.apache.struts2.ServletActionContext;

/**
 * Created by Rian on 2019/1/23.
 * Copyright © 2018 Rian. All rights reserved
 */
public class BOSLoginINterceptor extends MethodFilterInterceptor {
    protected String doIntercept(ActionInvocation actionInvocation) throws Exception {
        User loginuser = (User) ServletActionContext.getRequest().getSession().getAttribute("loginuser");
        if(loginuser==null){
            return "login";
        }else {
            actionInvocation.invoke();
        }
        return null;
    }
}
