package com.atu.api.dao;

import java.util.List;

import com.atu.api.domain.IndexRecommendItem;
import com.atu.api.domain.query.IndexRecommendItemQuery;

public interface IndexRecommendItemDao{
	
	/**
	 * 添加商品信息
	 * @param item
	 * @return
	 */
	public Integer insert(IndexRecommendItem indexRecommendItem);

	/**
	 * 依据商品ID修改商品信息
	 * @param item
	 */
	public void modify(IndexRecommendItem indexRecommendItem);

	
	/**
	 * 根据相应的条件查询满足条件的商品信息的总数
	 * @param itemQuery
	 * @return
	 */
	public int countByCondition(IndexRecommendItemQuery indexRecommendItemQuery);
	
	/**
	 * 根据相应的条件查询商品信息
	 * @param itemQuery
	 * @return
	 */
	public List<IndexRecommendItem> selectByCondition(IndexRecommendItemQuery indexRecommendItemQuery);
	
	/**
	 * 根据相应的条件查询商品信息---分页查询
	 * @param itemQuery
	 * @return
	 */
	public List<IndexRecommendItem> selectByConditionForPage(IndexRecommendItemQuery indexRecommendItemQuery);
}