package com.zone.bos.dao;


import com.zone.bos.domain.Decidedzone;
import com.zone.bos.utils.PageBean;

public interface DecidedzoneService {

	public void save(Decidedzone model, String[] subareaid);

	public void pageQuery(PageBean pageBean);

}
