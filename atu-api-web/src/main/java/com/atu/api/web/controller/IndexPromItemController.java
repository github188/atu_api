package com.atu.api.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atu.api.web.base.BaseController;
import com.atu.api.domain.query.IndexPromItemQuery;
import com.atu.api.service.IndexPromItemService;
import com.atu.api.service.result.Result;

/** 获取首页促销商品信息(分页) */

@Controller
@RequestMapping("indexPromItem")
public class IndexPromItemController extends BaseController {
	
	private IndexPromItemService indexPromItemService;
	
	/**
	 * 获取首页促销商品信息(分页)
	 */
	@RequestMapping(value="getIndexPromItemByPage", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getIndexPromItemByPage(IndexPromItemQuery indexPromItemQuery, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		return indexPromItemService.getIndexPromItemByPage(indexPromItemQuery);
	}

	public void setIndexPromItemService(IndexPromItemService indexPromItemService) {
		this.indexPromItemService = indexPromItemService;
	}
	
}
