package com.zone.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.zone.bos.dao.RoleDao;
import com.zone.bos.domain.Function;
import com.zone.bos.domain.Role;
import com.zone.bos.service.RoleService;
import com.zone.bos.utils.PageBean;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	@Resource
	private RoleDao roleDao;

    @Resource
    private IdentityService identityService;

	public void save(Role role, String ids) {//1,2,3,4
		roleDao.save(role);//持久对象

        Group group=new GroupEntity(role.getName());
        identityService.saveGroup(group);

		String[] functionIds = ids.split(",");
		for (String fid : functionIds) {
			Function function = new Function(fid);//托管,离线对象
			//角色关联权限
			role.getFunctions().add(function);
		}
	}
	public void pageQuery(PageBean pageBean) {
		roleDao.pageQuery(pageBean);
	}
	public List<Role> findAll() {
		return roleDao.findAll();
	}


}
