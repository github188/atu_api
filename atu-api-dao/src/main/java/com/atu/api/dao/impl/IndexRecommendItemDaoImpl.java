package com.atu.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.atu.api.dao.IndexRecommendItemDao;
import com.atu.api.domain.IndexRecommendItem;
import com.atu.api.domain.query.IndexRecommendItemQuery;

public class IndexRecommendItemDaoImpl extends SqlMapClientTemplate implements IndexRecommendItemDao {

	@Override
	public Integer insert(IndexRecommendItem indexRecommendItem) {
		return (Integer)insert("IndexRecommendItem.insert",indexRecommendItem);
	}

	@Override
	public void modify(IndexRecommendItem indexRecommendItem) {
		update("IndexRecommendItem.updateByPrimaryKey",indexRecommendItem);
	}

	@Override
	public int countByCondition(IndexRecommendItemQuery indexRecommendItemQuery) {
		return (Integer)queryForObject("IndexRecommendItem.countByCondition",indexRecommendItemQuery);
	}

	@Override
	public List<IndexRecommendItem> selectByCondition(IndexRecommendItemQuery indexRecommendItemQuery) {
		return (List<IndexRecommendItem>)queryForList("IndexRecommendItem.selectByCondition",indexRecommendItemQuery);
	}

	@Override
	public List<IndexRecommendItem> selectByConditionForPage(IndexRecommendItemQuery indexRecommendItemQuery) {
		return (List<IndexRecommendItem>)queryForList("IndexRecommendItem.selectByConditionForPage",indexRecommendItemQuery);
	}

}
