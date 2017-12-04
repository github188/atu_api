package com.atu.api.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 首页促销信息
 * 
 */
public class IndexPromItem implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** 自增ID */
	private Integer id;
	
	/** 促销名称 */
	private String promName;
	
	/** 促销介绍 */
	private String promIntro;

	/** 促销链接地址 */
	private String promUrl;

	/** 促销图片地址 */
	private String promImgUrl;

	/** 促销类型 */
	private Integer promType;
	
	/** 促销商品ID */
	private Integer itemId;

	/** 促销属性ID */
	private Integer skuId;

	/** 排序字段 */
	private Integer sortNum;

	/** 有效状态 */
	private Integer yn;

	/** 创建时间 */
	private Date created;

	/** 修改时间 */
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
	public Integer getPromType() {
		return promType;
	}
	public void setPromType(Integer promType) {
		this.promType = promType;
	}
	public String getPromName() {
		return promName;
	}
	public void setPromName(String promName) {
		this.promName = promName;
	}
	public String getPromIntro() {
		return promIntro;
	}
	public void setPromIntro(String promIntro) {
		this.promIntro = promIntro;
	}
	public String getPromUrl() {
		return promUrl;
	}
	public void setPromUrl(String promUrl) {
		this.promUrl = promUrl;
	}
	public Integer getSkuId() {
		return skuId;
	}
	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}
	public String getPromImgUrl() {
		return promImgUrl;
	}
	public void setPromImgUrl(String promImgUrl) {
		this.promImgUrl = promImgUrl;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Integer getYn() {
		return yn;
	}
	public void setYn(Integer yn) {
		this.yn = yn;
	}
	public Integer getSortNum() {
		return sortNum;
	}
	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
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
