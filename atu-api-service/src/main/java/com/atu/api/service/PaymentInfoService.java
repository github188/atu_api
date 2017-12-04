package com.atu.api.service;

import com.atu.api.domain.PaymentBindbankcard;
import com.atu.api.domain.PaymentInfo;
import com.atu.api.domain.query.PaymentInfoQuery;
import com.atu.api.service.result.Result;

public interface PaymentInfoService {
	/**
	 * 添加订单支付记录
	 * @param paymentInfo
	 * @return
	 */
	public Result addPaymentInfo(PaymentInfo paymentInfo);
	
	/**
	 * 分页查询
	 * @param paymentInfoQuery
	 * @return
	 */
	public Result getPaymentInfos(PaymentInfoQuery paymentInfoQuery);
	
	/**
	 * 转账账户信息
	 * @param userId
	 * @return
	 */
	public Result accountInfo();

	Result submit(PaymentBindbankcard paymentBindbankcard,
			PaymentInfoQuery paymentInfoQuery, Integer verify_code);

	Result sendCode(PaymentBindbankcard paymentBindbankcard,
			PaymentInfoQuery paymentInfoQuery);
	
}
