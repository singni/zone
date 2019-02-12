package com.zone.bos.service;


import com.zone.bos.domain.Staff;
import com.zone.bos.utils.PageBean;

import java.util.List;

public interface StaffService {

	public void save(Staff model);

	public void pageQuery(PageBean pageBean);

	public void deleteBatch(String ids);

	public Staff findById(String id);

	public void update(Staff staff);

    List<Staff> findListNotDelete();
}
