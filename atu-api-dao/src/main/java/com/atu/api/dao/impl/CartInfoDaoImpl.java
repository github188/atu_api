package com.atu.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.atu.api.dao.CartInfoDao;
import com.atu.api.domain.CartInfo;
import com.atu.api.domain.query.CartInfoQuery;

@SuppressWarnings("unchecked")
public class CartInfoDaoImpl extends SqlMapClientTemplate implements CartInfoDao {

	
	@Override
	public List<CartInfo> selectByCondition(CartInfoQuery cartInfoQuery) {
		return (List<CartInfo>)queryForList("CartInfo.selectByCondition",cartInfoQuery);
	}
	
	@Override
	public List<Integer> selectBusinessIdByCondition(CartInfoQuery cartInfoQuery) {
		return (List<Integer>)queryForList("CartInfo.selectBusinessIdByCondition",cartInfoQuery);
	}
	
	@Override
	public CartInfo selectById(Integer cartId){
		return (CartInfo)queryForObject("CartInfo.selectById",cartId);
	}

	@Override
	public Integer insert(CartInfo cartInfo) {
		return (Integer)insert("CartInfo.insert", cartInfo);
	}

	@Override
	public Integer update(CartInfo cartInfo) {
		return update("CartInfo.update", cartInfo);
	}
	
	@Override
	public Integer updateSkuNum(CartInfo cartInfo) {
		return update("CartInfo.updateSkuNum", cartInfo);
	}

	@Override
	public Integer delete(Integer cartId) {
		return delete("CartInfo.delete", cartId);
	}

}
