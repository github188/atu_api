package com.atu.api.dao.impl;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.atu.api.dao.ItemDescriptionDao;
import com.atu.api.domain.ItemDescription;

public class ItemDescriptionDaoImpl extends SqlMapClientTemplate implements ItemDescriptionDao {

	@Override
	public Integer insert(ItemDescription itemDescription) {
		return (Integer)insert("ItemDescription.insert",itemDescription);
	}

	@Override
	public void modify(ItemDescription itemDescription) {
		update("ItemDescription.updateByItemId",itemDescription);
	}

	@Override
	public ItemDescription selectByItemId(int itemId) {
		return (ItemDescription)queryForObject("ItemDescription.selectByItemId",itemId);
	}

	@Override
	public void insertOrUpdate(ItemDescription itemDescription) {
		insert("ItemDescription.insertOrUpdate", itemDescription);
	}

}
