package com.atu.api.web.intercept;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.atu.api.common.utils.DESUtil;
import com.atu.api.common.utils.JsonUtils;
import com.atu.api.service.result.Result;


public class LoginInterceptor  implements HandlerInterceptor {
	private static final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		try{
			String requestPath = request.getRequestURI();
            if(requestPath.contains("/v/getVersion")){
		    	return true;
		    }
			String token = request.getParameter("token");
			if(StringUtils.isBlank(token)){
				this.printResult("1001", "token不能为空", response);
				return false;
			}
			String value = DESUtil.decrypt(token, "gs2y601z");
			if(StringUtils.isBlank(value)){
				this.printResult("1001", "token不正确", response);
				return false;
			}
			
			String [] tokenArr = value.split("-");
			if(tokenArr == null || tokenArr.length != 3){
				this.printResult("1001", "token不正确", response);
				return false;
			}
			
			request.setAttribute("userId", tokenArr[0]);
			request.setAttribute("mobile", tokenArr[1]);
			request.setAttribute("userType", tokenArr[2]);
			
			return true;
		}catch (Exception e) {
			log.error("", e);
			this.printResult("1001", "token不正确", response);
		}
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}
	
	private void printResult(String resultCode, String resultMessage, HttpServletResponse response) throws Exception{
		Result result = new Result();
		PrintWriter pr = response.getWriter();
		response.setHeader("Content-Type", "application/json;charset=UTF-8");
		result.setSuccess(false);
		result.setResultCode(resultCode);
		result.setResultMessage(resultMessage);
		pr.print(JsonUtils.writeValue(result));
	}
}
