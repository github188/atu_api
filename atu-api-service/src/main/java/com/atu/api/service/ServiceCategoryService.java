package com.atu.api.service;

import com.atu.api.service.result.Result;

public interface ServiceCategoryService {
	/**
	 * 获取所有一级分类
	 * @return
	 */
	public Result getAllParentCategory();
	
	/**
	 * 根据父类别，获取分类信息
	 * @return
	 */
	public Result getCategoryByParentId(Integer parentId);
}
