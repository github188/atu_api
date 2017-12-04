package com.atu.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.atu.api.dao.CategoryDao;
import com.atu.api.dao.PropertyDao;
import com.atu.api.dao.PropertyValueDao;
import com.atu.api.domain.Category;
import com.atu.api.domain.Property;
import com.atu.api.domain.PropertyValue;
import com.atu.api.domain.query.CategoryQuery;
import com.atu.api.domain.query.PropertyQuery;
import com.atu.api.domain.query.PropertyValueQuery;
import com.atu.api.domain.vo.CategoryVO;
import com.atu.api.service.CategoryService;
import com.atu.api.service.result.Result;
import com.atu.api.service.utils.EcUtils;

@Service(value="categoryService")
public class CategoryServiceImpl implements CategoryService {
	private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
	
	private CategoryDao categoryDao;
	private PropertyDao propertyDao;
	private PropertyValueDao propertyValueDao;

	/**
	 * 获取所有一级分类信息
	 */
	@Override
	public Result getAllParentCategory() {
		Result result = new Result();
		try{
			// 查询一级分类
			CategoryQuery categoryQuery = new CategoryQuery();
			categoryQuery.setCategoryLevel(1);

			result = this.selectCategoryByCondition(categoryQuery);
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
			// 根据父分类ID获取分类信息
			CategoryQuery categoryQuery = new CategoryQuery();
			categoryQuery.setParentCategoryId(parentId);

			result = this.selectCategoryByCondition(categoryQuery);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		
		return result;
	}

	/**
	 * 查询二级分类下属性销售属性信息
	 */
	@Override
	public Result getPropertiesByCategoryId(Integer categoryId, Integer propertyType) {
		Result result = new Result();
		try{
			PropertyQuery propertyQuery = new PropertyQuery();
			propertyQuery.setCategoryId(categoryId);
			propertyQuery.setPropertyType(propertyType);
			propertyQuery.setYn(1);
			
			List<Property> list = propertyDao.selectByCondition(propertyQuery);
			if(list == null || list.size() == 0){
				result.setResultCode("7002");
				result.setResultMessage("属性列表不存在");
				return result;
			}
			result.setResult(list);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	
	/**
	 * 根据属性ID和商家ID获取属性值列表
	 */
	@Override
	public Result getPropertyValuesByVenderUserIdAndPropertyId(
			Integer venderUserId, Integer propertyId) {
		Result result = new Result();
		try{
			PropertyValueQuery propertyValueQuery = new PropertyValueQuery();
			//venderUserId= 35;
			propertyValueQuery.setVenderUserId(venderUserId);
			propertyValueQuery.setPropertyId(propertyId);
			List<PropertyValue> list = propertyValueDao.selectByCondition(propertyValueQuery);
			if(list == null || list.size() == 0){
				result.setResultCode("7003");
				result.setResultMessage("属性值列表不存在");
				return result;
			}
			result.setResult(list);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	/**
	 * 添加自定义属性值信息
	 * @param propertyValue
	 * @return
	 */
	@Override
	public Result addPropertyValue(PropertyValue propertyValue) {
		Result result = new Result();
		try{
			propertyValueDao.insert(propertyValue);
			result.setResult(true);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			result.setResultCode("500");
			result.setResultMessage("服务异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * 修改自定义属性值信息
	 */
	@Override
	public Result updatePropertyValues(PropertyValue propertyValue) {
		Result result = new Result();
		try{
			PropertyValue pv = propertyValueDao.selectByPropertyValueId(propertyValue.getPropertyValueId());
			if(pv == null){
				result.setResultCode("7004");
				result.setResultMessage("属性值信息不存在");
			}
			propertyValueDao.modify(propertyValue);
			EcUtils.setSuccessResult(result);
			result.setResult(true);
		}catch (Exception e) {
			log.error("", e);
			result.setResultCode("500");
			result.setResultMessage("服务异常，请稍后重试");
		}
		return result;
	}
	
	/**
	 * 获取分类信息
	 */
	private Result selectCategoryByCondition(CategoryQuery categoryQuery){
		Result result = new Result();
		List<Category> list = categoryDao.selectByCondition(categoryQuery);
		
		if(list == null || list.size() == 0){
			result.setResultCode("7001");
			result.setResultMessage("分类列表不存在");
			return result;
		}
		
		List<CategoryVO> cList = new ArrayList<CategoryVO>();
		for (Category category : list) {
			CategoryVO categoryVO = new CategoryVO();
			categoryVO.setCategoryId(category.getCategoryId());
			categoryVO.setCategoryName(category.getCategoryName());
			cList.add(categoryVO);
		}
		
		result.setResult(cList);
		return result;
	}

	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	public void setPropertyDao(PropertyDao propertyDao) {
		this.propertyDao = propertyDao;
	}

	public void setPropertyValueDao(PropertyValueDao propertyValueDao) {
		this.propertyValueDao = propertyValueDao;
	}



	
}
