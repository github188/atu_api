package com.atu.api.domain.query;

import java.io.Serializable;

/**
 * 服务信息分类查询条件
 *
 */
public class ServiceCategoryQuery extends BaseSearchForMysqlVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /** 分类ID */
    private Integer categoryId;

    /** 父分类ID */
    private Integer parentCategoryId;
    
    /** 分类等级 */
    private Integer categoryLevel;

    /** 有效性 */
    private Integer yn;

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(Integer parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}

	public Integer getYn() {
		return yn;
	}

	public void setYn(Integer yn) {
		this.yn = yn;
	}

	public Integer getCategoryLevel() {
		return categoryLevel;
	}

	public void setCategoryLevel(Integer categoryLevel) {
		this.categoryLevel = categoryLevel;
	}
    
}