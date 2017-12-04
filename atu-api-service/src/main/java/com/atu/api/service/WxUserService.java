package com.atu.api.service;

import com.atu.api.domain.WxUser;
import com.atu.api.service.result.Result;

public interface WxUserService {
	/**
	 * 添加微信用户
	 * @param wxUser
	 * @return
	 */
	public Result login(WxUser wxUser);

	
	/**
	 * 依据ID查询微信用户
	 * @param id
	 * @return
	 */
	public Result selectById(Integer id);
}
