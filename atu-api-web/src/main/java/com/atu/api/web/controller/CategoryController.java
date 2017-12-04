package com.atu.api.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atu.api.web.base.BaseController;
import com.atu.api.service.CategoryService;
import com.atu.api.service.result.Result;

/** 商品分类信息  */

@Controller
@RequestMapping("/category")
public class CategoryController extends BaseController {
	
	
	private CategoryService categoryService;
	
	/**
	 * 获取所有一级分类信息
	 * @return
	 */
	@RequestMapping(value="getAllParentCategory", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getAllParentCategory(HttpServletRequest request,HttpServletResponse response, ModelMap context){
		return categoryService.getAllParentCategory();
	}
	
	/**
	 * 根据父分类ID获取分类信息
	 * @param parentId 父类id
	 * @return
	 */
	@RequestMapping(value="getCategoryByParentId", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getCategoryByParentId(Integer parentId, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		if(parentId == null){
			Result result = new Result();
			result.setResultCode("1001");
			result.setResultMessage("parentId不能为空");
			return result;
		}
		return categoryService.getCategoryByParentId(parentId);
	}
	
	/**
	 * 查询二级分类下属性销售属性信息
	 * @param categoryId 分类id
	 * @return
	 */
	@RequestMapping(value="getSalePropertiesByCategoryId", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getSalePropertiesByCategoryId(Integer categoryId, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		if(categoryId == null){
			Result result = new Result();
			result.setResultCode("1001");
			result.setResultMessage("categoryId不能为空");
			return result;
		}
		return categoryService.getPropertiesByCategoryId(categoryId, 3);
	}
	
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
}
