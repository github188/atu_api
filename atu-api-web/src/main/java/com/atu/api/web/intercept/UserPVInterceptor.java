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
import com.atu.api.web.controller.utils.MetaqUtil;
import com.atu.api.web.controller.utils.RedisUtils;


public class UserPVInterceptor  implements HandlerInterceptor {
	private static final Logger log = LoggerFactory.getLogger(UserPVInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		try{
			String token = request.getParameter("token");
			Integer userId = 0;
			if(token!=null){
				String value = DESUtil.decrypt(token, "gs2y601z");
				String [] tokenArr = value.split("-");
				userId = Integer.parseInt(tokenArr[0]);
			}
			
			String  url =  request.getRequestURI();
			Integer fromWhere = Integer.parseInt(request.getParameter("fromWhere").toString());
			String uv = "";
			String pv = "";
			
			if(fromWhere == 1){
				if("/indexRecommendItem/getIndexRecommendItemByPage".equals(url)){
					pv = "30_1001_1";
					uv = "30_1001_2";
					
				}else if("/searchHotwords/selectSearchHotwords".equals(url)){
					pv = "30_1002_1";
					uv = "30_1002_2";
				}else if("/cartInfo/selectCartInfo".equals(url)){
					pv = "30_1003_1";
					uv = "30_1003_2";
				}else if("/orderInfo/getOrderInfosByUserId".equals(url)){
					pv = "30_1004_1";
					uv = "30_1004_2";
				}else if("/favorites/delFavorites".equals(url) || "/favorites/selectByPage".equals(url)){
					pv = "30_1005_1";
					uv = "30_1005_2";
				}else if("/item/getItemByItemId".equals(url)){
					pv = "30_1006_1";
					uv = "30_1006_2";
				}else if("/cartInfo/buyNow".equals(url) || "/cartInfo/confirmCartInfo".equals(url)){
					pv = "30_1007_1";
					uv = "30_1007_2";
				}else if("/orderInfo/submit".equals(url)){
					pv = "30_1008_1";
					uv = "30_1008_2";
				}
				
			}else if(fromWhere == 2){
				
				if("/indexRecommendItem/getIndexRecommendItemByPage".equals(url)){
					pv = "40_1001_1";
					uv = "40_1001_2";
				}else if("/searchHotwords/selectSearchHotwords".equals(url)){
					pv = "40_1002_1";
					uv = "40_1002_2";
				}else if("/cartInfo/selectCartInfo".equals(url)){
					pv = "40_1003_1";
					uv = "40_1003_2";
				}else if("/orderInfo/getOrderInfosByUserId".equals(url)){
					pv = "40_1004_1";
					uv = "40_1004_2";
				}else if("/favorites/delFavorites".equals(url) || "/favorites/selectByPage".equals(url)){
					pv = "40_1005_1";
					uv = "40_1005_2";
				}else if("/item/getItemByItemId".equals(url)){
					pv = "40_1006_1";
					uv = "40_1006_2";
				}else if("/cartInfo/buyNow".equals(url) || "/cartInfo/confirmCartInfo".equals(url)){
					pv = "40_1007_1";
					uv = "40_1007_2";
				}else if("/orderToGetTradeNo/getWxTradeNo".equals(url) || "/paymentInfo/accountInfo".equals(url)){
					pv = "40_1008_1";
					uv = "40_1008_2";
				}
			}
			
			MetaqUtil.sendMsg("risk", pv);
			
			String key = "api-cache-userId-" + uv;
			String cache = RedisUtils.get(key);
			if(StringUtils.isEmpty(cache)){
				MetaqUtil.sendMsg("risk", uv);
				RedisUtils.set(key, userId.toString());
			}else{
				if(!StringUtils.contains(cache,userId.toString())){
					RedisUtils.set(key, cache+"#"+userId);
					MetaqUtil.sendMsg("risk", uv);
				}
			}

			
			return true;
		}catch (Exception e) {
			log.error("", e);
			this.printResult("1001", "", response);
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
	
	
	public static void main(String[] args) {
//		RedisUtils.del("api-cache-userId-1");
		
		MetaqUtil.sendMsg("risk", "40_1001_1");
		MetaqUtil.sendMsg("risk", "40_1001_2");
	}
}
