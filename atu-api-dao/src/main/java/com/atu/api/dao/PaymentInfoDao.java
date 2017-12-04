package com.atu.api.dao;


import java.util.List;

import com.atu.api.domain.PaymentInfo;
import com.atu.api.domain.query.PaymentInfoQuery;

public interface PaymentInfoDao{
	/**
	 * 添加支付记录信息
	 * @param paymentInfo
	 * @return
	 */
	public Integer insert(PaymentInfo paymentInfo);

	/**
	 * 依据订单ID查询支付记录信息
	 * @param paymentId
	 * @return
	 */
	public List<PaymentInfo> selectByCondition(PaymentInfoQuery paymentInfoQuery);
	
}