package com.atu.api.service;

import java.util.List;

import com.atu.api.domain.CartInfo;
import com.atu.api.domain.been.CartUpdateBeen;
import com.atu.api.service.result.Result;

public interface CartInfoService {
	
	/**
	 * 获取购物车信息
	 * @param userId 用户编号
	 * @return
	 */
	public Result selectCartInfo(Integer userId);
	
	/**
	 * 添加购物车信息
	 * @param cartInfo 购物车信息
	 * @return
	 */
	public Result addCartInfo(CartInfo cartInfo);
	
	/**
	 * 根据ID编号删除购物车信息
	 * @param cartId 购物车编号
	 * @return
	 */
	public Result updateCartInfo(List<CartUpdateBeen> cartUpdateList);
	
	/**
	 * 确认购物车订单
	 * @param userId 用户编号
	 * @return
	 */
	public Result confirmCartInfo(Integer userId, List<Integer> cartIdList);
	
	/**
	 * 根据ID编号删除购物车信息
	 * @param cartId 购物车编号
	 * @return
	 */
	public Result delCartInfo(List<Integer> cartIdList);
	
	/**
	 * 立即购买信息
	 * @param cartInfo
	 * @return
	 */
	public Result buyNow(CartInfo cartInfo);
}
