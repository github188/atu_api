package com.atu.api.service;

import com.atu.api.domain.query.IndexPromItemQuery;
import com.atu.api.service.result.Result;

public interface IndexPromItemService {
	/**
	 * 获取所有首页促销商品信息
	 */
	public Result getIndexPromItemByPage(IndexPromItemQuery indexPromItemQuery);
}
