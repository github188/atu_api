package com.atu.api.service;

import java.math.BigDecimal;

import com.atu.api.service.result.Result;

public interface CouponService {
	
	/**
	 * 查询我的优惠券
	 * @param userId 用户ID
	 * @return
	 */
	public Result selectByUserId(Integer userId);
	
	/**
	 * 使用优惠券
	 * @param userId 用户ID
	 * @param price 价格
	 * @return
	 */
	public Result selectUseByUserId(Integer userId, BigDecimal price);
	
	/**
	 * 
	 * @param userId 用户ID
	 * @param couponsId 优惠券ID
	 * @return
	 */
	public Result drawCoupon(Integer userId, Integer couponsId);
}
