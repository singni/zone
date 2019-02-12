package com.zone.bos.service;

import com.zone.bos.domain.Region;
import com.zone.bos.utils.PageBean;

import java.util.List;

/**
 * Created by Rian on 2019/1/25.
 * Copyright © 2018 Rian. All rights reserved
 */
public interface RegionService {


    public void saveBatch(List<Region> list);

    public void pageQuery(PageBean pageBean);

    public List<Region> findAll();

    public List<Region> findByQ(String q);
}
