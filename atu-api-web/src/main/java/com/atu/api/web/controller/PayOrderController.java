package com.atu.api.web.controller;

import java.io.BufferedOutputStream;
import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.atu.api.service.OrderInfoService;
import com.atu.api.web.base.BaseController;
import com.atu.api.web.wx.WXPayUtils;

@Controller
@RequestMapping("/payOrder")
public class PayOrderController extends BaseController {
	private static final Logger log = LoggerFactory.getLogger(PayOrderController.class);
	
	private OrderInfoService orderInfoService;
	
	//微信支付回调接口
	@RequestMapping(value="payWX", method={RequestMethod.GET, RequestMethod.POST})
	public void payWX(Integer orderFlag,HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.info("==微信支付回调开始==");
		if(orderFlag == null || "".equals(orderFlag)){
			orderFlag=3;
		}
		boolean flag = false;
		String inputLine;
		String notityXml = "";
		String resXml = "";
		log.info("读取微信请求报文");
		while ((inputLine = request.getReader().readLine()) != null) {
			notityXml += inputLine;
		}
		request.getReader().close();
		
		if(StringUtils.isNotEmpty(notityXml)){
			Map<String, String> map = WXPayUtils.doXMLParse(notityXml);
			if("SUCCESS".equals(map.get("result_code"))){
				log.info("读取报文成功");
				String info = "支付来源：";
				flag = true;
				Integer orderId = Integer.parseInt(map.get("out_trade_no"));
				String tradeNo = map.get("transaction_id");
				BigDecimal price = div(Double.parseDouble(map.get("total_fee")),100.00,2);
				String timeEnd = map.get("time_end");
				if(orderFlag == 1){
					info = info + "Android支付";
				}else if(orderFlag == 2){
					info = info + "IOS支付";
				}else if(orderFlag == 3){
					info = info + "Html5支付";
				}else if(orderFlag == 4){
					info = info + "PC支付";
				}else{
					info = info + "非法来源";
				}
				info = info + "  微信支付单号："+tradeNo+"  订单编号："+orderId+"  订单金额："+price+"  回调时间："+timeEnd;
				log.info(info);
				log.info("执行修改订单状态，添加支付信息");
				orderInfoService.payWX(orderId, tradeNo, price,timeEnd,orderFlag);
				log.info("执行成功");
			}else{
				log.info("读取报文失败");
				flag = false;
			}
		}else{
			flag = false;
		}
		if(flag){
			//支付成功
			resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
			+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
			log.info("通知微信处理完成");
		}else{
			resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
			+ "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
			log.info("通知微信处理失败");
		}
		
		BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
		out.write(resXml.getBytes());
		out.flush();
		out.close();
		log.info("==微信支付回调结束==");
	}
	
	public static BigDecimal div(double d1, double d2, int len) {// 进行除法运算
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP);
	}

	public void setOrderInfoService(OrderInfoService orderInfoService) {
		this.orderInfoService = orderInfoService;
	}

	

}
