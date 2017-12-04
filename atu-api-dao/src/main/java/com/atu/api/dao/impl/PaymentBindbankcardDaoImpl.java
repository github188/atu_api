package com.atu.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.atu.api.dao.PaymentBindbankcardDao;
import com.atu.api.domain.PaymentBindbankcard;



public class PaymentBindbankcardDaoImpl extends SqlMapClientTemplate implements PaymentBindbankcardDao {

	@Override
	public Integer insert(PaymentBindbankcard paymentBindbankcard) {
		return (Integer)insert("PaymentBindbankcard.insert", paymentBindbankcard);
	}

	@Override
	public void modify(PaymentBindbankcard paymentBindbankcard) {
		update("PaymentBindbankcard.updateByPrimaryKey", paymentBindbankcard);
	}

	@Override
	public PaymentBindbankcard selectByPrimaryKey(Integer id) {
		return (PaymentBindbankcard)queryForObject("PaymentBindbankcard.selectByPrimaryKey", id);
	}

	@Override
	public int countByCondition(PaymentBindbankcard paymentBindbankcard) {
		return (Integer)queryForObject("PaymentBindbankcard.countByCondition",paymentBindbankcard);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentBindbankcard> selectByCondition(PaymentBindbankcard paymentBindbankcard) {
		return (List<PaymentBindbankcard>)queryForList("PaymentBindbankcard.selectByCondition",paymentBindbankcard);
	}

}
