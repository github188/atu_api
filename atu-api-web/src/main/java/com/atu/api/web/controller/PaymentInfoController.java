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
import com.atu.api.domain.PaymentBindbankcard;
import com.atu.api.domain.PaymentInfo;
import com.atu.api.domain.query.PaymentInfoQuery;
import com.atu.api.service.PaymentInfoService;
import com.atu.api.service.result.Result;


@Controller
@RequestMapping("/paymentInfo")
public class PaymentInfoController extends BaseController {

	private PaymentInfoService paymentInfoService;

	/**
	 * 订单支付接口
	 * @param paymentInfo 支付记录信息
	 * @return
	 */
	@RequestMapping(value = "addPaymentInfo", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody
	Result addPaymentInfo(PaymentInfo paymentInfo, HttpServletRequest request,
			HttpServletResponse response, ModelMap context) {
		Result result = new Result();
		if (paymentInfo.getOrderId() == null) {
			result.setResultCode("1001");
			result.setResultMessage("orderId不能为空");
			return result;
		}
		if (paymentInfo.getOrderPayType() == null) {
			result.setResultCode("1001");
			result.setResultMessage("orderPayType不能为空");
			return result;
		}
		if (paymentInfo.getPaymentInfoType() == null) {
			result.setResultCode("1001");
			result.setResultMessage("paymentInfoType不能为空");
			return result;
		}
		if (paymentInfo.getPaymentMoney() == null) {
			result.setResultCode("1001");
			result.setResultMessage("paymentMoney不能为空");
			return result;
		}
		if (StringUtils.isBlank(paymentInfo.getPaymentNumber())) {
			result.setResultCode("1001");
			result.setResultMessage("paymentNumber不能为空");
			return result;
		}

		if (paymentInfo.getPaymentMode() == null) {
			paymentInfo.setPaymentMode(1);// 连连支付
		}

		return paymentInfoService.addPaymentInfo(paymentInfo);
	}
	
	/**
	 * 转账账户信息
	 * @return
	 */
	@RequestMapping(value = "accountInfo", method = { RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody Result accountInfo(HttpServletRequest request,HttpServletResponse response, ModelMap context) {
		return paymentInfoService.accountInfo();
	}
	

	/**
	 * 查询支付接口
	 * @param paymentInfoQuery 支付记录
	 * @return
	 */
	@RequestMapping(value = "getPaymentInfos", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody
	Result getPaymentInfos(PaymentInfoQuery paymentInfoQuery,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap context) {
		Result result = new Result();
		if (paymentInfoQuery.getOrderId() == null) {
			result.setResultCode("1001");
			result.setResultMessage("orderId不能为空");
			return result;
		}
		return paymentInfoService.getPaymentInfos(paymentInfoQuery);
	}

	/**
	 * 发送一键支付短信验证码
	 * @param paymentBindbankcard 绑定银行卡
	 * @param paymentInfoQuery 支付记录
	 * @return
	 */
	@RequestMapping(value = "sendCode", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody
	Result sendCode(PaymentBindbankcard paymentBindbankcard,
			PaymentInfoQuery paymentInfoQuery, HttpServletRequest request,
			HttpServletResponse response, ModelMap context) {
		Result result = new Result();
		if (paymentBindbankcard.getMer_cust_id() == null) {
			result.setResultCode("1001");
			result.setResultMessage("天宝用户唯一标识不能为空");
			return result;
		}
		if (StringUtils.isEmpty(paymentBindbankcard.getBank_ac_last4())) {
			result.setResultCode("1001");
			result.setResultMessage("银行卡后四位不能为空");
			return result;
		}
		if (paymentInfoQuery.getOrderId() == null) {
			result.setResultCode("1001");
			result.setResultMessage("orderId不能为空");
			return result;
		}

		return paymentInfoService.sendCode(paymentBindbankcard,
				paymentInfoQuery);
	}

	public void setPaymentInfoService(PaymentInfoService paymentInfoService) {
		this.paymentInfoService = paymentInfoService;

	}

	/**
	 * 协议支付确认支付
	 * @param paymentBindbankcard 绑定银行卡
	 * @param paymentInfoQuery 支付记录
	 * @param verify_code 验证加密
	 * @return
	 */
	@RequestMapping(value = "submit", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody
	Result submit(PaymentBindbankcard paymentBindbankcard,
			PaymentInfoQuery paymentInfoQuery, Integer verify_code,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap context) {
		Result result = new Result();
		if (paymentBindbankcard.getMer_cust_id() == null) {
			result.setResultCode("1001");
			result.setResultMessage("天宝用户唯一标识不能为空");
			return result;
		}
		if (StringUtils.isEmpty(paymentBindbankcard.getBank_ac_last4())) {
			result.setResultCode("1001");
			result.setResultMessage("银行卡后4位不能为空");
			return result;
		}
		if (paymentInfoQuery.getOrderId() == null) {
			result.setResultCode("1001");
			result.setResultMessage("orderId不能为空");
			return result;
		}
		if (verify_code == null) {
			result.setResultCode("1001");
			result.setResultMessage("短信验证码不能为空");
			return result;
		}

		return paymentInfoService.submit(paymentBindbankcard, paymentInfoQuery,
				verify_code);
	}



}
