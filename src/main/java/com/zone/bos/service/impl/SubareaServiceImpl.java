package com.zone.bos.service.impl;


import com.zone.bos.dao.SubareaDao;
import com.zone.bos.domain.Subarea;
import com.zone.bos.service.SubareaService;
import com.zone.bos.utils.PageBean;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class SubareaServiceImpl implements SubareaService {
	@Resource
	private SubareaDao subareaDao;

	public void save(Subarea model) {
		subareaDao.save(model);
	}

	public void pageQuery(PageBean pageBean) {
		subareaDao.pageQuery(pageBean);
	}

	public List<Subarea> findAll() {
		return subareaDao.findAll();
	}

    public List<Subarea> findListNotAssociation() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
        detachedCriteria.add(Restrictions.isNull("decidedzone"));
        return subareaDao.findByCriteria(detachedCriteria );
    }
}
