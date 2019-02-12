package com.zone.bos.service;


import com.zone.bos.domain.Function;
import com.zone.bos.utils.PageBean;

import java.util.List;

public interface FunctionService {

	public void pageQuery(PageBean pageBean);

	public List<Function> findAll();

	public void save(Function model);

	public List<Function> findMenu();

}
