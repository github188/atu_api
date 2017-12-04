package com.atu.api.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *	购物车
 *
 */
public class CartInfo implements Serializable{
	private static final long serialVersionUID = 1L;

	/** 购物车自增ID */
    private Integer cartId;
    
    /** 用户ID */
    private Integer userId;
    
    /** 订单ID */
    private Integer orderId;
    
    /** 商家ID */
    private Integer venderUserId;
    
    /** 商品ID */
    private Integer itemId;
    
    /** 商品属性ID */
    private Integer skuId;
    
    /** 属性数量 */
    private Integer skuNum;
    
    /** IP地址 */
    private String ip;
    
    /** 有效状态 */
    private Integer yn;
    
    /** 创建时间 */
    private Date created;
    
    /** 修改时间 */
    private Date modified;
    
    /** 增加字段 */
    private BigDecimal deductionPrice;	// 直降价格
	private BigDecimal promPrice;		// 促销价格
	private BigDecimal tbPrice;			// 天宝价格

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

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
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

	public Integer getSkuNum() {
		return skuNum;
	}

	public void setSkuNum(Integer skuNum) {
		this.skuNum = skuNum;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getYn() {
		return yn;
	}

	public void setYn(Integer yn) {
		this.yn = yn;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public BigDecimal getPromPrice() {
		return promPrice;
	}

	public void setPromPrice(BigDecimal promPrice) {
		this.promPrice = promPrice;
	}

	public BigDecimal getTbPrice() {
		return tbPrice;
	}

	public void setTbPrice(BigDecimal tbPrice) {
		this.tbPrice = tbPrice;
	}

	public BigDecimal getDeductionPrice() {
		return deductionPrice;
	}

	public void setDeductionPrice(BigDecimal deductionPrice) {
		this.deductionPrice = deductionPrice;
	}
    
}
