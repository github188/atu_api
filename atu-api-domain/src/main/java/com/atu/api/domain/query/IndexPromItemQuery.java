package com.atu.api.domain.query;

import java.io.Serializable;

/**
 * 首页促销信息查询条件
 */
public class IndexPromItemQuery extends BaseSearchForMysqlVo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** 促销ID */
	private Integer id;
	
	/** 有效状态 */
	private Integer yn;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getYn() {
		return yn;
	}
	public void setYn(Integer yn) {
		this.yn = yn;
	}
	
}
