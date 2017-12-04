package com.atu.api.dao;

import java.util.List;

import com.atu.api.domain.UmpInfo;
import com.atu.api.domain.query.UmpInfoQuery;

public interface UmpInfoDao{
	
	public Integer insert(UmpInfo umpInfo);

	public void modify(UmpInfo umpInfo);
	
	public UmpInfo selectByPrimaryKey(Integer id);

	public int countByCondition(UmpInfoQuery umpInfoQuery);
	
	public List<UmpInfo> selectByCondition(UmpInfoQuery umpInfoQuery);
	
	public List<UmpInfo> selectByConditionForPage(UmpInfoQuery umpInfoQuery);

	void modifyByCallBack(UmpInfo umpInfo);
	
}