package com.atu.api.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atu.api.web.base.BaseController;
import com.atu.api.service.ServiceCategoryService;
import com.atu.api.service.result.Result;

@Controller
@RequestMapping("/serviceCategory")
public class ServiceCategoryController extends BaseController {
	
	private ServiceCategoryService serviceCategoryService;
	
	/**
	 * 获取所有一级分类信息
	 * @return
	 */
	@RequestMapping(value="getAllParentCategory", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getAllParentCategory(HttpServletRequest request,HttpServletResponse response, ModelMap context){
		return serviceCategoryService.getAllParentCategory();
	}
	
	/**
	 * 根据父分类ID获取服务分类信息
	 * @param parentId 父分类ID
	 * @return
	 */
	@RequestMapping(value="getCategoryByParentId", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getCategoryByParentId(Integer parentId, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		if(parentId == null){
			result.setResultCode("1001");
			result.setResultMessage("parentId不能为空");
			return result;
		}
		return serviceCategoryService.getCategoryByParentId(parentId);
	}

	public void setServiceCategoryService(
			ServiceCategoryService serviceCategoryService) {
		this.serviceCategoryService = serviceCategoryService;
	}

}
