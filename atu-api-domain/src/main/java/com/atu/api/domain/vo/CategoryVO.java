package com.atu.api.domain.vo;

import java.io.Serializable;

/**
 * 分类
 */
public class CategoryVO implements Serializable{
	private static final long serialVersionUID = 1L;

    /** 分类ID */
    private Integer categoryId;

    /** 分类名称 */
    private String categoryName;

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}