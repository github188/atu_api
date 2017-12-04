package com.atu.api.dao.impl;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.atu.api.dao.ItemPropertyDao;
import com.atu.api.domain.ItemProperty;

public class ItemPropertyDaoImpl extends SqlMapClientTemplate implements ItemPropertyDao {

	@Override
	public Integer insert(ItemProperty itemProperty) {
		return (Integer)insert("ItemProperty.insert",itemProperty);
	}

	@Override
	public void modify(ItemProperty itemProperty) {
		update("ItemProperty.updateByItemId",itemProperty);
	}

	@Override
	public ItemProperty selectByItemId(int itemId) {
		return (ItemProperty)queryForObject("ItemProperty.selectByItemId",itemId);
	}

}
