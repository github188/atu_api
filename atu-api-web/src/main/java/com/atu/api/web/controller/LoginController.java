package com.atu.api.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atu.api.common.utils.RegisterValidateRules;
import com.atu.api.domain.BusinessUserExt;
import com.atu.api.domain.UserInfo;
import com.atu.api.domain.UserInfoIncr;
import com.atu.api.service.LoginService;
import com.atu.api.service.UserInfoService;
import com.atu.api.service.result.Result;
import com.atu.api.service.utils.RedisUtils;
import com.atu.api.service.utils.RedisValue;
import com.atu.api.web.base.BaseController;

@Controller
@RequestMapping("user")
public class LoginController extends BaseController {
	
	private LoginService loginService;
	private UserInfoService userInfoService;
	
	/**
	 * 买家用户登录
	 * @param mobile 手机号
	 * @param password 密码
	 * @return
	 */
	@RequestMapping(value="buy/login", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result buyerLogin(UserInfo userInfo, UserInfoIncr userInfoIncr, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		
		if(StringUtils.isEmpty(userInfo.getMobile())){
			result.setResultCode("1001");
			result.setResultMessage("mobile不能为空");
			return result;
		}
		if(StringUtils.isEmpty(userInfo.getPassword())){
			result.setResultCode("1002");
			result.setResultMessage("password不能为空");
			return result;
		}
		if(!RegisterValidateRules.isMobile(userInfo.getMobile())){
			result.setResultCode("1003");
			result.setResultMessage("手机格式不正确");
			return result;
		}
		userInfo.setLastLoginIp(getRemoteIp(reuqest));
		
		return loginService.buyerLogin(userInfo, userInfoIncr);
	}
	
	/**
	 * 买家用户注册
	 * @param shopName 商铺名称
	 * @param mobile 手机号
	 * @param password 密码
	 * @param code 验证码
	 * @param openId
	 * @return
	 */
	@RequestMapping(value="buy/reg", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result buyerReg(UserInfo userInfo, UserInfoIncr userInfoIncr, String code, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		if(userInfo.getFromWhere()== null || userInfo.getFromWhere().equals("")){
			result.setResultCode("1001");
			result.setResultMessage("FromWhere不能为空");
			return result;
		}
		if(StringUtils.isEmpty(userInfo.getMobile())){
			result.setResultCode("1001");
			result.setResultMessage("mobile不能为空");
			return result;
		}
		if(StringUtils.isEmpty(userInfo.getPassword())){
			result.setResultCode("1002");
			result.setResultMessage("password不能为空");
			return result;
		}
		if(StringUtils.isEmpty(userInfoIncr.getShopName())){
			result.setResultCode("1005");
			result.setResultMessage("shopName不能为空");
			return result;
		}
		if(StringUtils.isEmpty(code)){
			result.setResultCode("1004");
			result.setResultMessage("code不能为空");
			return result;
		}
		if(!RegisterValidateRules.isMobile(userInfo.getMobile())){
			result.setResultCode("1003");
			result.setResultMessage("手机格式不正确");
			return result;
		}
		
		userInfo.setRegisterIp(getRemoteIp(request));
		userInfo.setLastLoginIp(getRemoteIp(request));
		return loginService.buyerReg(userInfo, userInfoIncr, code);
	}
	
	/**
	 * 买家找回密码
	 * @param k 手机校验码
	 * @param password 密码
	 * @return
	 */
	@RequestMapping(value="retrievePwd", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result retrievePwd(String k, String password, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		
		if(StringUtils.isEmpty(k)){
			result.setResultCode("1006");
			result.setResultMessage("k不能为空");
			return result;
		}
		if(StringUtils.isEmpty(password)){
			result.setResultCode("1002");
			result.setResultMessage("password不能为空");
			return result;
		}
		
		return loginService.retrievePwd(k, password);
	}
	
	/**
	 * 发送验证码
	 * @param mobile 手机号
	 * @param sendType 验证描述
	 * @return
	 */
	@RequestMapping(value="sendCode", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result sendCode(String mobile, Integer sendType, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		
		if(StringUtils.isEmpty(mobile)){
			result.setResultCode("1001");
			result.setResultMessage("mobile不能为空");
			return result;
		}
		if(sendType == null){
			result.setResultCode("1007");
			result.setResultMessage("sendType不能为空");
			return result;
		}
		if(!RegisterValidateRules.isMobile(mobile)){
			result.setResultCode("1003");
			result.setResultMessage("手机格式不正确");
			return result;
		}
		
		return loginService.sendCode(mobile, sendType);
	}
	
	/**
	 * 校验找回密码发送的验证码
	 * @param mobile 手机号
	 * @param code 验证码
	 * @return
	 */
	@RequestMapping(value="validFindPwdCode", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result validFindPwdCode(String mobile, String code, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		
		if(StringUtils.isEmpty(mobile)){
			result.setResultCode("1001");
			result.setResultMessage("mobile不能为空");
			return result;
		}
		if(StringUtils.isEmpty(code)){
			result.setResultCode("1004");
			result.setResultMessage("code不能为空");
			return result;
		}
		if(!RegisterValidateRules.isMobile(mobile)){
			result.setResultCode("1003");
			result.setResultMessage("手机格式不正确");
			return result;
		}
		
		return loginService.validCode(mobile, code, 1);
	}
	
	/**
	 * 校验原手机号时验证码
	 * @param mobile 手机
	 * @param code 验证码
	 * @return
	 */
	@RequestMapping(value="validOldMobileCode", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result validOldMobileCode(String mobile, String code, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		
		if(StringUtils.isEmpty(mobile)){
			result.setResultCode("1001");
			result.setResultMessage("mobile不能为空");
			return result;
		}
		if(StringUtils.isEmpty(code)){
			result.setResultCode("1004");
			result.setResultMessage("code不能为空");
			return result;
		}
		if(!RegisterValidateRules.isMobile(mobile)){
			result.setResultCode("1003");
			result.setResultMessage("手机格式不正确");
			return result;
		}
		
		return loginService.validCode(mobile, code, 2);
	}
	
	/**
	 * 卖家登陆
	 * @param mobile 登录手机号
	 * @param password 登录密码
	 * @return
	 */
	@RequestMapping(value="sell/login", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result sellerLogin(String mobile, String password, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		if(StringUtils.isBlank(mobile)){
			result.setResultCode("1001");
			result.setResultMessage("mobile不能为空");
			return result;
		}
		if(StringUtils.isBlank(password)){
			result.setResultCode("1002");
			result.setResultMessage("password不能为空");
			return result;
		}
		if(!RegisterValidateRules.isMobile(mobile)){
			result.setResultCode("1003");
			result.setResultMessage("手机格式不正确");
			return result;
		}
		return loginService.sellerLogin(mobile, password);
	}
	
	/**
	 * 卖家注册
	 * @param mobile 注册卖家手机号
	 * @param password 注册卖家密码
	 * @param code 注册卖家验证码
	 * @param shopName 注册卖家名称
	 * @return
	 */
	@RequestMapping(value="sell/reg", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result sellerReg(UserInfo userInfo, BusinessUserExt businessUserExt, String code, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		if(StringUtils.isBlank(userInfo.getMobile())){
			result.setResultCode("1001");
			result.setResultMessage("mobile不能为空");
			return result;
		}
		if(StringUtils.isBlank(userInfo.getPassword())){
			result.setResultCode("1002");
			result.setResultMessage("password不能为空");
			return result;
		}
		if(StringUtils.isBlank(code)){
			result.setResultCode("1004");
			result.setResultMessage("code不能为空");
			return result;
		}
		if(StringUtils.isBlank(businessUserExt.getShopName())){
			result.setResultCode("1005");
			result.setResultMessage("shopName不能为空");
			return result;
		}
		if(!RegisterValidateRules.isMobile(userInfo.getMobile())){
			result.setResultCode("1003");
			result.setResultMessage("手机格式不正确");
			return result;
		}
		return loginService.sellerReg(userInfo, businessUserExt, code);
	}
	
	/**
	 * 卖家找回密码
	 * @param k 手机校验码
	 * @param password 密码
	 * @return
	 */
	@RequestMapping(value="findPwd", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result findPwd(String k, String password, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		
		if(StringUtils.isEmpty(k)){
			result.setResultCode("1006");
			result.setResultMessage("k不能为空");
			return result;
		}
		if(StringUtils.isEmpty(password)){
			result.setResultCode("1002");
			result.setResultMessage("password不能为空");
			return result;
		}
		
		return loginService.findPwd(k, password);
	}
	
	/**
	 * 手机号码验证
	 * @param mobile 手机号
	 * @return
	 */
	@RequestMapping(value="checkMobile", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result checkMobile(String mobile, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		if(StringUtils.isBlank(mobile)){
			result.setResultCode("1001");
			result.setResultMessage("mobile不能为空");
			return result;
		}
		if(!RegisterValidateRules.isMobile(mobile)){
			result.setResultCode("1003");
			result.setResultMessage("手机格式不正确");
			return result;
		}
		return this.loginService.checkMobile(mobile);
	}
	
	/**
	 * 验证买家用户名
	 * @param shopName 买家用户名
	 * @return
	 */
	@RequestMapping(value="checkShopName", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result checkShopName(String shopName, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		if(StringUtils.isBlank(shopName)){
			result.setResultCode("1005");
			result.setResultMessage("shopName不能为空");
			return result;
		}
		return this.loginService.checkShopName(shopName);
	}
	
	/**
	 * 根据用户id获取用户信息
	 * @param userId 用户ID
	 * @return
	 */
	@RequestMapping(value="getUserInfoByUserId", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getUserInfoByUserId(Integer userId, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		if(userId == null){
			result.setResultCode("1008");
			result.setResultMessage("userId不能为空");
			return result;
		}
		return userInfoService.getUserInfoByUserId(userId);
	}
	
	/**
	 * 清楚缓存
	 * @param mobile 手机
	 * @return
	 */
	@RequestMapping(value="test", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result test(String mobile, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		
		RedisUtils.del(RedisValue.LoginFrozenName+mobile);
		RedisUtils.del(RedisValue.LoginErroCount+mobile);
		RedisUtils.del(RedisValue.LoginErroTime+mobile);
		
		RedisUtils.del(RedisValue.RegCodeSendTime+mobile);
		RedisUtils.del(RedisValue.RegCodeSendCount+mobile);
		RedisUtils.del(RedisValue.RegCodeFrozenName+mobile);
		RedisUtils.del(RedisValue.RegCodeName+mobile);
		
		RedisUtils.del(RedisValue.ResetValidCodeName+mobile);
		RedisUtils.del(RedisValue.ResetCodeSendTime+mobile);
		RedisUtils.del(RedisValue.ResetCodeSendCount+mobile);
		RedisUtils.del(RedisValue.ResetCodeFrozenName+mobile);
		RedisUtils.del(RedisValue.ResetCodeName+mobile);
		
		RedisUtils.del(RedisValue.OldMCodeName+mobile);
		RedisUtils.del(RedisValue.OldMValidCodeName+mobile);
		RedisUtils.del(RedisValue.OldMCodeSendTime+mobile);
		RedisUtils.del(RedisValue.OldMCodeSendCount+mobile);
		RedisUtils.del(RedisValue.OldMCodeFrozenName+mobile);
		
		RedisUtils.del(RedisValue.NewMCodeName+mobile);
		RedisUtils.del(RedisValue.NewMCodeSendTime+mobile);
		RedisUtils.del(RedisValue.NewMCodeSendCount+mobile);
		RedisUtils.del(RedisValue.NewMCodeFrozenName+mobile);
		
		result.setResultMessage("成功清楚缓存");
		result.setSuccess(true);
		result.setResult(true);
		return result;
	}
	
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

}
