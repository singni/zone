package com.zone.bos.service.impl;

import com.zone.bos.dao.RegionDao;
import com.zone.bos.domain.Region;
import com.zone.bos.service.RegionService;
import com.zone.bos.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Rian on 2019/1/25.
 * Copyright © 2018 Rian. All rights reserved
 */
@Service
@Transactional
public class RegionServiceImpl  implements RegionService{
    @Resource
    private RegionDao regionDao;

    public void saveBatch(List<Region> list) {
        for (Region region : list) {
            regionDao.saveOrUpdate(region);
        }
    }

    public void pageQuery(PageBean pageBean) {
        regionDao.pageQuery(pageBean);
    }

    public List<Region> findAll() {
        return regionDao.findAll();
    }

    public List<Region> findByQ(String q) {
        return regionDao.findByQ(q);
    }
}
