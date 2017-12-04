package com.atu.api.dao;

import java.util.List;

import com.atu.api.domain.ServiceCategory;
import com.atu.api.domain.query.ServiceCategoryQuery;

public interface ServiceCategoryDao {

	/**
	 * 根据条件查询服务分类信息(页面展示)
	 * @param serviceCategoryQuery
	 * @return
	 */
	public List<ServiceCategory> selectByCondition(ServiceCategoryQuery serviceCategoryQuery);
	
}
