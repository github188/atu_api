package com.atu.api.domain.vo;

import java.math.BigDecimal;


/**
 * 购物车-商铺下的商品信息(页面展示)
 */
public class CartOrderItemVO {
	/** 商品编号 */
    private Integer itemId;
    
    /** 商品属性 */
    private Integer skuId;
	
    /** 商品名称 */
    private String itemName;

    /** 商品图片路径 */
    private String itemImage;

    /** 商品数量 */
    private Integer skuNum;
    
    /** 商品价格 */
	private BigDecimal itemPrice;

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemImage() {
		return itemImage;
	}

	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
	}

	public Integer getSkuNum() {
		return skuNum;
	}

	public void setSkuNum(Integer skuNum) {
		this.skuNum = skuNum;
	}

	public BigDecimal getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
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
}
