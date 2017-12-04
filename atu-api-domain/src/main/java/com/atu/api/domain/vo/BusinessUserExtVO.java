package com.atu.api.domain.vo;

public class BusinessUserExtVO {
	/** 商铺编号 */
	private Integer id;
	
	/** 商铺名称 */
	private String shopName;
	
	/** 商铺图片 */
	private String shopImage;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

}
