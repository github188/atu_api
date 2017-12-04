package com.atu.api.domain.query;

import java.io.Serializable;
import java.util.Date;

/**
 * 推荐商品 
 *
 */
public class IndexRecommendItemQuery extends BaseSearchForMysqlVo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String recommendName;
	private String recommendIntro;
	private String recommendUrl;
	private String recommendImgUrl;
	private Integer recommendType;
	private Integer itemId;
	private Integer skuId;
	private Integer sortNum;
	private Integer yn;
	private Date created;
	private Date modified;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRecommendName() {
		return recommendName;
	}
	public void setRecommendName(String recommendName) {
		this.recommendName = recommendName;
	}
	public String getRecommendIntro() {
		return recommendIntro;
	}
	public void setRecommendIntro(String recommendIntro) {
		this.recommendIntro = recommendIntro;
	}
	public String getRecommendUrl() {
		return recommendUrl;
	}
	public void setRecommendUrl(String recommendUrl) {
		this.recommendUrl = recommendUrl;
	}
	public String getRecommendImgUrl() {
		return recommendImgUrl;
	}
	public void setRecommendImgUrl(String recommendImgUrl) {
		this.recommendImgUrl = recommendImgUrl;
	}
	public Integer getRecommendType() {
		return recommendType;
	}
	public void setRecommendType(Integer recommendType) {
		this.recommendType = recommendType;
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
	
}
