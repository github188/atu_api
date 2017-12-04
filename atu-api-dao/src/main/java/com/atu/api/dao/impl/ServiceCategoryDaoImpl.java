package com.atu.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.atu.api.dao.ServiceCategoryDao;
import com.atu.api.domain.ServiceCategory;
import com.atu.api.domain.query.ServiceCategoryQuery;
import com.atu.api.domain.vo.ServiceCategoryVO;

@SuppressWarnings("unchecked")
public class ServiceCategoryDaoImpl extends SqlMapClientTemplate implements ServiceCategoryDao {

	@Override
	public List<ServiceCategory> selectByCondition(ServiceCategoryQuery serviceCategoryQuery) {
		return (List<ServiceCategory>)queryForList("ServiceCategory.selectByCondition", serviceCategoryQuery);
	}

}
