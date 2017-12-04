package com.atu.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.atu.api.dao.ItemDao;
import com.atu.api.domain.Item;
import com.atu.api.domain.query.ItemQuery;
import com.atu.api.domain.vo.ItemVO;

@SuppressWarnings("unchecked")
public class ItemDaoImpl extends SqlMapClientTemplate implements ItemDao {

	@Override
	public Integer insert(Item item) {
		return (Integer)insert("Item.insert",item);
	}

	@Override
	public void modify(Item item) {
		update("Item.updateByPrimaryKey",item);
	}

	@Override
	public Item selectByItemId(Integer itemId) {
		return (Item)queryForObject("Item.selectByPrimaryKey",itemId);
	}

	@Override
	public int countByCondition(ItemQuery itemQuery) {
		return (Integer)queryForObject("Item.countByCondition",itemQuery);
	}

	@Override
	public List<Item> selectByCondition(ItemQuery itemQuery) {
		return (List<Item>)queryForList("Item.selectByCondition",itemQuery);
	}

	@Override
	public List<Item> selectByConditionForPage(ItemQuery itemQuery) {
		return (List<Item>)queryForList("Item.selectByConditionForPage",itemQuery);
	}
	
	@Override
	public int countVOByCondition(ItemQuery itemQuery) {
		return (Integer)queryForObject("Item.countVOByCondition",itemQuery);
	}
	
	@Override
	public List<ItemVO> selectVOByConditionForPage(ItemQuery itemQuery) {
		return (List<ItemVO>)queryForList("Item.selectVOByConditionForPage",itemQuery);
	}
	
	@Override
	public List<Integer> selectOriginProvinceByCondition(ItemQuery itemQuery) {
		return (List<Integer>)queryForList("Item.selectOriginProvinceByCondition",itemQuery);
	}

}
