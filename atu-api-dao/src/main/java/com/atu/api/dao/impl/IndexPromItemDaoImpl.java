package com.atu.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.atu.api.dao.IndexPromItemDao;
import com.atu.api.domain.IndexPromItem;
import com.atu.api.domain.query.IndexPromItemQuery;

@SuppressWarnings("unchecked")
public class IndexPromItemDaoImpl extends SqlMapClientTemplate implements IndexPromItemDao {

	@Override
	public List<IndexPromItem> selectByCondition(IndexPromItemQuery indexPromItemQuery) {
		return (List<IndexPromItem>)queryForList("IndexPromItem.selectByCondition",indexPromItemQuery);
	}

	@Override
	public List<IndexPromItem> selectByConditionForPage(
			IndexPromItemQuery indexPromItemQuery) {
		return (List<IndexPromItem>)queryForList("IndexPromItem.selectByConditionForPage", indexPromItemQuery);
	}

	@Override
	public int countByCondition(IndexPromItemQuery indexPromItemQuery) {
		return (Integer)queryForObject("IndexPromItem.countByCondition", indexPromItemQuery);
	}

}
