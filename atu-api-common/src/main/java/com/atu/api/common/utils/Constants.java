package com.atu.api.common.utils;

public  class Constants {
	public final static String[] return_trade_params = { "F0", "F2", "F3", "F4","F8", "F12",
		"F13", "F39", "F41", "F42", "F45", "F46", "VoidAmt", "UserFlag","MerSign" };
	public final static String[] return_creatTradeNo_params = { "F0", "F2", "F3", "F4", "F11",
		"F12", "F13", "F15", "F18", "F39", "F41", "F42", "F44", "F45",
		"F49", "MerSign" };
	public final static String[] return_getOrderState_params = { "F0", "F3", "F39", "F42",
		"F44", "F5", "MerSign" };
	public final static String url = "http://106.120.215.234:9888/GetPayResult";
	
	//交易支付访问协议路径待定
	public final static String trade_url ="";
	
}
