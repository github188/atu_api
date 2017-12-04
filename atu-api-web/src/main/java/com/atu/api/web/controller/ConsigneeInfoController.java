package com.atu.api.web.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atu.api.web.base.BaseController;
import com.atu.api.domain.ConsigneeInfo;
import com.atu.api.service.ConsigneeInfoService;
import com.atu.api.service.result.Result;

/** 收货人信息  */

@Controller
@RequestMapping("/consigneeInfo")
public class ConsigneeInfoController extends BaseController {

	private ConsigneeInfoService consigneeInfoService;
	
	/**
	 * 用户收货人信息列表查询
	 * @return
	 */
	@RequestMapping(value="getConsigneeInfosByUserId", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getConsigneeInfosByUserId(HttpServletRequest request,HttpServletResponse response, ModelMap context){
		return consigneeInfoService.getConsigneeInfosByUserId(getUserId(request));
	}
	
	/**
	 * 新增收货人信息
	 * @param consigneeInfo 收货人信息
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value="addConsigneeInfo", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result addConsigneeInfo(ConsigneeInfo consigneeInfo, HttpServletRequest request,HttpServletResponse response, ModelMap context) throws UnsupportedEncodingException{
		Result result = new Result();
		if(StringUtils.isBlank(consigneeInfo.getConsigneeName())){
			result.setResultCode("1001");
			result.setResultMessage("consigneeName不能为空");
			return result;
		}
		if(consigneeInfo.getConsigneeProvince() == null){
			result.setResultCode("1001");
			result.setResultMessage("consigneeProvince不能为空");
			return result;
		}
		if(consigneeInfo.getConsigneeCity() == null){
			result.setResultCode("1001");
			result.setResultMessage("consigneeCity不能为空");
			return result;
		}
		if(consigneeInfo.getConsigneeCounty() == null){
			result.setResultCode("1001");
			result.setResultMessage("consigneeCounty不能为空");
			return result;
		}
		if(StringUtils.isBlank(consigneeInfo.getConsigneeAddress())){
			result.setResultCode("1001");
			result.setResultMessage("consigneeAddress不能为空");
			return result;
		}
		if(StringUtils.isBlank(consigneeInfo.getConsigneeMobile())){
			result.setResultCode("1001");
			result.setResultMessage("consigneeMobile不能为空");
			return result;
		}
		consigneeInfo.setUserId(getUserId(request));
		return consigneeInfoService.addConsigneeInfo(consigneeInfo);
	}
	
	/**
	 * 修改收货人信息
	 * @param consigneeInfo 收货人信息
	 * @return
	 */
	@RequestMapping(value="updateConsigneeInfo", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result updateConsigneeInfo(ConsigneeInfo consigneeInfo, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		if(consigneeInfo.getConsigneeId() == null){
			result.setResultCode("1001");
			result.setResultMessage("consigneeId不能为空");
			return result;
		}
		return consigneeInfoService.updateConsigneeInfo(consigneeInfo);
	}
	
	/**
	 * 删除收货人信息
	 * @param consigneeId 收货人信息id(自增)
	 * @return
	 */
	@RequestMapping(value="delConsigneeInfo", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result delConsigneeInfo(Integer consigneeId, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		if(consigneeId == null){
			result.setResultCode("1001");
			result.setResultMessage("consigneeId不能为空");
			return result;
		}
		return consigneeInfoService.delConsigneeInfo(consigneeId);
	}
	
	/**
	 * 选择默认收货地址
	 * @param consigneeInfo 收货人信息
	 * @return
	 */
	@RequestMapping(value="choiceConsigneeInfo", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result choiceConsigneeInfo(ConsigneeInfo consigneeInfo, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		if(consigneeInfo.getConsigneeId() == null){
			result.setResultCode("1001");
			result.setResultMessage("consigneeId不能为空");
			return result;
		}
		consigneeInfo.setUserId(getUserId(request));
		return consigneeInfoService.choiceConsigneeInfo(consigneeInfo);
	}
	
	/**
	 * 根据收货人Id获取收货人信息
	 * @param consigneeId 收货人信息id(自增)
	 * @return
	 */
	@RequestMapping(value="getByConsigneeId", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getByCnsigneeId(Integer consigneeId, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		if(consigneeId== null){
			result.setResultCode("1001");
			result.setResultMessage("consigneeId不能为空");
			return result;
		}
		return consigneeInfoService.getByConsigneeId(consigneeId);
	}
	
	/**
	 * 验证收货人信息并且更新收货人信息
	 * @return
	 */
	@RequestMapping(value="getConsigneeInfosByDefault", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getConsigneeInfosByDefault(HttpServletRequest request,HttpServletResponse response, ModelMap context){
		return consigneeInfoService.getConsigneeInfosByDefault(getUserId(request));
	}

	public void setConsigneeInfoService(ConsigneeInfoService consigneeInfoService) {
		this.consigneeInfoService = consigneeInfoService;
	}

}
