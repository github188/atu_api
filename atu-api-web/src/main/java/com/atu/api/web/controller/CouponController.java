package com.atu.api.web.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atu.api.web.base.BaseController;
import com.atu.api.service.CouponService;
import com.atu.api.service.result.Result;

/** 优惠券 */

@Controller
@RequestMapping("coupon")
public class CouponController extends BaseController {
	
	private CouponService couponService;
	
	/**
	 * 查询我的优惠券
	 * @return
	 */
	@RequestMapping(value="/init", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result init(HttpServletRequest request,HttpServletResponse response){
		return couponService.selectByUserId(getUserId(request));
	}
	
	/**
	 * 使用优惠券
	 * @param orderPrice 订单价格
	 * @return
	 */
	@RequestMapping(value="/use", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result use(BigDecimal orderPrice, HttpServletRequest request){
		return couponService.selectUseByUserId(getUserId(request), orderPrice);
	}
	public void setCouponService(CouponService couponService) {
		this.couponService = couponService;
	}
}
