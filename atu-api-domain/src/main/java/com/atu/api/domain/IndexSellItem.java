package com.atu.api.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 热销商品 
 *
 */
public class IndexSellItem implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String sellName;
	private String sellIntro;
	private String sellUrl;
	private String sellImgUrl;
	private Integer sellType;
	private Integer itemId;
	private Integer skuId;
	private Integer sortNum;
	private Integer yn;
	private Date created;
	private Date modified;
	/** 增加字段 */
	private BigDecimal promPrice;	// 促销价格
	private BigDecimal tbPrice;		// 天宝价格
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSellName() {
		return sellName;
	}
	public void setSellName(String sellName) {
		this.sellName = sellName;
	}
	public String getSellIntro() {
		return sellIntro;
	}
	public void setSellIntro(String sellIntro) {
		this.sellIntro = sellIntro;
	}
	public String getSellUrl() {
		return sellUrl;
	}
	public void setSellUrl(String sellUrl) {
		this.sellUrl = sellUrl;
	}
	public String getSellImgUrl() {
		return sellImgUrl;
	}
	public void setSellImgUrl(String sellImgUrl) {
		this.sellImgUrl = sellImgUrl;
	}
	public Integer getSellType() {
		return sellType;
	}
	public void setSellType(Integer sellType) {
		this.sellType = sellType;
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
	public Integer getSortNum() {
		return sortNum;
	}
	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
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
}
