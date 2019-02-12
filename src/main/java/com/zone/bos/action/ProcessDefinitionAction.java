package com.zone.bos.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 流程定义管理
 * 
 * @author zhaoqx
 * 
 */
@Controller
@Scope("prototype")
public class ProcessDefinitionAction extends ActionSupport {
	// 注入Service
	@Autowired
	private RepositoryService repositoryService;

	/**
	 * 查询最新版本流程定义列表数据
	 */
	public String list() {
		ProcessDefinitionQuery query = repositoryService
				.createProcessDefinitionQuery();
		query.latestVersion();
		query.orderByProcessDefinitionName().desc();
		List<ProcessDefinition> list = query.list();
		// 压栈
		ActionContext.getContext().getValueStack().set("list", list);
		return "list";
	}

	// 接收上传的文件
	private File zipFile;

    public void setZipFile(File zipFile) {
        this.zipFile = zipFile;
    }

    /**
	 * 部署流程定义
	 * @throws FileNotFoundException 
	 */
	public String deploy() throws FileNotFoundException {
		DeploymentBuilder deploymentBuilder = repositoryService
				.createDeployment();
		deploymentBuilder.addZipInputStream(new ZipInputStream(
				new FileInputStream(zipFile)));
		deploymentBuilder.deploy();
		return "toList";
	}
	
	//接收流程定义id
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 展示png图片
	 */
	public String showpng(){
		//获取png图片对应的输入流
		InputStream pngStream = repositoryService.getProcessDiagram(id);
		ActionContext.getContext().getValueStack().set("pngStream", pngStream);
		return "showpng";
	}
	
	/**
	 * 删除流程定义
	 */
	public String delete(){
		String deltag = "0";
		//根据流程定义id查询部署id
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		query.processDefinitionId(id);//根据id过滤
		ProcessDefinition processDefinition = query.singleResult();
		String deploymentId = processDefinition.getDeploymentId();
		try{
			repositoryService.deleteDeployment(deploymentId);
		}catch (Exception e) {
			//当前要删除的流程定义正在使用
			deltag = "1";
			ActionContext.getContext().getValueStack().set("deltag", deltag);
			ProcessDefinitionQuery query2 = repositoryService
					.createProcessDefinitionQuery();
			query2.latestVersion();// 查询最新版本
			query2.orderByProcessDefinitionName().desc();// 排序
			List<ProcessDefinition> list = query2.list();// 执行查询
			// 压栈
			ActionContext.getContext().getValueStack().set("list", list);
			return "list";
		}
		return "toList";
	}

}
