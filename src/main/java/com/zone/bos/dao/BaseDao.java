package com.zone.bos.dao;

import com.zone.bos.utils.PageBean;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.criterion.DetachedCriteria;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Rian on 2019/1/22.
 * Copyright © 2018 Rian. All rights reserved
 */
public interface BaseDao<T> {

    void save(T entity);

    void delete(T entity);

    void update(T entity);

    void saveOrUpdate(T entity);

    T findById(Serializable id);

    List<T> findAll();

    void executeUpdate(String queryName, Object... objects);

    void pageQuery(PageBean pageBean);

    List<T> findByCriteria(DetachedCriteria detachedCriteria);
}
