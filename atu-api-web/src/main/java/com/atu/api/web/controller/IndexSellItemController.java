package com.atu.api.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atu.api.web.base.BaseController;
import com.atu.api.domain.query.IndexSellItemQuery;
import com.atu.api.service.IndexSellItemService;
import com.atu.api.service.result.Result;

/** 获取所有首页热销商品信息(分页) */

@Controller
@RequestMapping("indexSellItem")
public class IndexSellItemController extends BaseController {
	
	private IndexSellItemService indexSellItemService;
	
	/**
	 * 获取所有首页热销商品信息(分页)
	 */
	@RequestMapping(value="getIndexSellItemByPage", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getIndexSellItemByPage(IndexSellItemQuery indexSellItemQuery, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		return indexSellItemService.getIndexSellItemByPage(indexSellItemQuery);
	}

	public void setIndexSellItemService(IndexSellItemService indexSellItemService) {
		this.indexSellItemService = indexSellItemService;
	}
	
}
