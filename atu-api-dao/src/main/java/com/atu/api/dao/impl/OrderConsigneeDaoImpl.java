package com.atu.api.dao.impl;


import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.atu.api.dao.OrderConsigneeDao;
import com.atu.api.domain.OrderConsignee;
import com.atu.api.domain.OrderDetail;
import com.atu.api.domain.OrderInfo;
import com.atu.api.domain.query.OrderConsigneeQuery;

public class OrderConsigneeDaoImpl extends SqlMapClientTemplate implements OrderConsigneeDao{
	
	
	/** 添加收货人地址 */
	@Override
	public Integer insert(OrderConsignee orderConsignee) {
		return (Integer)insert("OrderConsignee.insert", orderConsignee);
	}
	
	/** 根据条件查询订单信息 */
	@Override
	public List<OrderConsignee> selectByCondition(OrderConsigneeQuery orderConsigneeQuery) {
		return (List<OrderConsignee>)queryForList("OrderConsignee.selectByCondition",orderConsigneeQuery);
	}
	
	/** 根据订单ID查询订单信息 */
	@Override
	public List<OrderConsignee> selectByOrderId(int orderId) {
		return (List<OrderConsignee>)queryForList("OrderConsignee.selectByOrderId",orderId);
	}
	
	/** 修改订单号根据订单号 */
	@Override
	public void updateOderIdByOrderId(OrderConsignee orderConsignee){
		update("OrderConsignee.updateOderIdByOrderId", orderConsignee);
	}
	
	/** 根据订单号查询订单收货人信息 */
	@Override
	public OrderConsignee selectBysOrderId(int orderId){
		return (OrderConsignee)queryForObject("OrderConsignee.selectBysOrderId",orderId);
	}
	
	@Override
	public void updateExpress(OrderConsignee orderConsignee) {
		update("OrderConsignee.updateExpress", orderConsignee);
	}

}
