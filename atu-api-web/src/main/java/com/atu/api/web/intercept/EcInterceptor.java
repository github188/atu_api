package com.atu.api.web.intercept;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atu.api.common.utils.JsonUtils;
import com.atu.api.common.utils.MD5Util;
import com.atu.api.service.result.Result;


public class EcInterceptor implements Filter {
	private static final Logger log = LoggerFactory.getLogger(EcInterceptor.class);
	
	private final static String secret = "d18ed7feb9db4621b95da86943f7717f";
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private void printResult(String resultCode, String resultMessage, HttpServletResponse response) throws IOException{
		Result result = new Result();
		PrintWriter pr = response.getWriter();
		response.setHeader("Content-Type", "application/json;charset=UTF-8");
		result.setSuccess(false);
		result.setResultCode(resultCode);
		result.setResultMessage(resultMessage);
		pr.print(JsonUtils.writeValue(result));
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		response.setHeader("Cache-Control","no-cache");   
	    response.setHeader("Pragma","no-cache");   
	    response.setDateHeader ("Expires", -1);
	    response.setHeader("Content-Type", "application/json;charset=UTF-8");
	    
	    HttpServletRequest req = (HttpServletRequest)request;
	    
	    String requestPath = req.getRequestURI();
	    System.out.println(sdf.format(new Date()) + "本次请求地址：" + requestPath+ "?" + req.getQueryString());
	   
        if(requestPath.contains("/v/getVersion")){
	    	chain.doFilter(request, response);
	    	return;
	    }
        if(requestPath.contains("/payOrder/payWX")){
        	String requestUrl = req.getRequestURI();
  		  	String requestStr = req.getQueryString();
        	log.info("微信回调路径==="+requestUrl);
        	log.info("微信回调参数==="+requestStr);
        	chain.doFilter(request, response);
	    	return;
        }
        if(requestPath.contains("/page/") || requestPath.contains("/css/") || requestPath.contains("/js/") || requestPath.contains("/img/")){
	    	chain.doFilter(request, response);
	    	return;
	    }
	    
		String v = request.getParameter("v");
		String timestamp = request.getParameter("timestamp");
		String sign = request.getParameter("sign");
		
		if(StringUtils.isBlank(v)){
			this.printResult("1001", "v不能为空", response);
			return;
		}
		
		if(StringUtils.isBlank(timestamp)){
			this.printResult("1001", "timestamp不能为空", response);
			return;
		}
		
		if(StringUtils.isBlank(sign)){
			this.printResult("1001", "sign不能为空", response);
			return;
		}
		
//		try{
//			Date date = sdf.parse(timestamp);
//			Date now = new Date();
//			if(Math.abs(now.getTime() - date.getTime()) > 1800000){
//				this.printResult("1001", "时间不正确，时间差不能大于30分钟。服务器当前时间为："+sdf.format(now), response);
//				return;
//			}
//		}catch (Exception e) {
//			log.error("", e);
//			this.printResult("1001", "时间格式不正确，必须是yyyy-MM-dd HH:mm:ss格式", response);
//			return;
//		}
		
		String mysign = MD5Util.md5Hex(secret + timestamp + v + secret).toUpperCase();
		if(!mysign.equals(sign)){
			this.printResult("1001", "sign不正确", response);
			return;
		}
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
	}
}
