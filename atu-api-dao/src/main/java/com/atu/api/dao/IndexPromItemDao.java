package com.atu.api.dao;

import java.util.List;

import com.atu.api.domain.IndexPromItem;
import com.atu.api.domain.query.IndexPromItemQuery;

public interface IndexPromItemDao {

	/**
	 * 根据相应的条件查询信息
	 * @param IndexPromItemQuery
	 * @return
	 */
	public List<IndexPromItem> selectByCondition(IndexPromItemQuery indexPromItemQuery);
	
	/**
	 * 根据相应的条件查询信息-分页
	 * @param IndexPromItemQuery
	 * @return
	 */
	public List<IndexPromItem> selectByConditionForPage(IndexPromItemQuery indexPromItemQuery);
	
	/**
	 * 根据相应的条件查询满足条件的信息的总数
	 * @param IndexPromItemQuery
	 * @return
	 */
	public int countByCondition(IndexPromItemQuery indexPromItemQuery);
}
