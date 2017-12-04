package com.atu.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.atu.api.dao.TbCouponDao;
import com.atu.api.dao.TbCouponPromoDao;
import com.atu.api.domain.TbCoupon;
import com.atu.api.domain.TbCouponPromo;
import com.atu.api.domain.query.TbCouponPromoQuery;
import com.atu.api.domain.query.TbCouponQuery;
import com.atu.api.service.CouponService;
import com.atu.api.service.result.Result;
import com.atu.api.service.utils.EcUtils;

/**
 * 优惠券相关
 * @author BCSK742A
 *
 */
@Service("couponService")
public class CouponServiceImpl implements CouponService {
	private static final Logger log = LoggerFactory.getLogger(CouponServiceImpl.class);
	
	private TbCouponDao tbCouponDao;
	private TbCouponPromoDao tbCouponPromoDao;
	
	/** 查询我的优惠券 */
	@Override
	public Result selectByUserId(Integer userId) {
		Result result = new Result();
		try{
			//查询条件， 实例化TbCouponQuery
			TbCouponQuery tbCouponQuery = new TbCouponQuery();
			//Yn为有效状态，1为有效，0位无效
			tbCouponQuery.setYn(1);
			//查询条件：userId的值赋给TbCouponQuery中用户ID
			tbCouponQuery.setUserId(userId);
			// 查询条件：优惠券状态，0为未使用，1为已使用，2位已过期，3位已锁定
			tbCouponQuery.setCouponState(0);
			//创建wsyList集合接收从数据库tb_coupon表中查询到的未使用优惠券信息
			List<TbCoupon> wsyList = tbCouponDao.selectByCondition(tbCouponQuery);
			// 查询优惠券状态为3已锁定的优惠券信息
			tbCouponQuery.setCouponState(3);
			//创建ysdList集合接收从数据库tb_coupon表中查询到的已锁定优惠券信息
			List<TbCoupon> ysdList = tbCouponDao.selectByCondition(tbCouponQuery);
			// 查询优惠券状态为1已使用的优惠券信息
			tbCouponQuery.setCouponState(1);
			//创建ysyList集合接收从数据库tb_coupon表中查询到的已锁定优惠券信息
			List<TbCoupon> ysyList = tbCouponDao.selectByCondition(tbCouponQuery);
			// 查询优惠券状态为0已过期的优惠券信息
			tbCouponQuery.setCouponState(2);
			//创建ygqList集合接收从数据库tb_coupon表中查询到的已锁定优惠券信息
			List<TbCoupon> ygqList = tbCouponDao.selectByCondition(tbCouponQuery);
			
			//初始化一个新的list集合
			List<TbCoupon> list = new ArrayList<TbCoupon>();
			//把wsyList集合中值添加给list
			list.addAll(wsyList);
			//把ysdList集合中值添加给list
			list.addAll(ysdList);
			//把ysyList集合中值添加给list
			list.addAll(ysyList);
			//把ygqList集合中值添加给list
			list.addAll(ygqList);
			
			//把list集合中的值赋给result
			result.setResult(list);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	/** 使用优惠券 */
	@Override
	public Result selectUseByUserId(Integer userId, BigDecimal price) {
		Result result = new Result();
		try{
			// 准备数据,ky集合为存储可用优惠券
			List<TbCoupon> ky = new ArrayList<TbCoupon>();
			//bky集合为存储不可用优惠券
			List<TbCoupon> bky = new ArrayList<TbCoupon>();
			//kymj集合为存储可用满减券
			List<TbCoupon> kymj = new ArrayList<TbCoupon>();
			//kyzj集合为存储可用直减券
			List<TbCoupon> kyzj = new ArrayList<TbCoupon>();
			//kymyf集合为存储可用免运费券
			List<TbCoupon> kymyf = new ArrayList<TbCoupon>();
			// 查询数据，实例化TbCouponQuery
			TbCouponQuery tbCouponQuery = new TbCouponQuery();
			//查询条件：Yn为有效状态，1为有效，0位无效
			tbCouponQuery.setYn(1);
			//查询条件：优惠券状态，0为未使用，1为已使用，2位已过期，3位已锁定
			tbCouponQuery.setCouponState(0);
			//查询条件：userId的值赋给TbCouponQuery中用户ID
			tbCouponQuery.setUserId(userId);
			//查询条件：根据优惠券类型查询满减券
			tbCouponQuery.setCouponType(0);
			//创建不可用满减券bkymj集合接收从数据库tb_coupon表中查询的优惠券信息
			List<TbCoupon> bkymj = tbCouponDao.selectByCondition(tbCouponQuery);
			
			// 根据优惠券类型查询直减券
			tbCouponQuery.setCouponType(1);
			//创建不可用直减券bkyzj集合接收从数据库tb_coupon表中查询的优惠券信息
			List<TbCoupon> bkyzj = tbCouponDao.selectByCondition(tbCouponQuery);
			
			// 根据优惠券类型查询免运费券
			tbCouponQuery.setCouponType(2);
			//创建不可用免运费券bkymyf集合接收从数据库tb_coupon表中查询的优惠券信息
			List<TbCoupon> bkymyf = tbCouponDao.selectByCondition(tbCouponQuery);
			
			//判断bkymj是否有赋值，如果有返回true进入
			if(!bkymj.isEmpty()){
				//使用foreach循环把bkymj中的值赋给tbCoupon
				for (TbCoupon tbCoupon : bkymj) {
					//判断
					//得到订单限额getOrderQuota()
					//使用price的值与根据BigDecimal重载OrderQuota订单限额的值用compareTo比较，返回三种结果，1,0，-1;
					if(price.compareTo(new BigDecimal(tbCoupon.getOrderQuota())) >= 0){
						//可以满减添加优惠券信息
						kymj.add(tbCoupon);
					}
				}
				//把可用满减kymj集合赋给可用满减集合ky接收
				ky.addAll(kymj);
				//removeAll掉kymj集合中信息存储在bkymj集合
				bkymj.removeAll(kymj);
			}
			//不可用满减优惠券添加到不可用集合中
			bky.addAll(bkymj);
			//判断不可用直减集合中的值不为空进入
			if(!bkyzj.isEmpty()){
				//使用for循环把不可用直减集合中的值赋给tbCoupon优惠券信息类
				for (TbCoupon tbCoupon : bkyzj) {
					//tbCoupon优惠券信息类中的值赋给可用直减集合
					kyzj.add(tbCoupon);
				}
				//可用直减集合添加到可用集合中
				ky.addAll(kyzj);
				//不可用直减中的值把包含可用直减部分删除掉
				bkyzj.removeAll(kyzj);
			}
			//不可用直减集合添加到不可用优惠券集合
			bky.addAll(bkyzj);
			//判断不可用 免运费券不为空进入
			if(!bkymyf.isEmpty()){
				
				for (TbCoupon tbCoupon : bkymyf) {
					if(price.compareTo(new BigDecimal(39)) <= 0){
						kymyf.add(tbCoupon);
					}
				}
				ky.addAll(kymyf);
				bkymyf.removeAll(kymyf);
			}
			bky.addAll(bkymyf);
			//创建map集合接收list集合中信息
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("usable", ky);
			map.put("nousable", bky);
			result.setResult(map);
			
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	@Override
	public Result drawCoupon(Integer userId, Integer couponPromoId) {
		Result result = new Result();
		try{
			TbCouponPromoQuery tbCouponPromoQuery = new TbCouponPromoQuery();
			tbCouponPromoQuery.setCouponPromoId(couponPromoId);
			tbCouponPromoQuery.setCouponPromoState(1);
			List<TbCouponPromo> tbCouponPromoList = tbCouponPromoDao.selectByCondition(tbCouponPromoQuery);
			if(tbCouponPromoList == null || tbCouponPromoList.isEmpty()){
				log.info("活动已结束");
				result.setResult(4);
			}else{
				TbCouponPromo tbCouponPromo = tbCouponPromoList.get(0);
				if(tbCouponPromo.getCouponValidEndtime().compareTo(new Date()) > 0){
					TbCouponQuery tbCouponQuery = new TbCouponQuery();
					tbCouponQuery.setCouponPromId(couponPromoId);
					List<TbCoupon> couponList = tbCouponDao.selectByCondition(tbCouponQuery);
					tbCouponQuery.setUserId(userId);
					List<TbCoupon> list = tbCouponDao.selectByCondition(tbCouponQuery);
					if(list == null || list.isEmpty()){
						int count = 0;
						if(couponList != null && !couponList.isEmpty()){
							count = couponList.size();
						}
						if(tbCouponPromo.getCouponApplyAmount() > count){
							TbCoupon tbCoupon = new TbCoupon();
							tbCoupon.setUserId(userId);
							tbCoupon.setCouponPromId(couponPromoId);
							tbCoupon.setCouponName(tbCouponPromo.getCouponPromoName());
							tbCoupon.setCouponType(tbCouponPromo.getCouponType());
							tbCoupon.setOrderQuota(tbCouponPromo.getOrderQuota());
							tbCoupon.setCouponAmount(tbCouponPromo.getCouponAmount());
							tbCoupon.setCouponState(0);
							tbCoupon.setYn(1);
							tbCoupon.setCouponValidStarttime(tbCouponPromo.getCouponValidStarttime());
							tbCoupon.setCouponValidEndtime(tbCouponPromo.getCouponValidEndtime());
							tbCouponDao.insert(tbCoupon);
							
							tbCouponPromo.setCouponGrantAmount(tbCouponPromo.getCouponGrantAmount() + 1);
							tbCouponPromoDao.modify(tbCouponPromo);
							
							log.info("用户成功领取");
							result.setResult(0);
						}else{
							log.info("活动数量已经领取完");
							result.setResult(2);
						}
					}else{
						log.info("用户已领取");
						result.setResult(1);
					}
				}else{
					log.info("活动已过期");
					result.setResult(3);
				}
			}
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	public void setTbCouponDao(TbCouponDao tbCouponDao) {
		this.tbCouponDao = tbCouponDao;
	}

	public void setTbCouponPromoDao(TbCouponPromoDao tbCouponPromoDao) {
		this.tbCouponPromoDao = tbCouponPromoDao;
	}
	
}
