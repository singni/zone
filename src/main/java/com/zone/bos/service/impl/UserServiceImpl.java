package com.zone.bos.service.impl;


import com.zone.bos.dao.UserDao;
import com.zone.bos.domain.Role;
import com.zone.bos.domain.User;
import com.zone.bos.service.UserService;
import com.zone.bos.utils.MD5Utils;
import com.zone.bos.utils.PageBean;
import org.activiti.engine.IdentityService;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	//注入dao
	@Autowired
	private UserDao userDao;
    @Autowired
    private IdentityService identityService;
	public User login(User user) {
		String username = user.getUsername();
		String password = user.getPassword();//明文
		password = MD5Utils.md5(password);//md5加密
		return userDao.findByUsernameAndPassword(username,password);
	}

	public void editPassword(String password, String id) {
		userDao.executeUpdate("editPassword", password,id);
	}

	public void pageQuery(PageBean pageBean) {
		userDao.pageQuery(pageBean);
	}

	public void save(User user, String[] roleIds) {
		String password = user.getPassword();
		password = MD5Utils.md5(password);
		user.setPassword(password);
		userDao.save(user);//持久对象

        UserEntity userEntity = new UserEntity(user.getId());
        identityService.saveUser(userEntity);
        for (String roleId : roleIds) {
			Role role = new Role(roleId);
			//用户关联角色
			user.getRoles().add(role);
			identityService.createMembership(userEntity.getId(),role.getName());
		}
	}
}
