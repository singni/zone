package com.zone.bos.dao.impl;

import com.zone.bos.dao.BaseDao;
import com.zone.bos.utils.PageBean;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Rian on 2019/1/22.
 * Copyright © 2018 Rian. All rights reserved
 */
public class  BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T>{
    // 实体类型
    private Class<T> entityClass;

    // 使用注解方式进行依赖注入
    @Resource
    // @Autowired
    // @Qualifier(value="abc")
    public void setMySessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    /**
     * 在构造方法中动态获得操作的实体类型
     */
    public BaseDaoImpl() {
        // 获得父类（BaseDaoImpl<T>）类型
        ParameterizedType genericSuperclass = (ParameterizedType) this
                .getClass().getGenericSuperclass();
        // 获得父类上的泛型数组
        Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
        entityClass = (Class<T>) actualTypeArguments[0];
    }

    public void save(T entity) {
        this.getHibernateTemplate().save(entity);
    }

    public void delete(T entity) {
        this.getHibernateTemplate().delete(entity);
    }

    public void update(T entity) {
        this.getHibernateTemplate().update(entity);
    }

    public T findById(Serializable id) {
        return this.getHibernateTemplate().get(entityClass, id);
    }

    public List<T> findAll() {// FROM User
        String hql = "FROM  " + entityClass.getSimpleName();
        return (List<T>) this.getHibernateTemplate().find(hql);
    }

    /**
     * 通用更新方法
     */
    public void executeUpdate(String queryName, Object... objects) {
        Session session = this.getSessionFactory().getCurrentSession();// 从本地线程中获得session对象

        // 使用命名查询语句获得一个查询对象
        Query query = session.getNamedQuery(queryName);
        // 为HQL语句中的？赋值
        int i = 0;
        for (Object arg : objects) {
            query.setParameter(i++, arg);
        }
        query.executeUpdate();// 执行更新
    }

    /**
     * 通用分页查询方法
     */
    public void pageQuery(PageBean pageBean) {
        int currentPage = pageBean.getCurrentPage();
        int pageSize = pageBean.getPageSize();
        DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
        //总数据量----select count(*) from bc_staff
        //改变Hibernate框架发出的sql形式
        detachedCriteria.setProjection(Projections.rowCount());//select count(*) from bc_staff
        List<Long> list = (List<Long>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
        Long total = list.get(0);
        pageBean.setTotal(total.intValue());//设置总数据量
        detachedCriteria.setProjection(null);//修改sql的形式为select * from ....
        //重置表和类的映射关系
        detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
        //当前页展示的数据集合
        int firstResult = (currentPage - 1) * pageSize;
        int maxResults = pageSize;
        List rows = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
        pageBean.setRows(rows);
    }

    public List<T> findByCriteria(DetachedCriteria detachedCriteria) {
        return (List<T>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
    }

    public void saveOrUpdate(T entity) {
        this.getHibernateTemplate().saveOrUpdate(entity);
    }
}


