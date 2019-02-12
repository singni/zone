package com.zone.bos.dao.impl;

import com.zone.bos.dao.RegionDao;
import com.zone.bos.domain.Region;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Rian on 2019/1/25.
 * Copyright © 2018 Rian. All rights reserved
 */
@Repository
public class RegionDaoImpl extends BaseDaoImpl<Region>  implements RegionDao {

    public List<Region> findByQ(String q) {
        String hql = "FROM Region WHERE province LIKE ? OR city LIKE ? OR district LIKE ?";
        return (List<Region>) this.getHibernateTemplate().find(hql, "%"+q+"%","%"+q+"%","%"+q+"%");
    }
}
