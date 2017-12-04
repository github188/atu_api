package com.atu.api.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.atu.api.common.utils.DESUtil;
import com.atu.api.dao.UserInfoDao;
import com.atu.api.dao.WxUserDao;
import com.atu.api.domain.UserInfo;
import com.atu.api.domain.WxUser;
import com.atu.api.domain.query.UserInfoQuery;
import com.atu.api.service.WxUserService;
import com.atu.api.service.result.Result;
import com.atu.api.service.utils.EcUtils;

@Service(value="wxUserService")
public class WxUserServiceImpl implements WxUserService {
	private static final Logger log = LoggerFactory.getLogger(WxUserServiceImpl.class);
	
	private WxUserDao wxUserDao;
	private UserInfoDao userInfoDao;

	@Override
	public Result login(final WxUser wxUser) {
		Result result = new Result();
		String token = "";
		try{
			UserInfoQuery userInfoQuery = new UserInfoQuery();
			userInfoQuery.setOpenId(wxUser.getOpenid());
			List<UserInfo> list = userInfoDao.selectByCondition(userInfoQuery);
			if(list == null || list.isEmpty()){
				token = "";
			}else{
				UserInfo userInfo = list.get(0);
				token = createLoginToken(userInfo);
			}
			wxUserDao.insert(wxUser);
			result.setResult(token);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	@Override
	public Result selectById(Integer id) {
		Result result = new Result();
		try{
			WxUser wxUser = wxUserDao.selectById(id);
			if(wxUser == null){
				result.setResultCode("10001");
				result.setResultMessage("微信用户信息不存在");
				return result;
			}
			result.setResult(wxUser);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	/**
	 * 创建用户登录令牌
	 */
	private String createLoginToken(UserInfo userInfo) throws Exception{
		String token = userInfo.getUserId() + "-" + userInfo.getMobile() + "-" + userInfo.getUserType();
		return DESUtil.encrypt(token, "gs2y601z");
	}

	public void setWxUserDao(WxUserDao wxUserDao) {
		this.wxUserDao = wxUserDao;
	}

	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}
}
