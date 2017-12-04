package com.atu.api.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atu.api.web.base.BaseController;
import com.atu.api.common.utils.JsonUtils;
import com.atu.api.domain.CartInfo;
import com.atu.api.domain.been.CartUpdateBeen;
import com.atu.api.domain.been.CartUpdateBeenList;
import com.atu.api.service.CartInfoService;
import com.atu.api.service.result.Result;

/** 购物车信息  */

@Controller
@RequestMapping("cartInfo")
@SuppressWarnings("unchecked")
public class CartInfoController extends BaseController {
	
	
	private CartInfoService cartInfoService;
	
	/**
	 * 查询购物车信息
	 * @return
	 */
	@RequestMapping(value="selectCartInfo", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result selectCartInfo(HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		return cartInfoService.selectCartInfo(getUserId(reuqest));
	}
	
	/**
	 * 添加购物车信息
	 * @param cartInfo 购物车
	 * @return
	 */
	@RequestMapping(value="addCartInfo", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result addCartInfo(CartInfo cartInfo, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();

		if(cartInfo.getSkuId() == null){
			result.setResultCode("1001");
			result.setResultMessage("属性编号不能为空");
			return result;
		}
		cartInfo.setUserId(getUserId(reuqest));
		cartInfo.setIp(getRemoteIp(reuqest));
		
		return cartInfoService.addCartInfo(cartInfo);
	}
	
	/**
	 * 修改购物车信息
	 * @param cartInfo 购物车
	 * @return
	 */
	@RequestMapping(value="updateCartInfo", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result updateCartInfo(String cartInfo, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		
		if(cartInfo == null){
			result.setResultCode("1001");
			result.setResultMessage("cartInfo为空");
			return result;
		}
		
		CartUpdateBeenList cartUpdateBeenList = null;
		String listString = "{\"list\":"+cartInfo+"}";
		
		try{
			cartUpdateBeenList = JsonUtils.readValue(listString, CartUpdateBeenList.class);
		}catch (Exception e) {
			result.setResultCode("1001");
			result.setResultMessage("cartInfo格式不正确");
			return result;
		}
		
		List<CartUpdateBeen> cartUpdateList = cartUpdateBeenList.getList();
		
		for (CartUpdateBeen cartUpdateBeen : cartUpdateList) {
			if(cartUpdateBeen.getCartId() == null || cartUpdateBeen.getSkuNum() == null){
				result.setResultCode("1001");
				result.setResultMessage("cartInfo格式不正确");
				return result;
			}
		}
		
		return cartInfoService.updateCartInfo(cartUpdateList);
	}
	
	/**
	 * 确认购物车订单
	 * @param cartIds 购物车自增ID
	 * @return
	 */
	@RequestMapping(value="confirmCartInfo", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result confirmCartInfo(String cartIds, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		
		List<Integer> cartIdList = null;
		
		try{
			cartIdList = JsonUtils.readValue(cartIds, List.class);
		}catch (Exception e) {
			result.setResultCode("1001");
			result.setResultMessage("cartInfo格式不正确");
			return result;
		}
		
		return cartInfoService.confirmCartInfo(getUserId(reuqest), cartIdList);
	}
	
	/**
	 * 立即购买
	 * @param cartInfo 购物车
	 * @return
	 */
	@RequestMapping(value="buyNow", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result buyNow(CartInfo cartInfo, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		
		if(cartInfo.getSkuId() == null){
			result.setResultCode("1001");
			result.setResultMessage("skuId不能为空");
			return result;
		}
		if(cartInfo.getSkuNum() == null){
			result.setResultCode("1001");
			result.setResultMessage("skuNum不能为空");
			return result;
		}
		
		cartInfo.setUserId(getUserId(reuqest));
		return cartInfoService.buyNow(cartInfo);
	}
	
	/**
	 * 删除购物车
	 * @param cartIds 购物车自增ID
	 * @return
	 */
	@RequestMapping(value="delCartInfo", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result delCartInfo(String cartIds, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		
		List<Integer> cartIdList = null;
		
		try{
			cartIdList = JsonUtils.readValue(cartIds, List.class);
		}catch (Exception e) {
			result.setResultCode("1001");
			result.setResultMessage("cartIds格式不正确");
			return result;
		}
		
		return cartInfoService.delCartInfo(cartIdList);
	}

	public void setCartInfoService(CartInfoService cartInfoService) {
		this.cartInfoService = cartInfoService;
	}
}
