package com.atu.api.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 热搜词
 * 
 */
public class SearchHotwords implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** 自增ID */
	private Integer id;
	
	/** 热搜词名称 */
	private String hotwordName;
	
	/** 排序字段 */
	private Integer sortNum;
	
	/** 有效状态 */
	private Integer yn;
	
	/** 创建时间 */
	private Date created;
	
	/** 修改时间 */
	private Date modified;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getHotwordName() {
		return hotwordName;
	}
	public void setHotwordName(String hotwordName) {
		this.hotwordName = hotwordName;
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
