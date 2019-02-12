package com.zone.bos.action;

import com.opensymphony.xwork2.ActionContext;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by Rian on 2019/2/9.
 * Copyright © 2018 Rian. All rights reserved
 */
@Controller
@Scope("prototype")
public class ProcessInstanceAction {
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private RepositoryService repositoryService;
    private String deploymentId;
    private String imageName;
    private String id;

    public String getImageName() {
        return imageName;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String list(){
        ProcessInstanceQuery query=runtimeService.createProcessInstanceQuery();
        query.orderByProcessDefinitionId().desc();
        List<ProcessInstance> list = query.list();
        ActionContext.getContext().getValueStack().set("list",list);
        return "list";
    }

    public String findData() throws IOException {
        Map<String,Object> variables=runtimeService.getVariables(id);
        ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
        ServletActionContext.getResponse().getWriter().print(variables.toString());
        return "none";
    }

    /**
     * 根据流程实例id查询坐标、部署id、图片名称
     */
    public String showPng(){
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(id).singleResult();
        String processDefinitionId = processInstance.getProcessDefinitionId();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        deploymentId = processDefinition.getDeploymentId();
        imageName = processDefinition.getDiagramResourceName();

        //查询坐标
        String activityId = processInstance.getActivityId();//usertask1
        ProcessDefinitionEntity pd = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);//查询act_ge_bytearray
        ActivityImpl findActivity = pd.findActivity(activityId);
        int x = findActivity.getX();
        int y = findActivity.getY();
        int width = findActivity.getWidth();
        int height = findActivity.getHeight();

        ActionContext.getContext().getValueStack().set("x", x);
        ActionContext.getContext().getValueStack().set("y", y);
        ActionContext.getContext().getValueStack().set("width", width);
        ActionContext.getContext().getValueStack().set("height", height);

        return "showPng";
    }

     public String viewImage(){
         InputStream pngStream=repositoryService.getResourceAsStream(deploymentId,imageName);
         ActionContext.getContext().getValueStack().set("pngStream",pngStream);
         return "viewImage";
     }
}
