package com.atu.api.service;

import com.atu.api.domain.BusinessUserExt;
import com.atu.api.domain.UserInfo;
import com.atu.api.domain.UserInfoIncr;
import com.atu.api.service.result.Result;

public interface LoginService {
	/**
	 * 买家用户登陆
	 * @param mobile 手机号
	 * @param password 密码
	 * @return
	 */
	public Result buyerLogin(UserInfo userInfo, UserInfoIncr userInfoIncr);
	
	/**
	 * 买家用户注册
	 * @param mobile 手机号
	 * @param password 密码
	 * @param code 验证码
	 * @return
	 */
	public Result buyerReg(UserInfo userInfo, UserInfoIncr userInfoIncr, String code);
	
	/**
	 * 买家找回密码
	 * @param k	手机校验码
	 * @param password 新密码
	 * @return
	 */
	public Result retrievePwd(String k, String password);
	
	/**
	 * 重置手机号
	 * @param k	旧手机校验码
	 * @param code	新手机验证码
	 * @param mobile 新手机
	 * @return
	 */
	public Result resetMobile(String k, String code, String mobile, Integer userId);
	
	/**
	 * 发送验证码
	 * @param mobile 手机号
	 * @param sendType 发送类型(1:找回密码时验证码,2:注册时验证码,3:修改原手机验证码,4:修改新手机验证码)
	 * @return
	 */
	public Result sendCode(String mobile, Integer sendType);
	
	/**
	 * 校验找回密码发送的验证码
	 * @param mobile 手机号
	 * @param code 验证码
	 * @return
	 */
	public Result validCode(String mobile, String code, Integer validType);
	
	/**
	 * 卖家用户注册
	 * @param mobile 手机号
	 * @param password 密码
	 * @param code 验证码
	 * @return
	 */
	public Result sellerReg(UserInfo userInfo, BusinessUserExt businessUserExt, String code);
	
	/**
	 * 卖家用户登陆
	 * @param mobile 手机号
	 * @param password 密码
	 * @return
	 */
	public Result sellerLogin(String mobile, String password);
	
	/**
	 * 卖家找回密码
	 * @param k 手机校验码
	 * @param password 密码
	 * @return
	 */
	public Result findPwd(String k, String password);
	
	/**
	 * 判断手机是否可以注册
	 * @param mobile 手机号
	 * @return
	 */
	public Result checkMobile(String mobile);
	
	/**
	 * 判断店铺名称是否可以注册
	 * @param mobile 手机号
	 * @return
	 */
	public Result checkShopName(String shopName);
}
