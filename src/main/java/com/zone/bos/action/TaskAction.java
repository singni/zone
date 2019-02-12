package com.zone.bos.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.zone.bos.domain.WorkOrderManage;
import com.zone.bos.service.WorkordermanageService;
import com.zone.bos.utils.BOSContext;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by Rian on 2019/2/11.
 * Copyright © 2018 Rian. All rights reserved
 */
@Controller
@Scope("prototype")
public class TaskAction extends ActionSupport {
    @Autowired
    private TaskService taskService;
    private String taskId;
    private Integer check;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private WorkordermanageService workordermanageService;

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setCheck(Integer check) {
        this.check = check;
    }

    public String findGroupTask() {
        TaskQuery taskQuery = taskService.createTaskQuery();
        String id = BOSContext.getLoginUser().getId();
        taskQuery.taskCandidateUser(id);
        List<Task> list = taskQuery.list();
        ActionContext.getContext().getValueStack().set("list", list);
        return "grouptasklist";
    }

    /**
     * 拾取组任务
     *
     * @return
     */
    public String takeTask() {
        String id = BOSContext.getLoginUser().getId();
        taskService.claim(taskId, id);
        return "togrouptasklist";
    }

    /**
     * 查询个人任务
     *
     * @return
     */
    public String findPersonalTask() {
        TaskQuery query = taskService.createTaskQuery();
        String id = BOSContext.getLoginUser().getId();
        query.taskCandidateUser(id);
        List<Task> list = query.list();
        ActionContext.getContext().getValueStack().set("list", list);
        return "persionaltasklist";
    }

    /**
     * 办理审核工作单任务
     */
    public String checkWorkOrdermanage() {
        // 根据任务id查询任务对象
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        // 根据任务对象查询流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 根据流程实例id查询流程实例对象
        ProcessInstance processInstance = runtimeService
                .createProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        String workOrdermanageId = processInstance.getBusinessKey();
        WorkOrderManage WorkOrdermanage = workordermanageService.findById(workOrdermanageId);
        if (check == null) {
            //跳转到审核页面
            // 跳转到一个审核工作单页面，展示当前对应的工作单信息
            ActionContext.getContext().getValueStack().set("map", workOrdermanageId);
            return "check";
        } else {
            workordermanageService.checkWorkOrdermanage(taskId, check, workOrdermanageId);
            return "topersonaltasklist";
        }
    }

    /**
     * 出库
     * @return
     */
    public String outStore(){
        taskService.complete(taskId);
        return "topersonaltasklist";
    }
    /**
     * 配送
     * @return
     */
    public String tranferGoods(){
        taskService.complete(taskId);
        return "topersonaltasklist";
    }
    /**
     * 签收
     * @return
     */
    public String receive(){
        taskService.complete(taskId);
        return "topersonaltasklist";
    }

}
