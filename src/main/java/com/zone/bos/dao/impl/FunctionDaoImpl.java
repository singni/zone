package com.zone.bos.dao.impl;


import com.zone.bos.dao.FunctionDao;
import com.zone.bos.domain.Function;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements
        FunctionDao {
	/**
	 * 根据用户id查询对应的权限
	 */
	public List<Function> findListByUserid(String userid) {
		String hql = "SELECT DISTINCT f FROM Function f LEFT OUTER JOIN f.roles r" +
				" LEFT OUTER JOIN r.users u WHERE u.id = ?";
		return (List<Function>) this.getHibernateTemplate().find(hql, userid);
	}

	/**
	 * 查询所有的菜单
	 */
	public List<Function> findAllMenu() {
		String hql = "FROM Function f WHERE f.generatemenu = '1' ORDER BY f.zindex DESC";
		return (List<Function>) this.getHibernateTemplate().find(hql);
	}

	/**
	 * 根据用户id查询对应的菜单
	 */
	public List<Function> findMenuByUserid(String id) {
		String hql = "SELECT DISTINCT f FROM Function f LEFT OUTER JOIN f.roles r" +
				" LEFT OUTER JOIN r.users u WHERE u.id = ? AND f.generatemenu = '1' ORDER BY f.zindex DESC ";
		return (List<Function>) this.getHibernateTemplate().find(hql, id);
	}}
