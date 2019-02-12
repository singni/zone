package com.zone.bos.dao;


import com.zone.bos.domain.Function;

import java.util.List;

public interface FunctionDao extends BaseDao<Function>{

	public List<Function> findListByUserid(String id);

	public List<Function> findAllMenu();

	public List<Function> findMenuByUserid(String id);

}
