package com.atu.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.atu.api.common.utils.RegisterValidateRules;
import com.atu.api.dao.BusinessUserExtDao;
import com.atu.api.dao.PaymentBindbankcardDao;
import com.atu.api.dao.UserInfoDao;
import com.atu.api.domain.BusinessUserExt;
import com.atu.api.domain.PaymentBindbankcard;
import com.atu.api.domain.UserInfo;
import com.atu.api.service.UserInfoService;
import com.atu.api.service.result.Result;
import com.atu.api.service.utils.BankUtils;
import com.atu.api.service.utils.EcUtils;

@Service(value="userInfoService")
public class UserInfoServiceImpl implements UserInfoService {
	private static final Logger log = LoggerFactory.getLogger(UserInfoServiceImpl.class);
	
	private UserInfoDao userInfoDao;
	
	private BusinessUserExtDao businessUserExtDao;
	
	private PaymentBindbankcardDao paymentBindbankcardDao;

	/**
	 * 买家用户修改密码
	 */
	@Override
	public Result updatePwd(Integer userId, String oldPwd, String newPwd) {
		Result result = new Result();
		
		try{
			//根据userId获取用户信息
			UserInfo userInfo = userInfoDao.selectByUserId(userId);
			
			if(userInfo == null){
				result.setResultCode("4001");
				result.setResultMessage("用户不存在");
				return result;
			}
			
			if(!userInfo.getPassword().equals(oldPwd)){
				result.setResultCode("4005");
				result.setResultMessage("旧密码不匹配");
				return result;
			}
			
			if(RegisterValidateRules.isInvalidPassword(newPwd)){
				result.setResultCode("1001");
				result.setResultMessage("新密码格式不正确");
				return result;
			}
			
			//修改登陆密码
			userInfo.setPassword(newPwd);
			userInfoDao.modify(userInfo);
			
			result.setResult(true);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	@Override
	public Result getUserInfoByUserId(Integer userId) {
		Result result = new Result();
		
		try{
			//获取用户信息
			UserInfo userInfo = userInfoDao.selectByUserId(userId);
			if(userInfo == null){
				result.setResultCode("4001");
				result.setResultMessage("用户不存在");
				return result;
			}
			userInfo.setPassword(null);
			
			//如果是卖家
			if(userInfo.getUserType()==2 ){
				//查询用户扩展表
				BusinessUserExt businessUserExt = this.businessUserExtDao.selectByUserId(userId);
				if(businessUserExt != null){
					userInfo.setBusinessUserExt(businessUserExt);
				}
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userInfo", userInfo);
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	@Override
	public Result getPaymentBindbankcard(Integer userId) {
		Result result = new Result();
		
		try{
			//获取用户绑定银行卡信息
			PaymentBindbankcard paymentBindbankcard = new PaymentBindbankcard();
			paymentBindbankcard.setMer_cust_id(userId);
			List<PaymentBindbankcard> list = paymentBindbankcardDao.selectByCondition(paymentBindbankcard);
			if(list == null){
				result.setResultCode("9001");
				result.setResultMessage("未绑定银行卡");
				return result;
			}
			List<String> strList = new ArrayList<String>(); 
			for (PaymentBindbankcard pb : list) {
				String bankName = BankUtils.getBankName(pb.getBank());
				String str = bankName + " 银行卡号：**** **** **** "+pb.getBank_ac_last4();
				strList.add(str);
			}
			
			result.setResult(strList);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}


	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	public void setBusinessUserExtDao(BusinessUserExtDao businessUserExtDao) {
		this.businessUserExtDao = businessUserExtDao;
	}

	public void setPaymentBindbankcardDao(
			PaymentBindbankcardDao paymentBindbankcardDao) {
		this.paymentBindbankcardDao = paymentBindbankcardDao;
	}

}
