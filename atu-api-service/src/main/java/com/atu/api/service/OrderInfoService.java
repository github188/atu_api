package com.atu.api.service;

import java.math.BigDecimal;
import java.util.List;

import com.atu.api.domain.OrderInfo;
import com.atu.api.domain.OrderDetail;
import com.atu.api.domain.query.OrderInfoQuery;
import com.atu.api.service.result.Result;

public interface OrderInfoService {
	/**
	 * 下单接口
	 * @param orderInfo
	 * @return
	 */
	public Result submit(OrderInfo orderInfo, List<OrderDetail> orderDetailList, String coupons);
	/**
	 * 用户所属订单列表查看（细分）
	 * @param orderId
	 * @param lockReason
	 * @return
	 */
	public Result getOrderInfosByOrderInfoQuery(OrderInfoQuery orderInfoQuery);
	/**
	 * 用户收货确认
	 * @param orderId
	 * @param lockReason
	 * @return
	 */
	public Result confirmGetGoods(Integer orderId, Integer userId);
    /**
	 * 根据订单号和用户id获取订单状态
	 * @param orderId
	 * @return
	 */
	public Result getOrderStatusByOrderIdAndUserId(Integer orderId, Integer userId);
	/**
	 * 根据订单号和用户id查询订单详细信息
	 * @param orderId
	 * @return
	 */
	public Result getOrderInfoByOrderIdAndUserId(Integer orderId, Integer userId);
	
	
	/**
	 * 商家所属订单列表查看（细分）
	 * @param orderId
	 * @param lockReason
	 * @return
	 */
	public Result getOrderInfosByVenderUserId(OrderInfoQuery orderInfoQuery);
	/**
	 * 商家确认收款
	 * @param orderId
	 * @param lockReason
	 * @return
	 */
	public Result confirmGetPrice(Integer orderId, Integer venderUserId);
	/**
	 * 商家确认尾款
	 * @param orderId
	 * @param lockReason
	 * @return
	 */
	public Result confirmGetLastPrice(Integer orderId, Integer venderUserId);
	/**
	 * 商家发货确认
	 * @param orderId
	 * @param lockReason
	 * @return
	 */
	public Result confirmSendOut(Integer orderId, Integer userId ,String express);
	/**
	 * 商家订单完成确认
	 * @param orderId
	 * @param lockReason
	 * @return
	 */
	public Result orderSuccess(Integer orderId);
	/**
	 * 商家订单锁定
	 * @param orderId
	 * @param lockReason
	 * @return
	 */
	public Result lockOrder(Integer orderId, String lockReason);
	/**
	 * 商家订单解锁
	 * @param orderId
	 * @return
	 */
	public Result unlockOrder(Integer orderId);
	/**
	 * 根据订单号和商家id获取订单详细信息(商家)
	 * @param orderId
	 * @return
	 */
	public Result getOrderInfoByOrderIdAndVenderUserId(Integer orderId,Integer userId);
	/**
	 * 根据订单号修改支付金额
	 * @param orderInfoQuery 订单
	 * @return
	 */
	public Result UpdateOderInfoPriceByOrderId(OrderInfoQuery orderInfoQuery);
	/**
	 * 此接口未使用
	 * @param orderId
	 * @param userId
	 * @return
	 */
	public Result createTradeNo(Integer orderId, Integer userId);
	


	
	/**
	 * 根据相应的条件查询订单信息
	 * @param orderInfoQuery
	 * @return
	 */
	public List<OrderInfo> selectByCondition(OrderInfoQuery orderInfoQuery);
	
	/**
	 * 依据订单 修改订单信息
	 * @param orderInfo
	 */
	public void modify(OrderInfo orderInfo);
	
	/**
	 * 根据订单号查询订单信息
	 * @param orderId 订单号
	 * @return
	 */
	public OrderInfo seleclById(Integer orderId);
	
	/**
	 * 微信支付
	 * @param orderId 订单号
	 * @param tradeNo 结算单号
	 * @param price 价格
	 * @param timeEnd 支付时间
	 * @param orderFlag 扩展字段订单状态
	 */
	public void payWX(Integer orderId, String tradeNo, BigDecimal price,String timeEnd,Integer orderFlag);
	
	/**
	 * 验证订单号
	 * @param orderId 订单号
	 * @return
	 */
	public Result checkOrderId(Integer orderId);
	
	/**
	 * 根据商家ID和订单号模糊查询订单信息
	 * @param orderId 订单号
	 * @param userId 用户ID
	 * @return
	 */
	public Result searchByOrderId(Integer orderId,Integer userId);
}
