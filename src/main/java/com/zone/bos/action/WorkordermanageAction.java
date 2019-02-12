package com.zone.bos.action;


import com.opensymphony.xwork2.ActionContext;
import com.zone.bos.domain.WorkOrderManage;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

/**
 * 工作单管理
 * @author zhaoqx
 *
 */
@Controller
@Scope("prototype")
public class WorkordermanageAction extends BaseAction<WorkOrderManage>{
	public String add() throws IOException {
		String flag = "1";
		try{
			workordermanageService.save(tClass);
		}catch (Exception e) {
			flag = "0";
		}
		ServletActionContext.getResponse().setContentType(
				"text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(flag);
		return NONE;
	}

    /**
     * 查询start为0的工作单
     * @return
     */
	public String list(){
        List<WorkOrderManage> list=workordermanageService.findListNotStart();
        ActionContext.getContext().getValueStack().set("list",list);
        return "list";
    }

    /**
     * 启动流程
     * @return
     */
    public String start(){
	    String id=tClass.getId();
	    workordermanageService.start(id);
	    return  "toList";
    }
}
