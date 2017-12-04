package com.atu.api.service;

import com.atu.api.domain.query.BusinessUserExtQuery;
import com.atu.api.service.result.Result;

public interface BusinessUserService {
	
	/**
     * 根据条件查询商户信息(分页)
     * @param userId 商户ID
     * @return
     */
    public Result getBusinessUserByPage(BusinessUserExtQuery businessUserExtQuery);
	
    /**
     * 根据商户ID,获得商户信息
     * @param userId 商户ID
     * @return
     */
    public Result getBusinessUserExtByUserID(Integer userId);
}
