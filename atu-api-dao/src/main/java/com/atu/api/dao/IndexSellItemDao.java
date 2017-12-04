package com.atu.api.dao;

import java.util.List;

import com.atu.api.domain.IndexSellItem;
import com.atu.api.domain.query.IndexSellItemQuery;

public interface IndexSellItemDao{
	
	/**
	 * 添加商品信息
	 * @param item
	 * @return
	 */
	public Integer insert(IndexSellItem indexSellItem);

	/**
	 * 依据商品ID修改商品信息
	 * @param item
	 */
	public void modify(IndexSellItem indexSellItem);

	
	/**
	 * 根据相应的条件查询满足条件的商品信息的总数
	 * @param itemQuery
	 * @return
	 */
	public int countByCondition(IndexSellItemQuery indexSellItemQuery);
	
	/**
	 * 根据相应的条件查询商品信息
	 * @param itemQuery
	 * @return
	 */
	public List<IndexSellItem> selectByCondition(IndexSellItemQuery indexSellItemQuery);
	
	/**
	 * 根据相应的条件查询商品信息---分页查询
	 * @param itemQuery
	 * @return
	 */
	public List<IndexSellItem> selectByConditionForPage(IndexSellItemQuery indexSellItemQuery);
}