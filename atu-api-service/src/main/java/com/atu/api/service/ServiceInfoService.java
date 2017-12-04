package com.atu.api.service;

import com.atu.api.domain.query.ServiceInfoQuery;
import com.atu.api.service.result.Result;

public interface ServiceInfoService {
	/**
	 * 根据分类编号获取服务信息
	 * @param ServiceInfoQuery 查询条件
	 * @return
	 */
	public Result getServiceInfoByPage(ServiceInfoQuery serviceInfoQuery);
}
