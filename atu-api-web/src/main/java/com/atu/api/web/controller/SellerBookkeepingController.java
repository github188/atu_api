package com.atu.api.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atu.api.web.base.BaseController;
import com.atu.api.domain.SellerBookkeeping;
import com.atu.api.domain.query.SellerBookkeepingQuery;
import com.atu.api.service.SellerBookkeepingService;
import com.atu.api.service.result.Result;

@Controller
@RequestMapping("/sellerBookkeeping")
public class SellerBookkeepingController extends BaseController {

	private SellerBookkeepingService sellerBookkeepingService;
	
	/**
	 * 添加记账本信息
	 * @param sellerBookkeeping 商家记账本
	 * @return
	 */
	@RequestMapping(value="addSellerBookkeeping", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result addSellerBookkeeping(SellerBookkeeping sellerBookkeeping, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		if(StringUtils.isBlank(sellerBookkeeping.getLinkman())){
			result.setResultCode("1001");
			result.setResultMessage("linkman不能为空");
			return result;
		}
		if(StringUtils.isBlank(sellerBookkeeping.getMobile())){
			result.setResultCode("1001");
			result.setResultMessage("mobile不能为空");
			return result;
		}
		if(sellerBookkeeping.getPaymentMoney() == null){
			result.setResultCode("1001");
			result.setResultMessage("paymentMoney不能为空");
			return result;
		}
		if(sellerBookkeeping.getTradeMoney() == null){
			result.setResultCode("1001");
			result.setResultMessage("paymentMoney不能为空");
			return result;
		}
		sellerBookkeeping.setVenderUserId(getUserId(request));
		return sellerBookkeepingService.addSellerBookkeeping(sellerBookkeeping);
	}
	
	/**
	 * 根据相应的条件查询记账本信息---分页查询
	 * @param sellerBookkeepingQuery 商家记账本
	 * @return
	 */
	@RequestMapping(value="getSellerBookkeepingByPage", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getSellerBookkeepingByPage(SellerBookkeepingQuery sellerBookkeepingQuery, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		sellerBookkeepingQuery.setVenderUserId(getUserId(request));
		return sellerBookkeepingService.getSellerBookkeepingByPage(sellerBookkeepingQuery);
	}
	
	/**
	 * 根据相应的条件查询满足条件的记账本信息,并累加金额
	 * @param sellerBookkeepingQuery 商家记账本
	 * @return
	 */
	@RequestMapping(value="selectSellerBookkeepingForCountMoney", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result selectSellerBookkeepingForCountMoney(SellerBookkeepingQuery sellerBookkeepingQuery, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		sellerBookkeepingQuery.setVenderUserId(getUserId(request));
		return sellerBookkeepingService.selectSellerBookkeepingForCountMoney(sellerBookkeepingQuery);
	}

	public void setSellerBookkeepingService(
			SellerBookkeepingService sellerBookkeepingService) {
		this.sellerBookkeepingService = sellerBookkeepingService;
	}


}
