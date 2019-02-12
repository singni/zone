package com.zone.bos.action;


import com.zone.bos.domain.Role;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

/**
 * 角色管理
 *
 */
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role>{
	private String ids;//接收权限id
	/**
	 * 添加角色
	 */
	public String add(){
		roleService.save(tClass,ids);
		return "list";
	}
	
	/**
	 * 分页查询方法
	 * @throws IOException 
	 */
	public String pageQuery() throws IOException {
		roleService.pageQuery(pageBean);
		//将PageBean对象转为json返回
		this.writePageBean2Json(pageBean);

		return NONE;
	}
	
	/**
	 * 查询所有的角色数据，返回json
	 * @return
	 * @throws IOException
	 */
	public String listajax() throws IOException{
		List<Role> list = roleService.findAll();
		this.writeList2Json(list );
		return NONE;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
}
