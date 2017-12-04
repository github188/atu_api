package com.atu.api.web.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atu.api.common.utils.Sha1Util;
import com.atu.api.common.utils.TenpayUtil;
import com.atu.api.domain.OrderInfo;
import com.atu.api.domain.query.OrderInfoQuery;
import com.atu.api.service.OrderInfoService;
import com.atu.api.service.result.Result;
import com.atu.api.web.base.BaseController;
import com.atu.api.web.wx.RequestHandler;
import com.atu.api.web.wx.WXPayUtils;
import com.atu.api.web.wx.WXValues;

@Controller
@RequestMapping("orderToGetTradeNo")
public class OrderTradeNoController extends BaseController {
	private static final Logger log = LoggerFactory.getLogger(OrderTradeNoController.class);
	
	@Autowired
	private OrderInfoService orderInfoService;
	
	
	@RequestMapping(value="getWxTradeNo", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getWxTradeNo(Integer orderId,Integer orderFlag, HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info("操作订单号："+orderId+" 进行支付");
		Result result = new Result();
		
		OrderInfoQuery orderInfoQuery = new OrderInfoQuery();
		orderInfoQuery.setOrderId(orderId);
		orderInfoQuery.setOrderStatus(0);
		
		List<OrderInfo> orderInfoList = orderInfoService.selectByCondition(orderInfoQuery);
		if(orderInfoList == null || orderInfoList.isEmpty()){
			result.setSuccess(false);
			result.setResultMessage("网络异常,请稍后再试！");
			return result;
		}
		
		OrderInfo orderInfo = orderInfoList.get(0);
		
		BigDecimal oughtPayMoney = orderInfo.getOughtPayMoney();
		String total_fee = String.valueOf((int)(oughtPayMoney.doubleValue()*100));
		String charSet = TenpayUtil.getCharacterEncoding(request, response);
		String appid = WXValues.AppID;
		String mch_id = WXValues.AppMchId;
		String nonce_str = Sha1Util.getNonceStr();
		String body = "阿土商品";
		String spbill_create_ip = "127.0.0.1";
		String notify_url = "http://www.tbny.net/payOrder/payWX?orderFlag="+orderFlag;
		String trade_type = "APP";
		
		RequestHandler reqHandler = new RequestHandler(request, response);
		SortedMap<String, String> signParameters = new TreeMap<String, String>();
		signParameters.put("appid", appid);  						//appid
		signParameters.put("body", body);	 						//商品介绍
		signParameters.put("mch_id", mch_id); 						//商家号 
		signParameters.put("nonce_str", nonce_str); 	  			//随机数
		signParameters.put("notify_url", notify_url); 				//回调地址
		signParameters.put("out_trade_no", orderId.toString()); 	//订单号
		signParameters.put("spbill_create_ip", spbill_create_ip);	//ip地址
		signParameters.put("total_fee", total_fee); 				//总价
		signParameters.put("trade_type", trade_type); 				//支付方式
	
		String sign = WXPayUtils.createSign(charSet, signParameters);
		signParameters.put("sign", sign);
	
		reqHandler.setParameters(signParameters);
		String xml = reqHandler.parseXML();
		String xmlResult = httpPostData("https://api.mch.weixin.qq.com/pay/unifiedorder", xml);
		
		Map<String, String> map = WXPayUtils.doXMLParse(xmlResult);
		log.info("获取支付编号prepay_id=="+map.get("prepay_id"));
		String prepay_id = map.get("prepay_id");
		
		String packageValue = "WXPay";
		if(StringUtils.isNotEmpty(prepay_id)){
			//设置package订单参数
			
			SortedMap<String, String> packageParams = new TreeMap<String, String>();
			packageParams.put("appid", appid);
			packageParams.put("noncestr", Sha1Util.getNonceStr());
			packageParams.put("package", "Sign=" + packageValue);
			packageParams.put("partnerid", WXValues.AppMchId);
			packageParams.put("prepayid", map.get("prepay_id"));
			packageParams.put("timestamp", Sha1Util.getTimeStamp());
			String paySign = WXPayUtils.createSign(charSet, packageParams);
			log.info("paySign===="+paySign);
			packageParams.put("paySign", paySign);
			
			packageParams.remove("package");
			packageParams.put("package1", "Sign=" + packageValue);
			
			result.setSuccess(true);
			result.setResult(packageParams);
		}else{
			result.setSuccess(false);
			result.setResultMessage("微信支付失败,请稍后再试！");
			return result;
		}
		return result;
	}
}

