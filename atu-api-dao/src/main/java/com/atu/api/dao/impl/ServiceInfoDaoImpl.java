package com.atu.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.atu.api.dao.ServiceInfoDao;
import com.atu.api.domain.ServiceInfo;
import com.atu.api.domain.query.ServiceInfoQuery;
import com.atu.api.domain.vo.ServiceInfoVO;

@SuppressWarnings("unchecked")
public class ServiceInfoDaoImpl extends SqlMapClientTemplate implements ServiceInfoDao {
	
	@Override
	public List<ServiceInfo> selectByCondition(ServiceInfoQuery serviceInfoQuery) {
		return (List<ServiceInfo>)queryForList("ServiceInfo.selectByCondition", serviceInfoQuery);
	}

	@Override
	public List<ServiceInfo> selectByConditionForPage(ServiceInfoQuery serviceInfoQuery) {
		return (List<ServiceInfo>)queryForList("ServiceInfo.selectByConditionForPage", serviceInfoQuery);
	}

	@Override
	public int countByCondition(ServiceInfoQuery serviceInfoQuery) {
		return (Integer)queryForObject("ServiceInfo.countByCondition", serviceInfoQuery);
	}

}
