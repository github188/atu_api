package com.atu.api.domain.query;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车信息查询条件
 */
public class CartInfoQuery extends BaseSearchForMysqlVo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** 购物车ID */
	private Integer cartId;
	
	/** 用户ID */
    private Integer userId;
    
    /** 商家用户ID */
    private Integer venderUserId;
    
    /** 商品ID */
    private Integer itemId;
    
    /** 属性ID */
    private Integer skuId;
    
    /** IP地址 */
    private String ip;
    
    /** 有效状态 */
    private Integer yn;
    
    /** 购物车编号集合 */
    private List<Integer> cartIdList;
   
	public Integer getCartId() {
		return cartId;
	}
	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getVenderUserId() {
		return venderUserId;
	}
	public void setVenderUserId(Integer venderUserId) {
		this.venderUserId = venderUserId;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Integer getSkuId() {
		return skuId;
	}
	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}
	public Integer getYn() {
		return yn;
	}
	public void setYn(Integer yn) {
		this.yn = yn;
	}
	public List<Integer> getCartIdList() {
		return cartIdList;
	}
	public void setCartIdList(List<Integer> cartIdList) {
		this.cartIdList = cartIdList;
	}
}
