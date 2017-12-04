package com.atu.api.dao.impl;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.atu.api.dao.WxUserDao;
import com.atu.api.domain.WxUser;

public class WxUserDaoImpl extends SqlMapClientTemplate implements WxUserDao {
	@Override
	public Integer insert(WxUser wxUser) {
		return (Integer) insert("WxUser.insert", wxUser);
	}

	@Override
	public WxUser selectById(Integer id) {
		return (WxUser) queryForObject("WxUser.selectByPrimaryKey", id);
	}

}
