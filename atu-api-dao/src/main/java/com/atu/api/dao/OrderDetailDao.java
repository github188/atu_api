package com.atu.api.dao;

import java.util.List;

import com.atu.api.domain.OrderDetail;


public interface OrderDetailDao{
	
	/**
	 * 添加订单详情
	 * @param orderDetail
	 * @return
	 */
	public Integer insert(OrderDetail orderDetail);


	/**
	 * 依据订单ID查询订单详情
	 * @param orderId
	 * @return
	 */
	public List<OrderDetail> selectByOrderId(int orderId);
	
	/**
	 * 依据子订单ID查询订单详情
	 * @param orderId
	 * @return
	 */
	public List<OrderDetail> selectBySubOrderId(int subOrderId);
	
	/**
	 * 根据订单号修改订单详情订单号
	 * @param orderId
	 * @return
	 */
	public void updateOderIdByOrderId(OrderDetail orderDetail);
}