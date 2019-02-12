package com.zone.bos.service;


import com.zone.bos.domain.WorkOrderManage;

import java.util.List;

public interface WorkordermanageService {

	public void save(WorkOrderManage model);

    List<WorkOrderManage> findListNotStart();

    void start(String id);

    WorkOrderManage findById(String workOrdermanageId);

    void checkWorkOrdermanage(String taskId, Integer check, String workOrdermanageId);
}
