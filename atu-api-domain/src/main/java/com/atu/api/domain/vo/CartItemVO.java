package com.atu.api.domain.vo;

import java.math.BigDecimal;


/**
 * 购物车-商铺下的商品信息(页面展示)
 */
public class CartItemVO {
	/** 购物车ID */
	private Integer cartId;

	/** 商品ID */
    private Integer itemId;
    
    /** 属性ID */
    private Integer skuId;

    /** 商品名称 */
    private String itemName;

    /** 商品图片路径 */
    private String itemImage;

    /** SKU数量 */
    private Integer skuNum;

    /** SKU属性名 */
    private String salesPropertyName;
    
    /** 最低起卖量 */
    private Integer leastBuy;
    
    /** 库存 */
    private Integer stock;
    
    /** 促销价格 */
    private BigDecimal promPrice;	
    
    /** 天宝价格 */
	private BigDecimal tbPrice;
    
	public Integer getCartId() {
		return cartId;
	}
	public void setCartId(Integer cartId) {
		this.cartId = cartId;
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
	public String getSalesPropertyName() {
		return salesPropertyName;
	}
	public void setSalesPropertyName(String salesPropertyName) {
		this.salesPropertyName = salesPropertyName;
	}
	public Integer getLeastBuy() {
		return leastBuy;
	}
	public void setLeastBuy(Integer leastBuy) {
		this.leastBuy = leastBuy;
	}
	public BigDecimal getTbPrice() {
		return tbPrice;
	}
	public void setTbPrice(BigDecimal tbPrice) {
		this.tbPrice = tbPrice;
	}
	public BigDecimal getPromPrice() {
		return promPrice;
	}
	public void setPromPrice(BigDecimal promPrice) {
		this.promPrice = promPrice;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
}
