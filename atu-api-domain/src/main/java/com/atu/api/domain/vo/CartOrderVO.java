package com.atu.api.domain.vo;

import java.math.BigDecimal;
import java.util.List;

import com.atu.api.domain.ConsigneeInfo;

/**
 *	购物车-结算确认订单(页面展示)
 */
public class CartOrderVO {
	/** 收货人地址 */
	private ConsigneeInfo consigneeInfo;
	
	/** 确认购物车信息 */
	private List<CartOrderItemVO> cartList;
	
	/** 商家店铺下的商品信息 */
	private List<CartSkuVO> skus;
	
	/** 确认商品数量 */
	private Integer cartCount;
	
	/** 确认商品促销总价格 */
	private BigDecimal cartPromPrice;
	
	/** 确认商品直降总价格 */
	private BigDecimal cartDeductionPrice;
	
	/** 运费 */
	private BigDecimal cartFreight;

	public ConsigneeInfo getConsigneeInfo() {
		return consigneeInfo;
	}

	public void setConsigneeInfo(ConsigneeInfo consigneeInfo) {
		this.consigneeInfo = consigneeInfo;
	}

	public List<CartOrderItemVO> getCartList() {
		return cartList;
	}

	public void setCartList(List<CartOrderItemVO> cartList) {
		this.cartList = cartList;
	}

	public Integer getCartCount() {
		return cartCount;
	}

	public void setCartCount(Integer cartCount) {
		this.cartCount = cartCount;
	}

	public BigDecimal getCartPromPrice() {
		return cartPromPrice;
	}

	public void setCartPromPrice(BigDecimal cartPromPrice) {
		this.cartPromPrice = cartPromPrice;
	}

	public BigDecimal getCartDeductionPrice() {
		return cartDeductionPrice;
	}

	public void setCartDeductionPrice(BigDecimal cartDeductionPrice) {
		this.cartDeductionPrice = cartDeductionPrice;
	}

	public BigDecimal getCartFreight() {
		return cartFreight;
	}

	public void setCartFreight(BigDecimal cartFreight) {
		this.cartFreight = cartFreight;
	}

	public List<CartSkuVO> getSkus() {
		return skus;
	}

	public void setSkus(List<CartSkuVO> skus) {
		this.skus = skus;
	}
}
