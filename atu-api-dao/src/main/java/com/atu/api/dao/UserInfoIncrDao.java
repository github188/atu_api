package com.atu.api.dao;

import java.util.List;

import com.atu.api.domain.UserInfoIncr;
import com.atu.api.domain.query.UserInfoIncrQuery;

public interface UserInfoIncrDao {
	
	/**
	 * 添加用户账号信息到用户信息扩展表
	 * @param userInfo
	 * @return
	 */
	public Integer insert(UserInfoIncr userInfoIncr);
	
	/**
	 * 依据用户ID查询用户账号信息
	 * @param userId
	 * @return
	 */
	public UserInfoIncr selectByPrimaryKey(int userId);
	
	/**
	 *  查询全部商户列表
	 * @param userInfoIncrQuery
	 * @return
	 */
	public List<UserInfoIncr> selectByCondition(UserInfoIncrQuery userInfoIncrQuery);

}
