package com.atu.api.web.wx;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import com.atu.api.common.utils.HttpUtils;
import com.atu.api.common.utils.JsonUtils;
import com.atu.api.domain.wx.WXTicketInfo;
import com.atu.api.domain.wx.WXUserInfo;
import com.atu.api.service.utils.RedisUtils;

public class WXUtils {
	
	// 获取服务号access_token
	public static String getToken(){
		String access_token = RedisUtils.get(WXValues.AccessTokenStr);
		if(StringUtils.isBlank(access_token)){
			// 获取token
			String param = "grant_type=client_credential&appid="+WXValues.AppID+"&secret="+WXValues.AppSecret;
			String date = HttpUtils.httpGetData("https://api.weixin.qq.com/cgi-bin/token", param, null);
			access_token = (String) JsonUtils.readValue(date).get("access_token");
			RedisUtils.set(WXValues.AccessTokenStr, WXValues.WxTime, access_token);
			System.out.println("access_token==="+access_token);
		}
		return access_token;
	}
	
	// 获取服务号jsapi_ticket
	public static String getTicket(String access_token){
		String jsapi_ticket = RedisUtils.get(WXValues.JsapiTicketStr);
		if(StringUtils.isBlank(jsapi_ticket)){
			// 获取jsticket
			String param = "access_token="+access_token+"&type=jsapi";
			String date = HttpUtils.httpGetData("https://api.weixin.qq.com/cgi-bin/ticket/getticket", param, null);
			jsapi_ticket = (String) JsonUtils.readValue(date).get("ticket");
			RedisUtils.set(WXValues.JsapiTicketStr, WXValues.WxTime, jsapi_ticket);
			System.out.println("jsapi_ticket==="+jsapi_ticket);
		}
		return jsapi_ticket;
	}
	
	// 加密jsapi_ticket
	public static WXTicketInfo shaTicket(String jsapi_ticket, String url){
		WXTicketInfo wxTicketInfo = new WXTicketInfo();
		
		String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
        
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
            System.out.println("signature==="+signature);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        
        wxTicketInfo.setAppId(WXValues.AppID);
        wxTicketInfo.setTimestamp(timestamp);
        wxTicketInfo.setNonceStr(nonce_str);
        wxTicketInfo.setSignature(signature);
		
        return wxTicketInfo;
	}
	
	// 获取用户授权code
	public static void getCode(String action, HttpServletResponse response) throws Exception{
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize";
		String param = "appid="+WXValues.AppID+"&redirect_uri="+WXValues.redirectUrl+"&response_type=code&scope=snsapi_base&state="+action+"#wechat_redirect";
		response.sendRedirect(url+"?"+param);
	}
		
	// 获取用户openId
	public static String getOpenId(String code){
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
		String param = "appid="+WXValues.AppID+"&secret="+WXValues.AppSecret+"&code="+code+"&grant_type=authorization_code";
		String date = HttpUtils.httpGetData(url, param, null);
		String openId = (String) JsonUtils.readValue(date).get("openid");
		return openId;
	}
	
	// 获取用户信息
	public static WXUserInfo getWXUser(String token, String openId){
		System.out.println("获取用户信息！");
		String url = "https://api.weixin.qq.com/cgi-bin/user/info";
		String param = "access_token="+token+"&openid="+openId;
		String data = HttpUtils.httpGetData(url, param, null);
		return JsonUtils.readValue(data, WXUserInfo.class);
	}
	
	private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}
