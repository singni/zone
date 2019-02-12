package com.zone.bos.service.impl;


import com.zone.bos.dao.FunctionDao;
import com.zone.bos.domain.Function;
import com.zone.bos.domain.User;
import com.zone.bos.service.FunctionService;
import com.zone.bos.utils.BOSContext;
import com.zone.bos.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FunctionServiceImpl implements FunctionService {
	@Autowired
	private FunctionDao functionDao;

	public void pageQuery(PageBean pageBean) {
		functionDao.pageQuery(pageBean);
	}

	public List<Function> findAll() {
		return functionDao.findAll();
	}

	public void save(Function model) {
		Function function = model.getFunction();
		if (function != null && function.getId().equals("")) {
			model.setFunction(null);
		}
		functionDao.save(model);
	}

	/**
	 * 查询菜单
	 */
	public List<Function> findMenu() {
		User user = BOSContext.getLoginUser();
		List<Function> list = null;
		if (user.getUsername().equals("admin")) {
			//查询所有菜单
			list = functionDao.findAllMenu();
		}else{
			//普通用户，查询对应的菜单
			list = functionDao.findMenuByUserid(user.getId());
		}
		return list;
	}
}
