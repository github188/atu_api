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
import com.atu.api.service.LoginService;
import com.atu.api.service.UserInfoService;
import com.atu.api.service.result.Result;

@Controller
@RequestMapping("/userInfo")
public class UserInfoController extends BaseController {
	
	private UserInfoService userInfoService;
	private LoginService loginService;
	
	/**
	 * 重置手机号
	 * @param k 旧手机校验码
	 * @param code 新手机验证码
	 * @param mobile 新手机号
	 * @return
	 */
	@RequestMapping(value="resetMobile", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result resetMobile(String k, String code, String mobile, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		
		if(StringUtils.isEmpty(k)){
			result.setResultCode("1001");
			result.setResultMessage("k不能为空");
			return result;
		}
		if(StringUtils.isEmpty(code)){
			result.setResultCode("1001");
			result.setResultMessage("code不能为空");
			return result;
		}
		if(StringUtils.isEmpty(mobile)){
			result.setResultCode("1001");
			result.setResultMessage("mobile不能为空");
			return result;
		}
		if(!RegisterValidateRules.isMobile(mobile)){
			result.setResultCode("1002");
			result.setResultMessage("手机格式不正确");
			return result;
		}
		
		return loginService.resetMobile(k, code, mobile, getUserId(reuqest));
	}
	
	/**
	 * 获取用户信息
	 * @return
	 */
	@RequestMapping(value="getUserInfo", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getUserInfo(HttpServletRequest request,HttpServletResponse response, ModelMap context){
		return userInfoService.getUserInfoByUserId(getUserId(request));
	}
	
	/**
	 * 获取用户绑定银行卡信息
	 * @return
	 */
	@RequestMapping(value="getPaymentBindbankcard", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getPaymentBindbankcard(HttpServletRequest request,HttpServletResponse response, ModelMap context){
		return userInfoService.getPaymentBindbankcard(getUserId(request));
	}
	
	/**
	 * 根据userId获取用户信息修改登陆密码
	 * @param oldPwd 旧密码
	 * @param newPwd 新密码
	 * @return
	 */
	@RequestMapping(value="updatePwd", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result updatePwd(String oldPwd, String newPwd, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		if(StringUtils.isBlank(oldPwd)){
			result.setResultCode("1001");
			result.setResultMessage("oldPwd不能为空");
			return result;
		}
		if(StringUtils.isBlank(newPwd)){
			result.setResultCode("1001");
			result.setResultMessage("newPwd不能为空");
			return result;
		}
		return userInfoService.updatePwd(getUserId(request), oldPwd, newPwd);
	}

	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

}
