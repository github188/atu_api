package com.atu.api.domain.query;

import java.io.Serializable;
import java.math.BigDecimal;


public class ItemQuery extends BaseSearchForMysqlVo implements Serializable{
	private static final long serialVersionUID = 1L;

	/** 商品ID */
    private Integer itemId;
    /** 商品名称 */
    private String itemName;
    /** 商品标题 */
    private String itemTitle;
    /** 商品类型 */
    private Integer itemType;
    
    /** 商品四级分类ID */
    private Integer categoryId;

    /** 商家ID */
    private Integer venderUserId;
    
    /** 用户ID */
    private Integer userId;

    /** 商品状态 */
    private Integer itemStatus;
    
    /** 可用状态 */
    private Integer yn;
    
    /** 商品品牌 */
    private Integer brandId;
    
    /** 商品产地(省级) */
    private Integer originProvince;
    
    /** 商品价格 */
    private BigDecimal maxPrice;
    private BigDecimal minPrice;
    
    /** 包装方式 */
    private Integer packingType;
    
    /** 单包净重量*/
    private Integer maxWeight;
    private Integer minWeight;
    
    /** 价格排序*/
    private String priceDesc;

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
	
	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getVenderUserId() {
		return venderUserId;
	}

	public void setVenderUserId(Integer venderUserId) {
		this.venderUserId = venderUserId;
	}

	public Integer getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(Integer itemStatus) {
		this.itemStatus = itemStatus;
	}

	public Integer getYn() {
		return yn;
	}

	public void setYn(Integer yn) {
		this.yn = yn;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public Integer getOriginProvince() {
		return originProvince;
	}

	public void setOriginProvince(Integer originProvince) {
		this.originProvince = originProvince;
	}

	public BigDecimal getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
	}

	public BigDecimal getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}

	public Integer getPackingType() {
		return packingType;
	}

	public void setPackingType(Integer packingType) {
		this.packingType = packingType;
	}

	public Integer getMaxWeight() {
		return maxWeight;
	}

	public void setMaxWeight(Integer maxWeight) {
		this.maxWeight = maxWeight;
	}

	public Integer getMinWeight() {
		return minWeight;
	}

	public void setMinWeight(Integer minWeight) {
		this.minWeight = minWeight;
	}

	public String getPriceDesc() {
		return priceDesc;
	}

	public void setPriceDesc(String priceDesc) {
		this.priceDesc = priceDesc;
	}
}