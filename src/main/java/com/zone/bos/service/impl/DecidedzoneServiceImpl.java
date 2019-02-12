package com.zone.bos.service.impl;

import com.zone.bos.dao.DecidedzoneDao;
import com.zone.bos.dao.DecidedzoneService;
import com.zone.bos.dao.SubareaDao;
import com.zone.bos.domain.Decidedzone;
import com.zone.bos.domain.Subarea;
import com.zone.bos.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Rian on 2019/1/26.
 * Copyright © 2018 Rian. All rights reserved
 */
@Service
@Transactional
public class DecidedzoneServiceImpl implements DecidedzoneService {
    @Autowired
    private DecidedzoneDao decidedzoneDao;
    @Autowired
    private SubareaDao subareaDao;

    /**
     * 添加定区
     */
    public void save(Decidedzone model, String[] subareaid) {
        decidedzoneDao.save(model);
        for (String sid : subareaid) {
            Subarea subarea = subareaDao.findById(sid);//持久对象
            subarea.setDecidedzone(model);
        }
    }

    public void pageQuery(PageBean pageBean) {
        decidedzoneDao.pageQuery(pageBean);
    }

}
