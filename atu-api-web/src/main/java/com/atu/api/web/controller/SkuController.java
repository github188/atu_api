package com.atu.api.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atu.api.web.base.BaseController;
import com.atu.api.service.SkuService;
import com.atu.api.service.result.Result;

@Controller
@RequestMapping("sku")
public class SkuController extends BaseController {
	
	private SkuService skuService;
	
	/**
	 * 依据SKU_ID查看SKU信息
	 * @param skuId
	 * @return
	 */
	@RequestMapping(value="getSkuBySkuId", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getSkuBySkuId(Integer skuId, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		if(skuId == null){
			Result result = new Result();
			result.setResultCode("1001");
			result.setResultMessage("skuId不能为空");
			return result;
		}
		return skuService.getSkuBySkuId(skuId);
	}

	public void setSkuService(SkuService skuService) {
		this.skuService = skuService;
	}

	
}
