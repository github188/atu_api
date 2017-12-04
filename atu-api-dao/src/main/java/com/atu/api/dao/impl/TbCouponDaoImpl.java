package com.atu.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.atu.api.dao.TbCouponDao;
import com.atu.api.domain.TbCoupon;
import com.atu.api.domain.query.TbCouponQuery;

public class TbCouponDaoImpl extends SqlMapClientTemplate implements TbCouponDao {

	/** 添加优惠券 */
	@Override
	public Integer insert(TbCoupon tbCoupon) {
		return (Integer)insert("TbCoupon.insert",tbCoupon);
	}

	/** 依据优惠券ID修改优惠券 */
	@Override
	public void modify(TbCoupon tbCoupon) {
		update("TbCoupon.updateByPrimaryKey",tbCoupon);
	}

	/** 依据优惠券ID查询优惠券 */
	@Override
	public TbCoupon selectByTbCouponId(int couponId) {
		return (TbCoupon)queryForObject("TbCoupon.selectByPrimaryKey",couponId);
	}

	/** 根据相应的条件查询满足条件的优惠券的总数 */
	@Override
	public int countByCondition(TbCouponQuery tbCouponQuery) {
		return (Integer)queryForObject("TbCoupon.countByTbCouponCondition",tbCouponQuery);
	}

	/** 根据条件查询优惠券 */
	@Override
	public List<TbCoupon> selectByCondition(TbCouponQuery tbCouponQuery) {
		return (List<TbCoupon>)queryForList("TbCoupon.selectByTbCouponCondition",tbCouponQuery);
	}

	/** 根据相应的条件分页查询优惠券 */
	@Override
	public List<TbCoupon> selectByConditionForPage(TbCouponQuery tbCouponQuery) {
		return (List<TbCoupon>)queryForList("TbCoupon.selectByTbCouponConditionForPage",tbCouponQuery);
	}

}
