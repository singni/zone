package com.zone.bos.dao;

import com.zone.bos.domain.Region;

import java.util.List;

/**
 * Created by Rian on 2019/1/25.
 * Copyright © 2018 Rian. All rights reserved
 */
public interface RegionDao extends BaseDao<Region> {

    public List<Region> findByQ(String q);
}
