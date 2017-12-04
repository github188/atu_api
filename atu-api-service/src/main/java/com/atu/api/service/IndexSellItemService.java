package com.atu.api.service;

import com.atu.api.domain.query.IndexSellItemQuery;
import com.atu.api.service.result.Result;

public interface IndexSellItemService {
	/**
	 * 获取所有首页促销商品信息
	 */
	public Result getIndexSellItemByPage(IndexSellItemQuery indexSellItemQuery);
}
