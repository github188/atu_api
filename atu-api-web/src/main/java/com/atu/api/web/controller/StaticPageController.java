package com.atu.api.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("page")
public class StaticPageController {
	
	/**
	 * 注册协议
	 * @return
	 */
	@RequestMapping(value="agreement", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView init(HttpServletRequest reuqest,HttpServletResponse response, ModelAndView mv){
		mv.setViewName("Agreement");
		return mv;
	}
}
