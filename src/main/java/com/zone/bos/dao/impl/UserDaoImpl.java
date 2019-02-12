package com.zone.bos.dao.impl;

import com.zone.bos.dao.UserDao;
import com.zone.bos.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//@Scope("prototype")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
	/**
	 * 根据用户名和密码查询用户
	 */
	public User findByUsernameAndPassword(String username, String password) {
		String hql = "FROM User u WHERE u.username = ? AND u.password = ?";
		List<User> list = (List<User>) this.getHibernateTemplate().find(hql, username,password);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	public User findUserByUsername(String username) {
		String hql = "FROM User u WHERE u.username = ? ";
		List<User> list = (List<User>) this.getHibernateTemplate().find(hql, username);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

}
