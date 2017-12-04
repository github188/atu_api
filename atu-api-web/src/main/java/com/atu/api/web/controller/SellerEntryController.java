package com.atu.api.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atu.api.web.base.BaseController;
import com.atu.api.domain.SellerEntry;
import com.atu.api.domain.query.SellerEntryQuery;
import com.atu.api.service.SellerEntryService;
import com.atu.api.service.result.Result;

@Controller
@RequestMapping("/sellerEntry")
public class SellerEntryController extends BaseController {

	private SellerEntryService sellerEntryService;
	
	/**
	 * 商家补录订单金额接口
	 * @param sellerEntry 商家补录金额信息
	 * @return
	 */
	@RequestMapping(value="addSellerEntry", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result addSellerEntry(SellerEntry sellerEntry, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		if(sellerEntry.getOrderId() == null){
			result.setResultCode("1001");
			result.setResultMessage("orderId不能为空");
			return result;
		}
		if(sellerEntry.getOrderPayType() == null){
			result.setResultCode("1001");
			result.setResultMessage("orderPayType不能为空");
			return result;
		}
		if(sellerEntry.getPaymentMode() == null){
			result.setResultCode("1001");
			result.setResultMessage("paymentMode不能为空");
			return result;
		}
		
		return sellerEntryService.addSellerEntry(sellerEntry);
	}

	/**
	 * 查询支付接口
	 * @param sellerEntryQuery 商家补录金额信息
	 * @return
	 */
	@RequestMapping(value="getSellerEntrys", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getSellerEntrys(SellerEntryQuery sellerEntryQuery, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		if(sellerEntryQuery.getOrderId() == null){
			result.setResultCode("1001");
			result.setResultMessage("orderId不能为空");
			return result;
		}
		return sellerEntryService.getSellerEntrys(sellerEntryQuery);
	}

	public void setSellerEntryService(SellerEntryService sellerEntryService) {
		this.sellerEntryService = sellerEntryService;
	}
	
	

}
