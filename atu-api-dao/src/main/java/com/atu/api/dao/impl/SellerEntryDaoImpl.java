package com.atu.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.atu.api.dao.SellerEntryDao;
import com.atu.api.domain.SellerEntry;
import com.atu.api.domain.query.SellerEntryQuery;

public class SellerEntryDaoImpl extends SqlMapClientTemplate implements SellerEntryDao {

	@Override
	public Integer insert(SellerEntry sellerEntry) {
		return (Integer)insert("SellerEntry.insert",sellerEntry);
	}

	@Override
	public List<SellerEntry> selectByOrderId(int orderId) {
		return (List<SellerEntry>)queryForList("SellerEntry.selectByOrderId",orderId);
	}

	@Override
	public int countByCondition(SellerEntryQuery sellerEntryQuery) {
		return (Integer)queryForObject("SellerEntry.countByCondition", sellerEntryQuery);
	}

	@Override
	public List<SellerEntry> selectByCondition(SellerEntryQuery sellerEntryQuery) {
		return (List<SellerEntry>)queryForList("SellerEntry.selectByCondition", sellerEntryQuery);
	}

	@Override
	public List<SellerEntry> selectByConditionForPage(
			SellerEntryQuery sellerEntryQuery) {
		return (List<SellerEntry>)queryForList("SellerEntry.selectByConditionForPage", sellerEntryQuery);
	}

}
