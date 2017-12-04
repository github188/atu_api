package com.atu.api.dao;

import java.util.List;

import com.atu.api.domain.PaymentBindbankcard;

public interface PaymentBindbankcardDao {

	public Integer insert(PaymentBindbankcard paymentBindbankcard);

	public void modify(PaymentBindbankcard paymentBindbankcard);

	public PaymentBindbankcard selectByPrimaryKey(Integer id);

	public int countByCondition(PaymentBindbankcard paymentBindbankcard);

	public List<PaymentBindbankcard> selectByCondition(PaymentBindbankcard paymentBindbankcard);

	//public List<UmpInfo> selectByConditionForPage(PaymentBindbankcard paymentBindbankcard);
}
