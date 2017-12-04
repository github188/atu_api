package com.atu.api.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atu.api.domain.query.BusinessUserExtQuery;
import com.atu.api.service.BusinessUserService;
import com.atu.api.service.result.Result;
import com.atu.api.web.base.BaseController;

/** 查询商户信息  */

@Controller
@RequestMapping("/businessUser")
public class BusinessUserController extends BaseController {
	
    private BusinessUserService businessUserService;
	
    /**
     * 根据条件查询商户信息(分页)
     * @param businessUserExtQuery 根据条件查询商户
     * @return
     */
	@RequestMapping(value="getBusinessUserByPage", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getBusinessUserByPage(BusinessUserExtQuery businessUserExtQuery,HttpServletRequest request, HttpServletResponse response, ModelMap context){
        return businessUserService.getBusinessUserByPage(businessUserExtQuery);
	}

	/**
	 * 根据商户ID,获得商户信息
	 * @param userId 商户id
	 * @return
	 */
	@RequestMapping(value="getBusinessUserByUserId", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getBusinessUserByUserId(Integer userId,HttpServletRequest request, HttpServletResponse response, ModelMap context){
        Result result = new Result();
		if( null == userId || userId < 1 ){
			result.setResultCode("1001");
			result.setResultMessage("userId不能为空");
			return result;
		}
        return businessUserService.getBusinessUserExtByUserID(userId);
	}

    public void setBusinessUserService(BusinessUserService businessUserService) {
        this.businessUserService = businessUserService;
    }
}
