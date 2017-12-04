package com.atu.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.atu.api.dao.OrderDetailDao;
import com.atu.api.domain.OrderDetail;

public class OrderDetailDaoImpl extends SqlMapClientTemplate implements OrderDetailDao {

	@Override
	public Integer insert(OrderDetail orderDetail) {
		return (Integer)insert("OrderDetail.insert", orderDetail);
	}

	@Override
	public List<OrderDetail> selectByOrderId(int orderId) {
		return (List<OrderDetail>)queryForList("OrderDetail.selectByOrderId",orderId);
	}
	
	@Override
	public List<OrderDetail> selectBySubOrderId(int subOrderId) {
		return (List<OrderDetail>)queryForList("OrderDetail.selectBySubOrderId",subOrderId);
	}
	
	@Override
	public void updateOderIdByOrderId(OrderDetail orderDetail){
		update("OrderDetail.updateOderIdByOrderId", orderDetail);
	}

}
