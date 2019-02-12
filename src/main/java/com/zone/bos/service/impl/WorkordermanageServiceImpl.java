package com.zone.bos.service.impl;


import com.zone.bos.dao.WorkordermanageDao;
import com.zone.bos.domain.WorkOrderManage;
import com.zone.bos.service.WorkordermanageService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class WorkordermanageServiceImpl implements WorkordermanageService {
    @Autowired
    private WorkordermanageDao workordermanageDao;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RuntimeService runtimeService;
    @Resource
    private HistoryService historyService;


    public void save(WorkOrderManage model) {
        model.setUpdatetime(new Date());
        workordermanageDao.save(model);
    }

    public List<WorkOrderManage> findListNotStart() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(WorkOrderManage.class);
        detachedCriteria.add(Restrictions.eq("start", "0"));
        return workordermanageDao.findByCriteria(detachedCriteria);
    }

    public void start(String id) {
        WorkOrderManage workOrderManage = workordermanageDao.findById(id);
        workOrderManage.setStart("1");
        String precessDefintitonKey = "transfer";
        String businessKey = id;
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("业务数据", workOrderManage);
        runtimeService.startProcessInstanceById(precessDefintitonKey, businessKey, variables);
    }

    public WorkOrderManage findById(String workOrdermanageId) {
        return workordermanageDao.findById(workOrdermanageId);
    }

    public void checkWorkOrdermanage(String taskId, Integer check, String workOrdermanageId) {
        WorkOrderManage wm = workordermanageDao.findById(workOrdermanageId);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        Map<String, Object> variable = new HashMap<String, Object>();
        variable.put("check", check);
        String processDefinitionId = task.getProcessDefinitionId();
        taskService.complete(taskId, variable);
        if (check == 0) {
            wm.setStart("0");
            historyService.deleteHistoricProcessInstance(processDefinitionId);
        }
    }
}
