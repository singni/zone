package com.zone.bos.action;


import com.zone.bos.crm.domain.Customer;
import com.zone.bos.domain.Decidedzone;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

/**
 * 定区管理
 * 
 * @author zhaoqx
 * 
 */
@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone> {
	// 接收分区id
	private String[] subareaid;

	public void setSubareaid(String[] subareaid) {
		this.subareaid = subareaid;
	}

	/**
	 * 添加定区
	 * 
	 * @return
	 */
	public String add() {
		decidedzoneService.save(tClass, subareaid);
		return "list";
	}

	/**
	 * 分页查询方法
	 * 
	 * @throws IOException
	 */
	public String pageQuery() throws IOException {
		decidedzoneService.pageQuery(pageBean);
		// 将PageBean对象转为json返回
		this.writePageBean2Json(pageBean);
		return NONE;
	}
    /**
     * 查询已经关联到指定定区的客户
     * @return
     * @throws IOException
     */
    public String findhasassociationCustomers() throws IOException{
        List<Customer> list = customerService.findhasassociationCustomers(tClass.getId());
        this.writeList2Json(list);
        return NONE;
    }

    private Integer[] customerIds;


    /**
     * 查询没有关联到定区的客户
     * @return
     * @throws IOException
     */
    public String findnoassociationCustomers() throws IOException{
        List<Customer> list = customerService.findnoassociationCustomers();
        this.writeList2Json(list);
        return NONE;
    }

    /**
     * 定区关联客户
     * @return
     */
    public String assigncustomerstodecidedzone(){
        customerService.assignCustomersToDecidedZone(customerIds, tClass.getId());
        return "list";
    }

    public Integer[] getCustomerIds() {
        return customerIds;
    }

    public void setCustomerIds(Integer[] customerIds) {
        this.customerIds = customerIds;
    }

}
