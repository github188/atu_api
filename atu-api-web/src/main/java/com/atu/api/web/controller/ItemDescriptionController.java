package com.atu.api.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atu.api.web.base.BaseController;
import com.atu.api.service.ItemDescriptionService;
import com.atu.api.service.result.Result;

@Controller
@RequestMapping("/item/description")
public class ItemDescriptionController extends BaseController {
	
	private ItemDescriptionService itemDescriptionService;
	
	/**
	 * 依据商品ID查看商品介绍
	 * @param itemId 商品ID
	 * @return
	 */
	@RequestMapping(value="getItemDescriptionByItemId", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getItemDescriptionByItemId(Integer itemId, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		if(itemId == null){
			Result result = new Result();
			result.setResultCode("1001");
			result.setResultMessage("itemId不能为空");
			return result;
		}
		return this.itemDescriptionService.getItemDescriptionByItemId(itemId);
	}
	
	public void setItemDescriptionService(
			ItemDescriptionService itemDescriptionService) {
		this.itemDescriptionService = itemDescriptionService;
	}
	
}
