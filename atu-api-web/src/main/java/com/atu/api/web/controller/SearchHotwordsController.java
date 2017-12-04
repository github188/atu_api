package com.atu.api.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atu.api.web.base.BaseController;
import com.atu.api.service.SearchHotwordsService;
import com.atu.api.service.result.Result;

@Controller
@RequestMapping("searchHotwords")
public class SearchHotwordsController extends BaseController {
	
	private SearchHotwordsService searchHotwordsService;
	
	/**
	 * 获取所有热搜词
	 * @return
	 */
	@RequestMapping(value="selectSearchHotwords", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result selectSearchHotwords(HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		return searchHotwordsService.selectSearchHotwords();
	}

	public void setSearchHotwordsService(SearchHotwordsService searchHotwordsService) {
		this.searchHotwordsService = searchHotwordsService;
	}
}
