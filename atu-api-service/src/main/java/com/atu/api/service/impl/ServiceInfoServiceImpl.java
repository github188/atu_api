package com.atu.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.atu.api.dao.ServiceInfoDao;
import com.atu.api.domain.ServiceInfo;
import com.atu.api.domain.query.ServiceInfoQuery;
import com.atu.api.domain.vo.ServiceInfoVO;
import com.atu.api.service.ServiceInfoService;
import com.atu.api.service.result.Result;
import com.atu.api.service.utils.EcUtils;

@Service(value="serviceInfoService")
public class ServiceInfoServiceImpl implements ServiceInfoService {
	private static final Logger log = LoggerFactory.getLogger(ServiceInfoServiceImpl.class);
	
	private ServiceInfoDao serviceInfoDao;

	/**
	 * 根据分类编号获取服务信息
	 */
	@Override
	public Result getServiceInfoByPage(ServiceInfoQuery serviceInfoQuery) {
		Result result = new Result();
		try{
			// 查询总个数
			int total = serviceInfoDao.countByCondition(serviceInfoQuery);
			if(total == 0){
				result.setResultCode("7006");
				result.setResultMessage("服务信息列表不存在");
				return result;
			}
			
			int totalPage = total/serviceInfoQuery.getPageSize() + 1;
			if(serviceInfoQuery.getPageNo() > totalPage){
				serviceInfoQuery.setPageNo(totalPage);
			}
			
			// 查询分页信息
			List<ServiceInfo> list = serviceInfoDao.selectByConditionForPage(serviceInfoQuery);
			List<ServiceInfoVO> voList = new ArrayList<ServiceInfoVO>();
			
			for (ServiceInfo serviceInfo : list) {
				ServiceInfoVO serviceInfoVO = new ServiceInfoVO();
				serviceInfoVO.setServiceId(serviceInfo.getServiceId());
				serviceInfoVO.setServiceImage(serviceInfo.getServiceImage());
				serviceInfoVO.setServiceName(serviceInfo.getServiceName());
				serviceInfoVO.setServiceTel(serviceInfo.getServiceTel());
				voList.add(serviceInfoVO);
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", total);
			map.put("totalPage", totalPage);
			map.put("curPage", serviceInfoQuery.getPageNo());
			map.put("list", voList);
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		
		return result;
	}

	public void setServiceInfoDao(ServiceInfoDao serviceInfoDao) {
		this.serviceInfoDao = serviceInfoDao;
	}

}
