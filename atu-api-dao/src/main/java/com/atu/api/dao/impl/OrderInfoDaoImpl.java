package com.atu.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.atu.api.dao.OrderInfoDao;
import com.atu.api.domain.OrderInfo;
import com.atu.api.domain.query.OrderInfoQuery;

public class OrderInfoDaoImpl extends SqlMapClientTemplate implements OrderInfoDao {

	@Override
	public Integer insert(OrderInfo orderInfo) {
		return (Integer)insert("OrderInfo.insert", orderInfo);
	}

	@Override
	public void modify(OrderInfo orderInfo) {
		update("OrderInfo.updateByPrimaryKey", orderInfo);
	}

	@Override
	public OrderInfo selectByOrderId(int orderId) {
		return (OrderInfo)queryForObject("OrderInfo.selectByPrimaryKey",orderId);
	}

	@Override
	public int countByCondition(OrderInfoQuery orderInfoQuery) {
		return (Integer)queryForObject("OrderInfo.countByCondition",orderInfoQuery);
	}

	@Override
	public List<OrderInfo> selectByCondition(OrderInfoQuery orderInfoQuery) {
		return (List<OrderInfo>)queryForList("OrderInfo.selectByCondition",orderInfoQuery);
	}

	@Override
	public List<OrderInfo> selectByConditionForPage(
			OrderInfoQuery orderInfoQuery) {
		return (List<OrderInfo>)queryForList("OrderInfo.selectByConditionForPage",orderInfoQuery);
	}
	
	@Override
	public int modifyPayMoney(OrderInfo orderInfo) {
		return update("OrderInfo.modifyPayMoney", orderInfo);
	}
	
	@Override
	public List<OrderInfo> selectByVenderUserIdForPage(OrderInfoQuery orderInfoQuery) {
		return (List<OrderInfo>)queryForList("OrderInfo.selectByVenderUserIdForPage",orderInfoQuery);
	}
	
	@Override
	public int countByConditionForPage(OrderInfoQuery orderInfoQuery) {
		return (Integer)queryForObject("OrderInfo.countByConditionForPage",orderInfoQuery);
	}
	
	@Override
	public Integer delPriceByOrderId(int orderId){
		return delete("OrderInfo.delPriceByOrderId", orderId);
	}
}
