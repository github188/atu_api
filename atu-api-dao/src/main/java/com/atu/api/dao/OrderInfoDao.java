package com.atu.api.dao;

import java.util.List;

import com.atu.api.domain.OrderInfo;
import com.atu.api.domain.query.OrderInfoQuery;

public interface OrderInfoDao{
	/**
	 * 添加订单信息
	 * @param orderInfo
	 * @return
	 */
	public Integer insert(OrderInfo orderInfo);

	/**
	 * 依据订单ID修改订单信息
	 * @param orderInfo
	 */
	public void modify(OrderInfo orderInfo);

	/**
	 * 依据订单ID查询订单信息
	 * @param userId
	 * @return
	 */
	public OrderInfo selectByOrderId(int orderId);
	
	/**
	 * 根据相应的条件查询满足条件的订单信息的总数
	 * @param orderInfoQuery
	 * @return
	 */
	public int countByCondition(OrderInfoQuery orderInfoQuery);
	
	/**
	 * 根据相应的条件查询订单信息
	 * @param orderInfoQuery
	 * @return
	 */
	public List<OrderInfo> selectByCondition(OrderInfoQuery orderInfoQuery);
	
	/**
	 * 根据相应的条件查询订单信息---分页查询
	 * @param orderInfoQuery
	 * @return
	 */
	public List<OrderInfo> selectByConditionForPage(OrderInfoQuery orderInfoQuery);
	
	/**
	 * 修改订单实际支付金额
	 * @param orderInfo
	 * @return
	 */
	public int modifyPayMoney(OrderInfo orderInfo);
	
	/**
	 * 商家根据VenderUserId查询订单信息---分页查询
	 * @param orderInfoQuery
	 * @return
	 */
	public List<OrderInfo> selectByVenderUserIdForPage(OrderInfoQuery orderInfoQuery);
	
	/**
	 * 商家根据条件查询订单数量(ForPage为功能实现中用到分页)
	 * @param orderInfoQuery
	 * @return
	 */
	public int countByConditionForPage(OrderInfoQuery orderInfoQuery);
	
	/**
	 * 商家根据订单号删除数据
	 * @param orderInfo 订单信息
	 */
	public Integer delPriceByOrderId(int orderId);
}