package com.atu.api.web.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atu.api.web.base.BaseController;
import com.atu.api.common.utils.DESUtil;
import com.atu.api.common.utils.JsonUtils;
import com.atu.api.domain.Item;
import com.atu.api.domain.query.ItemQuery;
import com.atu.api.service.ItemService;
import com.atu.api.service.result.Result;

/** 根据条件获取商品列表  */

@Controller
@RequestMapping("item")
public class ItemController extends BaseController {
	
	private ItemService itemService;
	
	/**
	 * 根据条件获取商品列表
	 * @param queryItem
	 * @param token
	 * @return
	 */
	@RequestMapping(value="getItemByPage", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getItemByPage(String queryItem, String token, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		ItemQuery itemQuery = new ItemQuery();
		
		try{
			itemQuery = JsonUtils.readValue(queryItem, ItemQuery.class);
		}catch (Exception e) {
			result.setResultCode("1001");
			result.setResultMessage("queryItem格式不正确");
			return result;
		}
		if(StringUtils.isNotBlank(token)){
			try{
				String value = DESUtil.decrypt(token, "gs2y601z");
				if(StringUtils.isNotBlank(value)){
					String [] tokenArr = value.split("-");
					if(tokenArr != null && tokenArr.length == 3){
						itemQuery.setUserId(Integer.parseInt(tokenArr[0]));
					}
				}
			}catch (Exception e) {
				result.setResultCode("1001");
				result.setResultMessage("token请求参数有误");
				return result;
			}
		}
		
		
		return itemService.getItemByPage(itemQuery);
	}
	
	/**
	 *获取商品列表的所有产地(省级)
	 * @return
	 */
	@RequestMapping(value="getItemOrigin", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getItemOrigin(HttpServletRequest request,HttpServletResponse response, ModelMap context){
		return itemService.getItemOrigin();
	}
	
	/**
	 * 根据itemId获取商品信息
	 * @param itemId 商品ID
	 * @param token
	 * @return
	 */
	@RequestMapping(value="getItemByItemId", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getItemByItemId(Integer itemId, String token, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		Integer userId = null;
		
		if(itemId == null){
			result.setResultCode("1001");
			result.setResultMessage("itemId不能为空");
			return result;
		}
		
		if(StringUtils.isNotBlank(token)){
			try{
				String value = DESUtil.decrypt(token, "gs2y601z");
				if(StringUtils.isNotBlank(value)){
					String [] tokenArr = value.split("-");
					if(tokenArr != null && tokenArr.length == 3){
						userId = Integer.parseInt(tokenArr[0]);
					}
				}
			}catch (Exception e) {
				result.setResultCode("1001");
				result.setResultMessage("token请求参数有误");
				return result;
			}
		}
		return itemService.getItemByItemId(itemId, userId);
	}
	
	/**
	 * 依据用户ID查询商品信息
	 * @param itemQuery 商品请求
	 * @return
	 */
	@RequestMapping(value="getItemsByUserId", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getItemsByUserId(ItemQuery itemQuery, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		itemQuery.setUserId(getUserId(request));
		return itemService.getItemsByVenderUserId(itemQuery);
	}
	
	/**
	 * 依据卖家用户ID查询商品信息
	 * @param itemQuery 商品请求
	 * @return
	 */
	@RequestMapping(value="getItemsByVenderUserId", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getItemsByVenderUserId(ItemQuery itemQuery, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		if(itemQuery.getVenderUserId() == null){
			Result result = new Result();
			result.setResultCode("1001");
			result.setResultMessage("venderUserId不能为空");
			return result;
		}
		return itemService.getItemsByVenderUserId(itemQuery);
	}
	
	/**
	 * 依据商品ID查询商品下SKU信息
	 */
	@RequestMapping(value="getSkusByItemId", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getSkusByItemId(Integer itemId, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		if(itemId == null){
			Result result = new Result();
			result.setResultCode("1001");
			result.setResultMessage("itemId不能为空");
			return result;
		}
		return itemService.getSkusByItemId(itemId);
	}
	
	/**
	 * 修改商品信息
	 * @param item 商品
	 * @return
	 */
	@RequestMapping(value="updateItem", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result updateItem(Item item, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		if(item.getItemId() == null){
			result.setResultCode("1001");
			result.setResultMessage("itemId不能为空");
			return result;
		}
		item.setModified(new Date());
		return itemService.updateItem(item);
	}
	
	/**
	 * 依据商品ID查询商品轮播图
	 * @param itemId 商品ID
	 * @return
	 */
	@RequestMapping(value="getItemImages", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getItemImages(Integer itemId, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		if(itemId == null){
			Result result = new Result();
			result.setResultCode("1001");
			result.setResultMessage("itemId不能为空");
		}
		return itemService.getItemImages(itemId);
	}
	
	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}
	
}
