package com.atu.api.domain.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 购物车-商铺信息(页面展示)
 */
public class CartBusinessVO {
	/** 商家ID */
	private Integer venderUserId;
	
	/** 商家店铺名称 */
	private String shopName;
	
	/** 商家店铺图片 */
	private String shopImage;
	
	/** 商家店铺下的商品信息 */
	private List<CartItemVO> itemList;
	
	/** 购买数量 */
	private Integer skuNum;
	
	/** 促销价格 */
    private BigDecimal promPrice;	
    
    /** 天宝价格 */
	private BigDecimal tbPrice;
	
	public Integer getVenderUserId() {
		return venderUserId;
	}
	
	public void setVenderUserId(Integer venderUserId) {
		this.venderUserId = venderUserId;
	}
	
	public String getShopName() {
		return shopName;
	}
	
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	
	public String getShopImage() {
		return shopImage;
	}
	
	public void setShopImage(String shopImage) {
		this.shopImage = shopImage;
	}
	
	public List<CartItemVO> getItemList() {
		return itemList;
	}
	
	public void setItemList(List<CartItemVO> itemList) {
		this.itemList = itemList;
	}

	public Integer getSkuNum() {
		return skuNum;
	}

	public void setSkuNum(Integer skuNum) {
		this.skuNum = skuNum;
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
}
