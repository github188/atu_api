package com.atu.api.domain.vo;


/**
 * 购物车-商铺下的商品信息(页面展示)
 */
public class CartSkuVO {

	/** 商品ID */
    private Integer itemId;
    
    /** 属性ID */
    private Integer skuId;

    /** SKU数量 */
    private Integer num;

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

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
}
