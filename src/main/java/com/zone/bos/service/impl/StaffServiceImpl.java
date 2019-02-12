package com.zone.bos.service.impl;


import com.zone.bos.dao.StaffDao;
import com.zone.bos.domain.Staff;
import com.zone.bos.domain.Subarea;
import com.zone.bos.service.StaffService;
import com.zone.bos.utils.PageBean;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StaffServiceImpl implements StaffService {
	//注入dao
	@Autowired
	private StaffDao staffDao;

	public void save(Staff model) {
		staffDao.save(model);
	}

	public void pageQuery(PageBean pageBean) {
		staffDao.pageQuery(pageBean);
	}

	/**
	 * 批量删除
	 */
	public void deleteBatch(String ids) {
		String[] staffIds = ids.split(",");
		for (String id : staffIds) {
			staffDao.executeUpdate("staff.delete", id);
		}
	}

	public Staff findById(String id) {
		return staffDao.findById(id);
	}
	
	public void update(Staff staff) {
		staffDao.update(staff);
	}

    public List<Staff> findListNotDelete() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Staff.class);
        detachedCriteria.add(Restrictions.ne("deltag", "1"));
        return staffDao.findByCriteria(detachedCriteria );
    }
}
