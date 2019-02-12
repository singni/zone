package com.zone.bos.action;


import com.zone.bos.crm.domain.Customer;
import com.zone.bos.domain.Noticebill;
import com.zone.bos.domain.User;
import com.zone.bos.utils.BOSContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
@Scope("prototype")
public class NoticebillAction extends BaseAction<Noticebill>{
	/**
	 * 调用代理对象，根据手机号查询客户信息
	 * @throws IOException 
	 */
	public String findCustomerByTelephone() throws IOException {
		Customer customer = customerService.findCustomerByPhonenumber(tClass.getTelephone());
		this.writeObject2Json(customer);
		return NONE;
	}
	
	/**
	 * 保存业务通知单，尝试自动分单
	 * @return
	 */
	public String add(){
		User user = BOSContext.getLoginUser();
		tClass.setUser(user);
		noticebillService.save(tClass);
		return "addUI";
	}
}
