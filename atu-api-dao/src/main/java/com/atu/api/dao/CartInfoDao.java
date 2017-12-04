package com.atu.api.dao;

import java.util.List;

import com.atu.api.domain.CartInfo;
import com.atu.api.domain.query.CartInfoQuery;

public interface CartInfoDao {

	/**
	 * 根据查询条件获取信息
	 * @param cartInfoQuery
	 * @return
	 */
	public List<CartInfo> selectByCondition(CartInfoQuery cartInfoQuery);
	
	/**
	 * 根据查询条件获取购物车商家信息
	 * @param cartInfoQuery
	 * @return
	 */
	public List<Integer> selectBusinessIdByCondition(CartInfoQuery cartInfoQuery);
	
	/**
	 * 根据主键获取信息
	 * @param cartId
	 * @return
	 */
	public CartInfo selectById(Integer cartId);
	
	/**
	 * 添加信息
	 * @param cartInfo
	 * @return
	 */
	public Integer insert(CartInfo cartInfo);

	/**
	 * 修改信息
	 * @param cartInfo
	 */
	public Integer update(CartInfo cartInfo);
	
	/**
	 * 修改购物车商品数量
	 * @param cartInfo
	 */
	public Integer updateSkuNum(CartInfo cartInfo);
	
	/**
	 * 删除信息
	 * @param cartId
	 * @return
	 */
	public Integer delete(Integer cartId);
}
