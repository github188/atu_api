package com.atu.api.dao;

import com.atu.api.domain.WxUser;

public interface WxUserDao{
	
	/**
	 * 添加微信用户
	 * @param wxUser
	 * @return
	 */
	public Integer insert(WxUser wxUser);

	
	/**
	 * 依据ID查询微信用户
	 * @param id
	 * @return
	 */
	public WxUser selectById(Integer id);
	
}