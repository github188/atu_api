package com.atu.api.domain.vo;

/**
 * 收藏夹-商铺下的商品信息(页面展示)
 */
public class SellerItemVO {

	/** 自增ID */
    private Integer favoritesId;

	/** 商品ID */
    private Integer itemId;
    
    /** 商品名称 */
    private String itemName;
    
    /** 商品主图 */
    private String itemImage;
    
    /** 规格：500ml,600ml */
    private String salesPropertySet;

	public Integer getFavoritesId() {
		return favoritesId;
	}

	public void setFavoritesId(Integer favoritesId) {
		this.favoritesId = favoritesId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
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

	public String getSalesPropertySet() {
		return salesPropertySet;
	}

	public void setSalesPropertySet(String salesPropertySet) {
		this.salesPropertySet = salesPropertySet;
	}
    
}