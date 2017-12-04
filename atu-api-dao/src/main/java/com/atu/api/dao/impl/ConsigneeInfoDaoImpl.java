package com.atu.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.atu.api.dao.ConsigneeInfoDao;
import com.atu.api.domain.ConsigneeInfo;
import com.atu.api.domain.query.ConsigneeInfoQuery;

public class ConsigneeInfoDaoImpl extends SqlMapClientTemplate implements ConsigneeInfoDao {

	@Override
	public Integer insert(ConsigneeInfo consigneeInfo) {
		return (Integer)insert("ConsigneeInfo.insert", consigneeInfo);
	}

	@Override
	public void modify(ConsigneeInfo consigneeInfo) {
		update("ConsigneeInfo.updateByPrimaryKey", consigneeInfo);
	}

	@Override
	public ConsigneeInfo selectByConsigneeId(int consigneeId) {
		return (ConsigneeInfo)queryForObject("ConsigneeInfo.selectByPrimaryKey",consigneeId);
	}

	@Override
	public ConsigneeInfo selectByUserIdForLastUse(int userId) {
		return (ConsigneeInfo)queryForObject("ConsigneeInfo.selectByUserIdForLastUse",userId);
	}
	
	@Override
	public ConsigneeInfo selectByUserIdForDefault(int userId) {
		return (ConsigneeInfo)queryForObject("ConsigneeInfo.selectByUserIdForDefault",userId);
	}

	@Override
	public int countByCondition(ConsigneeInfoQuery consigneeInfoQuery) {
		return (Integer)queryForObject("ConsigneeInfo.countByCondition",consigneeInfoQuery);
	}

	@Override
	public List<ConsigneeInfo> selectByCondition(
			ConsigneeInfoQuery consigneeInfoQuery) {
		return (List<ConsigneeInfo>)queryForList("ConsigneeInfo.selectByCondition",consigneeInfoQuery);
	}

	@Override
	public int delConsigneeInfo(Integer consigneeId) {
		return delete("ConsigneeInfo.delConsigneeInfo", consigneeId);
	}

}
