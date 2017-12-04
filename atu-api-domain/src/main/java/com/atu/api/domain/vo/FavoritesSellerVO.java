package com.atu.api.domain.vo;

public class FavoritesSellerVO {
	/** 收藏id */
	private Integer favoritesId;
	
	/** 收藏商铺编号 */
	private Integer venderUserId;
	
	/** 商铺名称 */
	private String shopName;
	
	/** 商铺图片 */
	private String shopImage;

	public Integer getFavoritesId() {
		return favoritesId;
	}

	public void setFavoritesId(Integer favoritesId) {
		this.favoritesId = favoritesId;
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

	public Integer getVenderUserId() {
		return venderUserId;
	}

	public void setVenderUserId(Integer venderUserId) {
		this.venderUserId = venderUserId;
	}
}
