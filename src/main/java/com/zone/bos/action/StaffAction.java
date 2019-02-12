package com.zone.bos.action;


import com.alibaba.fastjson.JSONObject;
import com.zone.bos.domain.Staff;
import com.zone.bos.service.StaffService;
import com.zone.bos.utils.PageBean;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

/**
 * 取派员管理
 *
 *
 */
@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff>{

	private int page;//页码
	private int rows;//每页显示的记录数
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public void setPage(int page) {
		this.page = page;
	}
	
	/**
	 * 添加取派员
	 */
	public String add(){
		staffService.save(tClass);
		return "list";
	}

	/**
	 * 分页查询方法
	 * @throws IOException 
	 */
	public String pageQuery() throws IOException {
		PageBean pageBean = new PageBean();
		pageBean.setCurrentPage(page);
		pageBean.setPageSize(rows);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Staff.class);
		pageBean.setDetachedCriteria(detachedCriteria);
		staffService.pageQuery(pageBean);
		//将PageBean对象转为json返回
        String json = JSONObject.toJSONString(pageBean);
		ServletActionContext.getResponse().setContentType("text/json;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(json);
		return NONE;
	}
	
	//接收ids参数
	private String ids;
	public void setIds(String ids) {
		this.ids = ids;
	}
	
	/**
	 * 批量删除功能（逻辑删除）
	 * @return
	 */
	public String delete(){
		staffService.deleteBatch(ids);
		return "list";
	}
	
	/**
	 * 修改取派员信息
	 */
	public String edit(){
		Staff staff = staffService.findById(tClass.getId());
		
		staff.setName(tClass.getName());
		staff.setTelephone(tClass.getTelephone());
		staff.setStation(tClass.getStation());
		staff.setHaspda(tClass.getHaspda());
		staff.setStandard(tClass.getStandard());
		
		staffService.update(staff);
		return "list";
	}
    public String listAjax() throws IOException{
        List<Staff> list = staffService.findListNotDelete();
        this.writeList2Json(list);
        return NONE;
    }
}
