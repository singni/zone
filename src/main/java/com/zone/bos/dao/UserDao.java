package com.zone.bos.dao;


import com.zone.bos.domain.User;

public interface UserDao extends BaseDao<User>{
	public User findByUsernameAndPassword(String username, String password);

	public User findUserByUsername(String username);
}
