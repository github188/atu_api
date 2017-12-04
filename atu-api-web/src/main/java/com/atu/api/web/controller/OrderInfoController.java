package com.atu.api.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.atu.api.web.base.BaseController;
import com.atu.api.common.utils.JsonUtils;
import com.atu.api.domain.OrderDetail;
import com.atu.api.domain.OrderInfo;
import com.atu.api.domain.SkuList;
import com.atu.api.domain.query.OrderInfoQuery;
import com.atu.api.service.OrderInfoService;
import com.atu.api.service.result.Result;

@Controller
@RequestMapping("/orderInfo")
public class OrderInfoController extends BaseController {
	private static final Logger log = LoggerFactory.getLogger(OrderInfoController.class);
	private OrderInfoService orderInfoService;
	
	/**
	 * 下单
	 * @param orderInfo 订单信息
	 * @param skus
	 * @return
	 */
	@RequestMapping(value="submit", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result submit(OrderInfo orderInfo, String skus, String coupons, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		if(StringUtils.isBlank(skus)){
			result.setResultCode("1001");
			result.setResultMessage("skus不能为空");
			return result;
		}
		if(orderInfo.getFromWhere()== null || orderInfo.getFromWhere().equals("")){
			result.setResultCode("1001");
			result.setResultMessage("FromWhere不能为空");
			return result;
		}

		String listString = "{\"list\":"+skus+"}";
		SkuList skuList = null;
		try{
			skuList = JsonUtils.readValue(listString, SkuList.class);
		}catch (Exception e) {
			result.setResultCode("1001");
			result.setResultMessage("skus格式不正确");
			return result;
		}
		
		List<OrderDetail> orderDetailList = skuList.getList();
		for(int i=0;i<orderDetailList.size();i++){
			OrderDetail od = orderDetailList.get(i);
			if(od.getItemId() == null || od.getNum() == null || od.getSkuId() == null || od.getNum() <= 0){
				result.setResultCode("1001");
				result.setResultMessage("skus格式不正确");
				return result;
			}
		}
		
		if(orderInfo.getOrderType() == null || (orderInfo.getOrderType() !=1 && orderInfo.getOrderType() != 2 &&orderInfo.getOrderType() !=3)){
			result.setResultCode("1001");
			result.setResultMessage("orderType不能为空");
			return result;
		}
		
		if(StringUtils.isBlank(orderInfo.getConsigneeName())){
			result.setResultCode("1001");
			result.setResultMessage("consigneeName不能为空");
			return result;
		}
		if(orderInfo.getConsigneeProvince() == null){
			result.setResultCode("1001");
			result.setResultMessage("consigneeProvince不能为空");
			return result;
		}
		if(orderInfo.getConsigneeCity() == null){
			result.setResultCode("1001");
			result.setResultMessage("consigneeCity不能为空");
			return result;
		}
		if(orderInfo.getConsigneeCounty() == null){
			result.setResultCode("1001");
			result.setResultMessage("consigneeCounty不能为空");
			return result;
		}
		if(StringUtils.isBlank(orderInfo.getConsigneeAddress())){
			result.setResultCode("1001");
			result.setResultMessage("consigneeAddress不能为空");
			return result;
		}
		if(StringUtils.isBlank(orderInfo.getConsigneeMobile())){
			result.setResultCode("1001");
			result.setResultMessage("consigneeMobile不能为空");
			return result;
		}
		/*if(orderInfo.getVenderUserId() == null){
			result.setResultCode("1001");
			result.setResultMessage("venderUserId不能为空");
			return result;
		}*/
		
		orderInfo.setUserId(getUserId(request));
		orderInfo.setIp(getRemoteIp(request));
		
		return orderInfoService.submit(orderInfo, orderDetailList, coupons);
	}
	
	/**
	 * 用户所属订单列表查看（细分）
	 * @return
	 */
	@RequestMapping(value="getOrderInfosByUserId", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getOrderInfosByUserId(OrderInfoQuery orderInfoQuery, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		//所有接口都会传fromWhere参数被orderInfoQuery接收,因为要使用对象(orderInfoQuery)查询数据;fromWhere字段会影响查询结果所以塞null值
		orderInfoQuery.setFromWhere(null);
		orderInfoQuery.setUserId(getUserId(request));
		return orderInfoService.getOrderInfosByOrderInfoQuery(orderInfoQuery);
	}
	
	/**
	 * 根据订单号和用户id获取订单详细信息
	 * @return
	 */
	@RequestMapping(value="getOrderInfoByOrderIdAndUserId", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getOrderInfoByOrderIdAndUserId(Integer orderId, Integer userId, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		return orderInfoService.getOrderInfoByOrderIdAndUserId(orderId,getUserId(request));
	}
	
    /**
	 * 根据订单号和用户id获取订单状态信息
	 * @return
	 */
	@RequestMapping(value="getOrderStatusByOrderIdAndUserId", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getOrderStatusByOrderIdAndUserId(Integer orderId, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		return orderInfoService.getOrderStatusByOrderIdAndUserId(orderId,getUserId(request) );//
	}
	
	
	/**
	 * 商家所属列表信息查看（细分）
	 * userId 用户ID
	 * @return
	 */
	@RequestMapping(value="getOrderInfosByVenderUserId", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getOrderInfosByVenderUserId(OrderInfoQuery orderInfoQuery,HttpServletRequest request,HttpServletResponse response, ModelMap context){
		orderInfoQuery.setUserId(getUserId(request));
		return orderInfoService.getOrderInfosByVenderUserId(orderInfoQuery);
	}

	/**
	 * 根据订单号和商家id获取订单详细信息
	 * @return
	 */
	@RequestMapping(value="getOrderInfoByOrderIdAndVenderUserId", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getOrderInfoByOrderIdAndVenderUserId(Integer orderId, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		return orderInfoService.getOrderInfoByOrderIdAndVenderUserId(orderId,getUserId(request));
	}
	
    /**
	 * 根据订单号和用户id模糊查询订单状态信息(商家)
	 * @return
	 */
	@RequestMapping(value="searchByOrderId", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result searchByOrderId(Integer orderId, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		return orderInfoService.searchByOrderId(orderId, getUserId(request));
	}
	
	/**
	 * 根据订单号修改支付金额(商家)
	 * @return
	 */
	@RequestMapping(value="UpdateOderInfoPriceByOrderId", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result UpdateOderInfoPriceByOrderId(OrderInfoQuery orderInfoQuery, HttpServletRequest request, HttpServletResponse response, ModelMap context){
		orderInfoQuery.setUserId(getUserId(request));
		return orderInfoService.UpdateOderInfoPriceByOrderId(orderInfoQuery);
	}

	/**
	 * 商家确认收款
	 * @param orderId 订单ID
	 * @return
	 */
	@RequestMapping(value="confirmGetPrice", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result confirmGetPrice(Integer orderId, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		if(orderId == null){
			Result result = new Result();
			result.setResultCode("1001");
			result.setResultMessage("orderId不能为空");
			return result;
		}
		return orderInfoService.confirmGetPrice(orderId, getUserId(request));
	}
	
	/**
	 * 商家尾款确认收款
	 * @param orderId 订单号
	 * @return
	 */
	@RequestMapping(value="confirmGetLastPrice", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result confirmGetLastPrice(Integer orderId, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		if(orderId == null){
			Result result = new Result();
			result.setResultCode("1001");
			result.setResultMessage("orderId不能为空");
			return result;
		}
		return orderInfoService.confirmGetLastPrice(orderId, getUserId(request));
	}
	
	/**
	 * 商家发货确认并补录配送信息
	 * @param orderId 订单ID
	 * @return
	 */
	@RequestMapping(value="confirmSendOut", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result confirmSendOut(Integer orderId,String express, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		if(orderId == null){
			Result result = new Result();
			result.setResultCode("1001");
			result.setResultMessage("orderId不能为空");
			return result;
		}
		return orderInfoService.confirmSendOut(orderId, getUserId(request),express);
	}
	
	/**
	 * 用户收货确认
	 * @param orderId 订单ID
	 * @return
	 */
	@RequestMapping(value="confirmGetGoods", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result confirmGetGoods(Integer orderId, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		if(orderId == null){
			Result result = new Result();
			result.setResultCode("1001");
			result.setResultMessage("orderId不能为空");
			return result;
		}
		return orderInfoService.confirmGetGoods(orderId, getUserId(request));
	}
	
	/**
	 * 商家订单完成确认
	 * @param orderId 订单ID
	 * @return
	 */
	@RequestMapping(value="orderSuccess", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result orderSuccess(Integer orderId, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		if(orderId == null){
			Result result = new Result();
			result.setResultCode("1001");
			result.setResultMessage("orderId不能为空");
			return result;
		}
		return orderInfoService.orderSuccess(orderId);
	}
	
	/**
	 * 商家订单锁定
	 * @param orderId 订单ID
	 * @param lockReason 锁定原因
	 * @return
	 */
	@RequestMapping(value="lockOrder", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result lockOrder(Integer orderId, String lockReason, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		if(orderId == null){
			Result result = new Result();
			result.setResultCode("1001");
			result.setResultMessage("orderId不能为空");
			return result;
		}
		System.out.println(lockReason);
		return orderInfoService.lockOrder(orderId, lockReason);
	}
	
	/**
	 * 商家订单解锁
	 * @param orderId 订单ID
	 * @return
	 */
	@RequestMapping(value="unlockOrder", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result unlockOrder(Integer orderId, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		if(orderId == null){
			Result result = new Result();
			result.setResultCode("1001");
			result.setResultMessage("orderId不能为空");
			return result;
		}
		return orderInfoService.unlockOrder(orderId);
	}
	
	/**
	 * 生成结算订单号
	 * @param orderId 订单ID
	 * @return
	 */
	@RequestMapping(value="createTradeNo", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result createTradeNo(Integer orderId, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		log.info("OrderInfoController.createTradeNo()-->");
		if(orderId == null){
			Result result = new Result();
			result.setResultCode("1001");
			result.setResultMessage("orderId不能为空");
			return result;
		}
		log.info("Goto orderInfoService.createTradeNo()-->");
		log.info("Goto orderInfoService.createTradeNo().orderId=="+orderId);
		log.info("Goto orderInfoService.createTradeNo().getUserId=="+getUserId(request));
		return orderInfoService.createTradeNo(orderId, getUserId(request));
	}
	
	/**
	 * 验证订单号 
	 * @param orderId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="checkOrderId", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result checkOrderId(Integer orderId, HttpServletRequest request,HttpServletResponse response){
		if(orderId == null){
			Result result = new Result();
			result.setResultCode("1001");
			result.setResultMessage("orderId不能为空");
			return result;
		}
		return orderInfoService.checkOrderId(orderId);
	}
	
	
	public void setOrderInfoService(OrderInfoService orderInfoService) {
		this.orderInfoService = orderInfoService;
	}
}
