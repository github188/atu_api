package com.atu.api.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.atu.api.common.utils.DESUtil;
import com.atu.api.common.utils.MD5Util;
import com.atu.api.common.utils.RandomUtil;
import com.atu.api.dao.BusinessUserExtDao;
import com.atu.api.dao.SmsDao;
import com.atu.api.dao.UserInfoDao;
import com.atu.api.dao.UserInfoIncrDao;
import com.atu.api.domain.BusinessUserExt;
import com.atu.api.domain.Sms;
import com.atu.api.domain.UserInfo;
import com.atu.api.domain.UserInfoIncr;
import com.atu.api.domain.query.BusinessUserExtQuery;
import com.atu.api.domain.query.UserInfoQuery;
import com.atu.api.service.LoginService;
import com.atu.api.service.result.Result;
import com.atu.api.service.utils.EcUtils;
import com.atu.api.service.utils.RedisUtils;
import com.atu.api.service.utils.RedisValue;

@Service(value="loginService")
public class LoginServiceImpl implements LoginService {
	private static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);
	
	private SmsDao smsDao;
	private UserInfoDao userInfoDao;
	private BusinessUserExtDao businessUserExtDao;
	private DataSourceTransactionManager transactionManager;
	private UserInfoIncrDao userInfoIncrDao;
	
	/**
	 * 买家用户登陆
	 */
	@Override
	public Result buyerLogin(final UserInfo userInfo,final UserInfoIncr userInfoIncr) {
		Result result = new Result();
		try{
			// 判断手机号是否冻结
			String loginFrozen = RedisUtils.get(RedisValue.LoginFrozenName+userInfo.getMobile());
			if(!StringUtils.isBlank(loginFrozen)){
				result.setResultCode("3001");
				result.setResultMessage("账号被冻结信息。冻结账号20分钟后可以正常登录");
				return result;
			}
			
			// 判断手机号是否存在
			UserInfo userInfos = this.getUserInfoByMobile(userInfo.getMobile());
			if(userInfos.getUserId() == null){
				result.setResultCode("3002");
				result.setResultMessage("您输入的手机号不存在，请核对后重新输入");
				return result;
			}
			
			// 判断密码是否正确
			if(!userInfo.getPassword().equals(userInfos.getPassword())){
				// 添加限制，是否冻结
				Integer loginCount = this.isLimitRedis(userInfo.getMobile(), RedisValue.LoginErroTime, RedisValue.LoginErroCount, 
						RedisValue.LoginFrozenName, RedisValue.LoginFrozenTime, RedisValue.LoginFrozenCount);
				if(loginCount == -1){	// 账号冻结,提示冻结信息
					result.setResultCode("1003");
					result.setResultMessage("账号被冻结信息。冻结账号20分钟后可以正常登录");
				}else{	// 账号未冻结,提示错误信息并返回错误次数
					result.setResultCode("4002");
					result.setResult(loginCount+"");
					result.setResultMessage("您输入的手机号和密码不匹配，请重新输入");
				}
				return result;
			}
			
			// 更新用户登录信息
			userInfo.setUserId(userInfos.getUserId());
			userInfo.setLastLoginTime(new Date());
			userInfoDao.modify(userInfo);
			
			UserInfoIncr userInfoIncrs = userInfoIncrDao.selectByPrimaryKey(userInfos.getUserId());
			
			// 准备返回结果
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId",  userInfos.getUserId());
			map.put("mobile", userInfos.getMobile());
			map.put("shopName", userInfoIncrs.getUserName());
			map.put("headImageUrl", userInfoIncrs.getHeadImageUrl());
			map.put("token", createLoginToken(userInfos));
			
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		
		// 成功登陆，清楚缓存信息
		RedisUtils.del(RedisValue.LoginErroTime+userInfo.getMobile());
		RedisUtils.del(RedisValue.LoginErroCount+userInfo.getMobile());
		RedisUtils.del(RedisValue.LoginFrozenName+userInfo.getMobile());
		return result;
	}

	/**
	 * 买家用户注册
	 */
	@Override
	public Result buyerReg(final UserInfo userInfo, final UserInfoIncr userInfoIncr, final String code) {
		Result result = new Result();
		
		try{
			String reg_code = RedisUtils.get(RedisValue.RegCodeName+userInfo.getMobile());
			
			// 判断验证码是否过期
			if(StringUtils.isEmpty(reg_code)){
				result.setResultCode("4004");
				result.setResultMessage("验证码已过期，请重新获取验证码");
				return result;
			}
			
			// 判断验证码是否正确
			if(!code.equals(reg_code)){
				result.setResultCode("4004");
				result.setResultMessage("验证码错误，请重新输入");
				return result;
			}
			
			// 判断手机号是否注册
			final UserInfo userInfos = this.getUserInfoByMobile(userInfo.getMobile());
			if(userInfos.getUserId() != null){
				result.setResultCode("4003");
				result.setResultMessage("该手机号已被使用，请更换手机号");
				return result;
			}
			
			// 注册用户信息
			userInfos.setMobile(userInfo.getMobile());
			userInfos.setPassword(userInfo.getPassword());
			userInfos.setUserType(3);
			userInfos.setRegisterIp(userInfo.getRegisterIp());
			userInfos.setLastLoginTime(new Date());
			userInfos.setLastLoginIp(userInfo.getLastLoginIp());

			userInfoIncr.setYn(1);
			userInfoIncr.setSex(userInfoIncr.getSex());
			userInfoIncr.setHeadImageUrl(userInfoIncr.getHeadImageUrl());
			userInfoIncr.setUserName(userInfoIncr.getShopName());
			
			new TransactionTemplate(transactionManager).execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus arg0) {
					
					Integer userId = userInfoDao.insert(userInfos);
					userInfoIncr.setUserId(userId);
					Integer UserInfoIncrId = userInfoIncrDao.insert(userInfoIncr);
					
					userInfoIncr.setId(UserInfoIncrId);
				}
				
			});
			
			// 返回信息
			if(userInfoIncr.getId()==null || "".equals(userInfoIncr.getId())){
				result.setSuccess(false);
				result.setResult("注册失败");
				return result;
			}else{
				result.setSuccess(true);
				result.setResult("注册成功");
			}
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		
		// 成功注册，清除缓存信息
		RedisUtils.del(RedisValue.RegCodeName+userInfo.getMobile());
		RedisUtils.del(RedisValue.RegCodeSendCount+userInfo.getMobile());
		RedisUtils.del(RedisValue.RegCodeSendTime+userInfo.getMobile());
		RedisUtils.del(RedisValue.RegCodeFrozenName+userInfo.getMobile());
		return result;
	}

	/**
	 * 买家找回密码
	 */
	@Override
	public Result retrievePwd(String k, String password) {
		Result result = new Result();
		String mobile = RedisUtils.get(RedisValue.ResetValidCodeName+k);
		//mobile = "13522849845";
		try{
			// 校验验证码是否有效
			if(StringUtils.isBlank(mobile)){
				result.setResultCode("4004");
				result.setResultMessage("短信验证码无效，请重新获取");
				return result;
			}
			
			// 获取手机号的用户信息
			UserInfo userInfo = this.getUserInfoByMobile(mobile);
			
			// 修改用户登陆密码
			userInfo.setPassword(password);
			userInfoDao.modify(userInfo);
			
			//根据用户ID获取用户扩展信息
			UserInfoIncr userInfoIncr = userInfoIncrDao.selectByPrimaryKey(userInfo.getUserId());
			
			// 返回信息
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userInfo.getUserId());
			map.put("mobile", userInfo.getMobile());
			map.put("shopName", userInfoIncr.getUserName());
			map.put("headImageUrl", userInfoIncr.getHeadImageUrl());
			map.put("token", createLoginToken(userInfo));
			
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		
		// 找回密码成功，清除缓存信息
		RedisUtils.del(RedisValue.ResetValidCodeName+k);
		RedisUtils.del(RedisValue.ResetCodeSendCount+mobile);
		RedisUtils.del(RedisValue.ResetCodeSendTime+mobile);
		RedisUtils.del(RedisValue.ResetCodeFrozenName+mobile);
		return result;
	}
	
	/**
	 * 重置手机号
	 */
	@Override
	public Result resetMobile(String k, String code, String mobile, Integer userId) {
		Result result = new Result();
		String oldValue = RedisUtils.get(RedisValue.OldMValidCodeName+k);
		String newValue = RedisUtils.get(RedisValue.NewMCodeName+mobile);
		
		try{
			// 判断旧验证码是否过期
			if(StringUtils.isEmpty(oldValue)){
				result.setResultCode("4004");
				result.setResultMessage("验证码不正确");
				return result;
			}
			
			// 判断新验证码是否有效
			if(!code.equals(newValue)){
				result.setResultCode("4004");
				result.setResultMessage("验证码不正确");
				return result;
			}
			
			// 修改用户手机号
			UserInfo userInfo = userInfoDao.selectByUserId(userId);
			userInfo.setMobile(mobile);
			userInfoDao.modify(userInfo);
			
			// 返回信息
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> userMap = new HashMap<String, Object>();
			userMap.put("userId", userInfo.getUserId());
			userMap.put("mobile", userInfo.getMobile());
			map.put("userInfo", userMap);
			map.put("token", createLoginToken(userInfo));
			
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		
		return result;
	}

	/**
	 * 用户发送验证码,发送类型(1:找回密码时验证码,2:注册时验证码,3:修改原手机验证码,4:修改新手机验证码)
	 */
	@Override
	public Result sendCode(String mobile, Integer sendType) {
		Result result = new Result();
		
		if(sendType == 1){
			// 找回密码时验证码
			result = this.sendCode(mobile, sendType, RedisValue.ResetCodeName, RedisValue.ResetCodeTime, 
					RedisValue.ResetCodeSendTime, RedisValue.ResetCodeSendCount, 
					RedisValue.ResetCodeFrozenName, RedisValue.ResetCodeFrozenTime, RedisValue.ResetCodeFrozenCount);
		}else if(sendType == 2){	
			// 注册时验证码                                                                                          "RegCode_"验证码名称              300  验证码有效时间
			result = this.sendCode(mobile, sendType, RedisValue.RegCodeName, RedisValue.RegCodeTime, 
		// RegCodeSendTime_ 记录第一次发送验证码时间        RegCodeSendCount_记录发送验证码次数
					RedisValue.RegCodeSendTime, RedisValue.RegCodeSendCount, 
					// RegCodeFrozen_  冻结用户            86400 冻结时间                                                    10 发送最大次数
					RedisValue.RegCodeFrozenName, RedisValue.RegCodeFrozenTime, RedisValue.RegCodeFrozenCount);
		}else if(sendType == 3){	
			// 修改原手机验证码
			result = this.sendCode(mobile, sendType, RedisValue.OldMCodeName, RedisValue.OldMCodeTime, 
					RedisValue.OldMCodeSendTime, RedisValue.OldMCodeSendCount, 
					RedisValue.OldMCodeFrozenName, RedisValue.OldMCodeFrozenTime, RedisValue.OldMCodeFrozenCount);
		}else if(sendType == 4){	
			// 修改新手机验证码
			result = this.sendCode(mobile, sendType, RedisValue.NewMCodeName, RedisValue.NewMCodeTime, 
					RedisValue.NewMCodeSendTime, RedisValue.NewMCodeSendCount, 
					RedisValue.NewMCodeFrozenName, RedisValue.NewMCodeFrozenTime, RedisValue.NewMCodeFrozenCount);
		}else{
			result.setResultCode("4001");
			result.setResultMessage("错误的信息");
		}
		
		return result;
	}
	
	/**
	 * 校验验证码
	 */
	@Override
	public Result validCode(String mobile, String code, Integer validType){
		Result result = new Result();
		
		// 校验找回密码发送的验证码
		if(validType == 1){
			result = validCode(mobile, code, RedisValue.ResetCodeName, 
					RedisValue.ResetValidCodeName, RedisValue.ResetValidCodeTime);
		}
		
		// 校验修改原手机号验证码
		if(validType == 2){
			result = validCode(mobile, code, RedisValue.OldMCodeName, 
					RedisValue.OldMValidCodeName, RedisValue.OldMValidCodeTime);
		}
		
		return result;
	}
	
	/** 卖家登录 */
	@Override
	public Result sellerLogin(String mobile, String password) {
		Result result = new Result();
		try{
			//查询用户信息
			UserInfoQuery userInfoQuery = new UserInfoQuery();
			userInfoQuery.setMobile(mobile);
			List<UserInfo> list = userInfoDao.selectByCondition(userInfoQuery);
			//判断是否查询到数据
			if(list == null || list.size() == 0){
				result.setResultCode("4001");
				result.setResultMessage("用户不存在");
				return result;
			}
			
			UserInfo userInfo = list.get(0);
			if(!userInfo.getPassword().equals(password)){
				result.setResultCode("4002");
				result.setResultMessage("密码不正确");
				return result;
			}
			if(userInfo.getUserType() != 2){
				result.setResultCode("4006");
				result.setResultMessage("非商家用户不能登陆");
				return result;
			}
			//根据用户ID查询卖家信息
			userInfo.setBusinessUserExt(this.businessUserExtDao.selectByUserId(userInfo.getUserId()));
			Map<String, Object> map = new HashMap<String, Object>();
			
			//添加businessType（卖家属性：1普通卖家；2认证商家；3vip商家）属性
//            if( null != userInfo.getBusinessUserExt() ){
//                userMap.put("businessType", userInfo.getBusinessUserExt().getBusinessType());
//            }
			//把userMap集合和token添加到map集合中
			map.put("userId", userInfo.getUserId());
			map.put("status", userInfo.getBusinessUserExt().getStatus());
			map.put("usermobile", userInfo.getMobile());
			map.put("usershopName", userInfo.getBusinessUserExt().getShopName());
			map.put("businessType", userInfo.getBusinessUserExt().getBusinessType());
			map.put("usertoken", createLoginToken(userInfo));
			//把map中数据用result（object）接收
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	/** 
	 * 卖家注册
	 */
	@Override
	public Result sellerReg(UserInfo userInfo, BusinessUserExt businessUserExt, String code) {
		Result result = new Result();
		
		try{
			//查询数据库，判断该用户是否已经注册过
			UserInfoQuery userInfoQuery = new UserInfoQuery();
			userInfoQuery.setMobile(userInfo.getMobile());
			List<UserInfo> list = userInfoDao.selectByCondition(userInfoQuery);
			if(list != null && list.size() > 0){
				result.setResultCode("4003");
				result.setResultMessage("手机号已存在");
				return result;
			}
			//查询数据库，判断该用户的商家名称是否已经存在
			BusinessUserExtQuery businessUserExtQuery = new BusinessUserExtQuery();
			businessUserExtQuery.setShopName(businessUserExt.getShopName());
			List<BusinessUserExt> list2 = businessUserExtDao.selectByCondition(businessUserExtQuery);
			if(list2 != null && list2.size() > 0){
				result.setResultCode("4007");
				result.setResultMessage("商家名称已存在");
				return result;
			}
			
			// 注册用户信息
			final UserInfo userInfos = new UserInfo();
			userInfos.setMobile(userInfo.getMobile());
			userInfos.setUserType(2);
			userInfos.setYn(1);
			userInfos.setCreated(new Date());
			userInfos.setLastLoginIp(userInfo.getLastLoginIp());
			userInfos.setLastLoginTime(new Date());
			userInfos.setRegisterIp(userInfo.getRegisterIp());
			userInfos.setRegisterTime(new Date());
			userInfos.setPassword(userInfo.getPassword());
			userInfos.setFromWhere(userInfo.getFromWhere());
			
			// 注册卖家信息
			final BusinessUserExt businessUserExts = new BusinessUserExt();
			businessUserExts.setCreated(new Date());
			businessUserExts.setShopName(businessUserExt.getShopName());
			businessUserExts.setStatus(1);
			businessUserExts.setMobile(businessUserExt.getMobile());
			
			new TransactionTemplate(transactionManager).execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus arg0) {
					Integer userId = userInfoDao.insert(userInfos);
					
					businessUserExts.setUserId(userId);
					Integer id = businessUserExtDao.insert(businessUserExts);
					
					businessUserExts.setId(id);
					userInfos.setUserId(userId);
					userInfos.setBusinessUserExt(businessUserExts);
				}
			});
			
			if(userInfos.getUserId() == null || "".equals(userInfos.getUserId())){
				result.setSuccess(false);
				result.setResult("注册失败");
				return result;
			}else{
				result.setSuccess(true);
				result.setResult("注册成功");
			}
			
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	/**
	 * 卖家找回密码
	 */
	public Result findPwd(String k, String password){
		Result result = new Result();
		String mobile = RedisUtils.get(RedisValue.ResetValidCodeName+k);
		try{
			// 校验验证码是否有效
			if(StringUtils.isBlank(mobile)){
				result.setResultCode("4004");
				result.setResultMessage("短信验证码无效，请重新获取");
				return result;
			}
			// 获取手机号的用户信息
			UserInfo userInfo = this.getUserInfoByMobile(mobile);
			// 修改用户登陆密码
			userInfo.setPassword(MD5Util.md5Hex(password));
			userInfoDao.modify(userInfo);
			//根据用户ID查询收货人地址
			BusinessUserExt businessUserExt = businessUserExtDao.selectByUserId(userInfo.getUserId());
			// 返回信息
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userInfo.getUserId());
			map.put("status", businessUserExt.getStatus());
			map.put("usermobile", userInfo.getMobile());
			map.put("usershopName", businessUserExt.getShopName());
			map.put("businessType", businessUserExt.getBusinessType());
			map.put("token", createLoginToken(userInfo));
						
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		RedisUtils.del(RedisValue.ResetValidCodeName+k);
		RedisUtils.del(RedisValue.ResetCodeSendCount+mobile);
		RedisUtils.del(RedisValue.ResetCodeSendTime+mobile);
		RedisUtils.del(RedisValue.ResetCodeFrozenName+mobile);
		return result;
	}
	
	/**
	 * 验证手机号是否已注册
	 */
	@Override
	public Result checkMobile(String mobile) {
		Result result = new Result();
		try{
			UserInfoQuery userInfoQuery = new UserInfoQuery();
			userInfoQuery.setMobile(mobile);
			List<UserInfo> list = userInfoDao.selectByCondition(userInfoQuery);
			if(list != null && list.size() > 0){
				result.setResultCode("4003");
				result.setResultMessage("手机号已存在");
				return result;
			}
			result.setResult(true);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	/**
	 * 发送找回密码验证码
	 */
	@Override
	public Result checkShopName(String shopName) {
		Result result = new Result();
		try{
			BusinessUserExtQuery businessUserExtQuery = new BusinessUserExtQuery();
			businessUserExtQuery.setShopName(shopName);
			List<BusinessUserExt> list = businessUserExtDao.selectByCondition(businessUserExtQuery);
			if(list != null && list.size() > 0){
				result.setResultCode("4007");
				result.setResultMessage("商家名称已存在");
				return result;
			}
			result.setResult(true);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	/**
	 * 用户发送验证码
	 * // 注册时验证码                                                                                          "RegCode_"验证码名称              300  验证码有效时间
	 *		result = this.sendCode(mobile, sendType, RedisValue.RegCodeName, RedisValue.RegCodeTime, 
	 *	  RegCodeSendTime_ 记录第一次发送验证码时间        RegCodeSendCount_记录发送验证码次数
	 *				RedisValue.RegCodeSendTime, RedisValue.RegCodeSendCount, 
	 *				 RegCodeFrozen_  冻结用户            86400 冻结时间                                                    10 发送最大次数
	 *				RedisValue.RegCodeFrozenName, RedisValue.RegCodeFrozenTime, RedisValue.RegCodeFrozenCount);
	 * 
	 */
	private Result sendCode(String mobile, Integer sendType, String codeName, Integer codeTime, String erroTimeName, 
			String erroCountName, String frozenName, Integer frozenTime, Integer frozenCount){
		Result result = new Result();
		
		try{
			// 判断手机号是否存在
			UserInfo userInfo = this.getUserInfoByMobile(mobile);
			//买家发送验证码,发送类型(1:找回密码时验证码,2:注册时验证码,3:修改原手机验证码,4:修改新手机验证码)
			if(sendType == 2 || sendType == 4){
				if(userInfo.getUserId() != null){
					result.setResultCode("4003");
					result.setResultMessage("该手机号已注册，请更换手机号");
					return result;
				}
			}else{
				if(userInfo.getUserId() == null){
					result.setResultCode("4003");
					result.setResultMessage("该手机号未注册，请更换手机号");
					return result;
				}
			}
			
			
			// 判断手机号是否冻结
			String resetCodeFrozen = RedisUtils.get(frozenName+mobile);
			if(!StringUtils.isBlank(resetCodeFrozen)){
				result.setResultCode("1004");
				result.setResultMessage("您申请获取短信验证码的次数过多，请24小时后再试");
				return result;
			}
			
			// 发送验证码,添加短信记录
			int code = RandomUtil.randomRangeInt(100000, 999999);
			Sms sms = new Sms();
			if(sendType == 2){
				sms.setContent("注册验证码为："+code+",请在页面中提交验证码完成验证");
			}else{
				sms.setContent("您的短信验证码是："+code+",如非本人操作，请及时修改密码以防被盗（请勿泄露）");
			}
			sms.setMobile(mobile);
			sms.setStatus(0);
			smsDao.insert(sms);
			RedisUtils.set(codeName+mobile, codeTime, code+"");
			
			// 添加限制，是否冻结
			this.isLimitRedis(mobile, erroTimeName, erroCountName, 
					frozenName, frozenTime, frozenCount);
			
			result.setResult(true);
			EcUtils.setSuccessResult(result);
			result.setResultMessage("验证码已发送，请查收短信");
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
			result.setResultMessage("验证码发送失败，请重新获取");
		}
		return result;
	}
	
	/**
	 * 校验验证码
	 */
	private Result validCode(String mobile, String code, String codeName, String validName, Integer validTime){
		Result result = new Result();
		
		try{
			String value = RedisUtils.get(codeName+mobile);
			//value="745185";
			// 判断验证码是否有效
			if(!code.equals(value)){
				result.setResultCode("4004");
				result.setResultMessage("验证码不正确");
				return result;
			}
			
			// 添加校验成功后的验证码到缓存中
			RedisUtils.del(codeName+mobile);
			String k = MD5Util.md5Hex(""+System.currentTimeMillis()+RandomUtil.randomRangeInt(100000, 999999));
			RedisUtils.set(validName+k, validTime, mobile);
			
			// 返回信息
			result.setResult(k);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
			result.setResultMessage("效验失败，请重新输入信息");
		}
		
		return result;
	}
	
	/**
	 * 添加redis进行操作限制
	 */
	private Integer isLimitRedis(String mobile, String erroTimeName, String erroCountName, 
			String frozenName, Integer frozenTime, Integer frozenCount){
		// 记录限制次数
		Integer count = 0;
		String nowDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String erroTime = RedisUtils.get(erroTimeName+mobile);
		String erroCount = RedisUtils.get(erroCountName+mobile);
		// 判断是否第一次记录
		if(StringUtils.isBlank(erroTime)){			// 首次记录
			count = 1;
			erroTime = nowDate;
			RedisUtils.set(erroTimeName+mobile, frozenTime, nowDate);
		}else{										// 不是首次记录，记录限制次数
			count = Integer.parseInt(erroCount)+1;
		}
		RedisUtils.set(erroCountName+mobile, frozenTime, count.toString());
		// 判断限制次数,是否冻结
		if(count == frozenCount){					// 冻结账户
			RedisUtils.del(erroTimeName+mobile);
			RedisUtils.del(erroCountName+mobile);
			RedisUtils.set(frozenName+mobile, frozenTime, erroTime);
			return -1;
		}else{										// 不冻结账户
			return count;
		}
	}
	
	/**
	 * 通过手机号获取用户信息
	 */
	private UserInfo getUserInfoByMobile(String mobile){
		UserInfoQuery userInfoQuery = new UserInfoQuery();
		userInfoQuery.setMobile(mobile);
		List<UserInfo> list = userInfoDao.selectByCondition(userInfoQuery);
		UserInfo userInfo = new UserInfo();
		// 判断手机号是否存在
		if(list != null && list.size() > 0){
			userInfo = list.get(0);
		}
		return userInfo;
	}
	
	/**
	 * 通过商铺名称获取用户补充信息
	 */
//	private BusinessUserExt getBusinessUserByShopName(String shopName){
//		BusinessUserExtQuery businessUserExtQuery = new BusinessUserExtQuery();
//		businessUserExtQuery.setShopName(shopName);
//		List<BusinessUserExt> list = businessUserExtDao.selectByCondition(businessUserExtQuery);
//		BusinessUserExt businessUserExt = new BusinessUserExt();
//		// 判断手机号是否存在
//		if(list != null && list.size() > 0){
//			businessUserExt = list.get(0);
//		}
//		return businessUserExt;
//	}
	
	/**
	 * 创建用户登录令牌
	 */
	private String createLoginToken(UserInfo userInfo) throws Exception{
		String token = userInfo.getUserId() + "-" + userInfo.getMobile() + "-" + userInfo.getUserType();
		return DESUtil.encrypt(token, "gs2y601z");
	}

	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	public void setBusinessUserExtDao(BusinessUserExtDao businessUserExtDao) {
		this.businessUserExtDao = businessUserExtDao;
	}

	public void setSmsDao(SmsDao smsDao) {
		this.smsDao = smsDao;
	}

	public void setTransactionManager(
			DataSourceTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public void setUserInfoIncrDao(UserInfoIncrDao userInfoIncrDao) {
		this.userInfoIncrDao = userInfoIncrDao;
	}

}
