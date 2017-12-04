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
import com.atu.api.common.utils.RegisterValidateRules;
import com.atu.api.domain.UserInfo;
import com.atu.api.domain.UserInfoIncr;
import com.atu.api.domain.WxUser;
import com.atu.api.service.LoginService;
import com.atu.api.service.WxUserService;
import com.atu.api.service.result.Result;

@Controller
@RequestMapping("wxUser")
public class WxUserController extends BaseController {
	
	private WxUserService wxUserService;
	
	private LoginService loginService;
	
	
	/**
	 * 微信用户信息
	 * @param wxUser 微信用户信息
	 * @return
	 */
	@RequestMapping(value="login", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result login(WxUser wxUser, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		return wxUserService.login(wxUser);
	}
	
	@RequestMapping(value="bindMobile", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result bindMobile(UserInfo userInfo, UserInfoIncr userInfoIncr, String code, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result = new Result();
//		if(StringUtils.isEmpty(openId)){
//			result.setResultCode("1001");
//			result.setResultMessage("openId不能为空");
//			return result;
//		}
		if(StringUtils.isEmpty(userInfo.getMobile())){
			result.setResultCode("1001");
			result.setResultMessage("mobile不能为空");
			return result;
		}
		if(StringUtils.isEmpty(userInfo.getPassword())){
			result.setResultCode("1001");
			result.setResultMessage("password不能为空");
			return result;
		}
		if(StringUtils.isEmpty(code)){
			result.setResultCode("1001");
			result.setResultMessage("code不能为空");
			return result;
		}
		if(!RegisterValidateRules.isMobile(userInfo.getMobile())){
			result.setResultCode("1002");
			result.setResultMessage("手机格式不正确");
			return result;
		}
		return loginService.buyerReg(userInfo, userInfoIncr, code);
	}

	public void setWxUserService(WxUserService wxUserService) {
		this.wxUserService = wxUserService;
	}

}
