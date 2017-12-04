package com.atu.api.dao;

import java.util.List;

import com.atu.api.domain.ServiceInfo;
import com.atu.api.domain.query.ServiceInfoQuery;

public interface ServiceInfoDao {
	/**
	 * 根据条件获取服务信息
	 * @param ServiceInfoQuery
	 * @return
	 */
	public List<ServiceInfo> selectByCondition(ServiceInfoQuery serviceInfoQuery);
	
	/**
	 * 根据条件获取服务信息-分页
	 * @param ServiceInfoQuery
	 * @return
	 */
	public List<ServiceInfo> selectByConditionForPage(ServiceInfoQuery serviceInfoQuery);
	
	/**
	 * 根据相应的条件查询满足条件的服务信息的总数
	 * @param ServiceInfoQuery
	 * @return
	 */
	public int countByCondition(ServiceInfoQuery serviceInfoQuery);
}
