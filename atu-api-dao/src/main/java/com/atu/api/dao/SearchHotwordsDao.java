package com.atu.api.dao;

import java.util.List;

import com.atu.api.domain.SearchHotwords;
import com.atu.api.domain.query.SearchHotwordsQuery;


public interface SearchHotwordsDao {
	/**
	 * 获取所有首页促销商品信息
	 */
	public List<SearchHotwords> selectByCondition(SearchHotwordsQuery searchHotwordsQuery);
}
