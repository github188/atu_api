package com.atu.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.atu.api.dao.UserInfoIncrDao;
import com.atu.api.domain.UserInfoIncr;
import com.atu.api.domain.query.UserInfoIncrQuery;

public class UserInfoIncrDaoImpl extends SqlMapClientTemplate implements UserInfoIncrDao {
	
	@Override
	public Integer insert(UserInfoIncr userInfoIncr) {
		return (Integer)insert("UserInfoIncr.insert", userInfoIncr);
	}
	
	@Override
	public UserInfoIncr selectByPrimaryKey(int userId) {
		return (UserInfoIncr)queryForObject("UserInfoIncr.selectByPrimaryKey",userId);
	}
	
	/** 查询全部商户列表  */
	@Override
	public List<UserInfoIncr> selectByCondition(UserInfoIncrQuery userInfoIncrQuery) {
		return queryForList("UserInfoIncr.selectByCondition",userInfoIncrQuery);
	}

}
