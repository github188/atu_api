package com.atu.api.dao;

import java.util.List;

import com.atu.api.domain.OrderConsignee;
import com.atu.api.domain.query.OrderConsigneeQuery;

public interface OrderConsigneeDao {
	
	/** 添加收货人地址 */
	public Integer insert(OrderConsignee orderConsignee);
	
	/** 根据条件查询订单信息 */
	public List<OrderConsignee> selectByCondition(OrderConsigneeQuery orderConsigneeQuery);
	
	/** 根据订单号查询订单信息 */
	public List<OrderConsignee> selectByOrderId(int orderId);
	
	/** 根据订单收货人ID修改订单号 */
	public void updateOderIdByOrderId(OrderConsignee orderConsignee);
	
	/** 根据订单号查询收货人信息 */
	public OrderConsignee selectBysOrderId(int orderId);
	
	/** 根据订单收货人信息修改快递信息 */
	public void updateExpress(OrderConsignee orderConsignee);

}
