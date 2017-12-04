package com.atu.api.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atu.api.web.base.BaseController;
import com.atu.api.domain.query.IndexRecommendItemQuery;
import com.atu.api.service.IndexRecommendItemService;
import com.atu.api.service.result.Result;

/** 获取所有首页新品推荐商品信息 */

@Controller
@RequestMapping("indexRecommendItem")
public class IndexRecommendItemController extends BaseController {
	
	private IndexRecommendItemService indexRecommendItemService;
	
	/**
	 * 获取所有首页新品推荐商品信息(分页)
	 */
	@RequestMapping(value="getIndexRecommendItemByPage", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getIndexRecommendItemByPage(IndexRecommendItemQuery indexRecommendItemQuery, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		return indexRecommendItemService.getIndexRecommendItemByPage(indexRecommendItemQuery);
	}

	public void setIndexRecommendItemService(IndexRecommendItemService indexRecommendItemService) {
		this.indexRecommendItemService = indexRecommendItemService;
	}
	
}
