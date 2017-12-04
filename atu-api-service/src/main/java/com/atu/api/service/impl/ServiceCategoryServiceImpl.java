package com.atu.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.atu.api.dao.ServiceCategoryDao;
import com.atu.api.domain.ServiceCategory;
import com.atu.api.domain.query.ServiceCategoryQuery;
import com.atu.api.domain.vo.ServiceCategoryVO;
import com.atu.api.service.ServiceCategoryService;
import com.atu.api.service.result.Result;
import com.atu.api.service.utils.EcUtils;

@Service(value="serviceCategoryService")
public class ServiceCategoryServiceImpl implements ServiceCategoryService {
	private static final Logger log = LoggerFactory.getLogger(ServiceCategoryServiceImpl.class);
	
	private ServiceCategoryDao serviceCategoryDao;

	/**
	 * 获取所有一级分类信息
	 */
	@Override
	public Result getAllParentCategory() {
		Result result = new Result();
		try{
			// 查询一级分类
			ServiceCategoryQuery serviceCategoryQuery = new ServiceCategoryQuery();
			serviceCategoryQuery.setCategoryLevel(1);
			
			result = this.selectCategoryByCondition(serviceCategoryQuery);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		
		return result;
	}

	/**
	 * 根据父分类ID获取分类信息
	 */
	@Override
	public Result getCategoryByParentId(Integer parentId) {
		Result result = new Result();
		try{
			// 查询子级分类
			ServiceCategoryQuery serviceCategoryQuery = new ServiceCategoryQuery();
			serviceCategoryQuery.setParentCategoryId(parentId);
			
			result = this.selectCategoryByCondition(serviceCategoryQuery);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		
		return result;
	}
	
	/**
	 * 获取分类信息
	 */
	private Result selectCategoryByCondition(ServiceCategoryQuery serviceCategoryQuery){
		Result result = new Result();
		List<ServiceCategory> list = serviceCategoryDao.selectByCondition(serviceCategoryQuery);
		
		if(list == null || list.size() == 0){
			result.setResultCode("7001");
			result.setResultMessage("分类列表不存在");
			return result;
		}
		
		List<ServiceCategoryVO> cList = new ArrayList<ServiceCategoryVO>();
		for (ServiceCategory serviceCategory : list) {
			ServiceCategoryVO serviceCategoryVO = new ServiceCategoryVO();
			serviceCategoryVO.setCategoryId(serviceCategory.getCategoryId());
			serviceCategoryVO.setCategoryName(serviceCategory.getCategoryName());
			cList.add(serviceCategoryVO);
		}
		
		result.setResult(cList);
		return result;
	}

	public void setServiceCategoryDao(ServiceCategoryDao serviceCategoryDao) {
		this.serviceCategoryDao = serviceCategoryDao;
	}

}
