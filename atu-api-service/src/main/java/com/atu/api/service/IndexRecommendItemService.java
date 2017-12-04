package com.atu.api.service;

import com.atu.api.domain.query.IndexRecommendItemQuery;
import com.atu.api.service.result.Result;

public interface IndexRecommendItemService {
	/**
	 * 获取所有首页促销商品信息
	 */
	public Result getIndexRecommendItemByPage(IndexRecommendItemQuery indexRecommendItemQuery);
}
