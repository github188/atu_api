package com.atu.api.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atu.api.web.base.BaseController;
import com.atu.api.domain.query.ServiceInfoQuery;
import com.atu.api.service.ServiceInfoService;
import com.atu.api.service.result.Result;

@Controller
@RequestMapping("/serviceInfo")
public class ServiceInfoController extends BaseController {
	
	private ServiceInfoService serviceInfoService;
	
	/**
	 * 根据分类编号获取服务信息(分页)
	 * @param serviceInfoQuery 服务信息
	 * @return
	 */
	@RequestMapping(value="getServiceInfoByPage", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getServiceInfoByPage(ServiceInfoQuery serviceInfoQuery, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		
		if(serviceInfoQuery.getCategoryId() == null){
			result.setResultCode("1001");
			result.setResultMessage("categoryId不能为空");
			return result;
		}
		
		return serviceInfoService.getServiceInfoByPage(serviceInfoQuery);
	}

	public void setServiceInfoService(ServiceInfoService serviceInfoService) {
		this.serviceInfoService = serviceInfoService;
	}
	
}
