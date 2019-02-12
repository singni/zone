package com.zone.bos.service;


import com.zone.bos.domain.Role;
import com.zone.bos.utils.PageBean;

import java.util.List;

public interface RoleService {

	public void save(Role model, String ids);

	public void pageQuery(PageBean pageBean);

	public List<Role> findAll();

}
