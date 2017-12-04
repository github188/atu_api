package com.atu.api.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atu.api.web.base.BaseController;
import com.atu.api.service.IndexImageService;
import com.atu.api.service.result.Result;

/** 首页轮播图信息 */

@Controller
@RequestMapping("/indexImage")
public class IndexImageController extends BaseController {
	
	private IndexImageService indexImageService;
	
	/**
	 * 首页轮播图接口信息
	 * @return
	 */
	@RequestMapping(value="getIndexImages", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getIndexImages(HttpServletRequest request,HttpServletResponse response, ModelMap context){
		return indexImageService.getIndexImages();
	}

	public void setIndexImageService(IndexImageService indexImageService) {
		this.indexImageService = indexImageService;
	}


}
