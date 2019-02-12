package com.zone.bos.service;


import com.zone.bos.domain.Subarea;
import com.zone.bos.utils.PageBean;

import java.util.List;

public interface SubareaService {

	public void save(Subarea model);

	public void pageQuery(PageBean pageBean);

	public List<Subarea> findAll();

    List<Subarea> findListNotAssociation();
}
