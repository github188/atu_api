package com.atu.api.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.atu.api.common.utils.JsonUtils;
import com.atu.api.dao.AddressDao;
import com.atu.api.dao.BusinessUserExtDao;
import com.atu.api.dao.CartInfoDao;
import com.atu.api.dao.ConsigneeInfoDao;
import com.atu.api.dao.ItemDao;
import com.atu.api.dao.OrderConsigneeDao;
import com.atu.api.dao.OrderDetailDao;
import com.atu.api.dao.OrderInfoDao;
import com.atu.api.dao.PaymentInfoDao;
import com.atu.api.dao.PromotionInfoDao;
import com.atu.api.dao.PromotionSkuDao;
import com.atu.api.dao.SkuDao;
import com.atu.api.dao.TbCouponDao;
import com.atu.api.domain.Address;
import com.atu.api.domain.BusinessUserExt;
import com.atu.api.domain.CartInfo;
import com.atu.api.domain.ConsigneeInfo;
import com.atu.api.domain.Item;
import com.atu.api.domain.OrderConsignee;
import com.atu.api.domain.OrderDetail;
import com.atu.api.domain.OrderInfo;
import com.atu.api.domain.PaymentInfo;
import com.atu.api.domain.PromotionInfo;
import com.atu.api.domain.PromotionSku;
import com.atu.api.domain.Sku;
import com.atu.api.domain.TbCoupon;
import com.atu.api.domain.query.CartInfoQuery;
import com.atu.api.domain.query.OrderConsigneeQuery;
import com.atu.api.domain.query.OrderInfoQuery;
import com.atu.api.domain.query.PaymentInfoQuery;
import com.atu.api.domain.query.PromotionSkuQuery;
import com.atu.api.service.OrderInfoService;
import com.atu.api.service.result.Result;
import com.atu.api.service.utils.EcUtils;

@Service(value="orderInfoService")
public class OrderInfoServiceImpl implements OrderInfoService {
	private static final Logger log = LoggerFactory.getLogger(OrderInfoServiceImpl.class);

	private SkuDao skuDao;
	private ItemDao itemDao;
    private AddressDao addressDao;
    private CartInfoDao cartInfoDao;
    private TbCouponDao tbCouponDao;
    private OrderInfoDao orderInfoDao;
	private OrderDetailDao orderDetailDao;
	private PaymentInfoDao paymentInfoDao;
	private PromotionSkuDao promotionSkuDao;
	private PromotionInfoDao promotionInfoDao;
    private ConsigneeInfoDao consigneeInfoDao;
    private OrderConsigneeDao orderConsigneeDao;
	private BusinessUserExtDao businessUserExtDao;
	private DataSourceTransactionManager transactionManager;
    
	/**
	 * 下单方法
	 */
	@Override
	public Result submit(final OrderInfo orderInfo, final List<OrderDetail> orderDetailList, String coupons) {
		final Result result = new Result();
		try{
			Date now = new Date();
			Integer venderUserId = -1;
			//promotionMap 优惠明细
			Map<Integer, BigDecimal> promotionMap = new HashMap<Integer, BigDecimal>();
			
			int allNum = 0;
			// 组装订单详情信息
			for(int i=0;i<orderDetailList.size();i++){
				OrderDetail orderDetail = orderDetailList.get(i);
				orderDetail.setCreated(now);
				
				Item item = itemDao.selectByItemId(orderDetail.getItemId());
				
				if(item == null){
					result.setResultMessage("商品编号为："+orderDetail.getItemId()+"不存在");
					result.setResultCode("8009");
					return result;
				}
				
				if(item.getItemStatus() != 1 || item.getYn() != 1){
					result.setResultMessage("商品编号为："+item.getItemId()+"已下架");
					result.setResultCode("8010");
					return result;
				}
				
				if(item.getLeastBuy()!=null && orderDetail.getNum() < item.getLeastBuy()){
					result.setResultMessage("购买商品数量小于该商品的起买量");
					result.setResultCode("9002");
					return result;
				}
				
				//添加线下支付时间限制
//				if(orderInfo.getOrderType() == 2 && now.getHours() < 10){
//					result.setSuccess(false);
//					result.setResultCode("9002");
//					result.setResultMessage("线下支付时间必须为上午10点至下午20点");
//					return result;
//				}else if(orderInfo.getOrderType() == 2 && now.getHours() >=20){
//					result.setSuccess(false);
//					result.setResultCode("9002");
//					result.setResultMessage("线下支付时间必须为上午10点至下午20点");
//					return result;
//				}

				if(venderUserId == -1){
					venderUserId = item.getVenderUserId();
				}else{
					if(!venderUserId.equals(item.getVenderUserId())){
						venderUserId = 0;
					}
				}
				orderDetail.setItemTitle(item.getItemTitle());
				orderDetail.setItemId(item.getItemId());
				orderDetail.setItemImage(item.getItemImage());
				orderDetail.setItemName(item.getItemName());
				orderDetail.setVenderUserId(item.getVenderUserId());
				
				Sku sku = skuDao.selectBySkuId(orderDetail.getSkuId());
				if(sku == null){
					result.setResultMessage("sku编号为"+orderDetail.getSkuId()+"的商品不存在");
					result.setResultCode("8006");
					return result;
				}
				
				if(sku.getStock() < orderDetail.getNum()){
					result.setResultMessage("sku编号为"+orderDetail.getSkuId()+"的商品无货");
					result.setResultCode("9001");
					return result;
				}

                //一个SKU可能有多个促销，取价格最优者
                PromotionSkuQuery promotionSkuQuery = new PromotionSkuQuery();
                promotionSkuQuery.setSkuId(sku.getSkuId());
                promotionSkuQuery.setYn(1);
                List<PromotionSku> psList = promotionSkuDao.selectByCondition(promotionSkuQuery);
                PromotionSku promotionSku = null;
                if( null != psList && 0 < psList.size() ){
                    promotionSku = psList.get(0);   
                }
                for(PromotionSku ps :psList ){
                    if( ps.getDeductionPrice().compareTo( promotionSku.getDeductionPrice() ) ==1 ){
                        promotionSku = ps;
                    }
                }
                //如果带促销，计算带促销订单价格
				if(promotionSku != null && promotionSku.getDeductionPrice().compareTo(new BigDecimal(0)) > 0){
					PromotionInfo promotionInfo = promotionInfoDao.selectByPromotionId(promotionSku.getPromotionId());
					//判断是否有促销活动
					 if(promotionInfo != null
			                    && promotionInfo.getPromotionStatus()!=null && promotionInfo.getStartTime()!=null && promotionInfo.getEndTime() !=null
			                    && promotionInfo.getYn() == 1 && promotionInfo.getPromotionStatus() == 1
			                    && now.after(promotionInfo.getStartTime()) && now.before(promotionInfo.getEndTime())){
                            if(sku.getTbPrice().compareTo(promotionSku.getDeductionPrice())>0){
                                orderDetail.setPrice(sku.getTbPrice().subtract(promotionSku.getDeductionPrice()));//商品实际出售单价
                            }
						    promotionMap.put(orderDetail.getSkuId(), orderDetail.getPrice());// 优惠信息明细，需要再确认
					}
				}
				
				//如果商品无促销，该商品价格为商品单价
				if(orderDetail.getPrice() == null || orderDetail.getPrice().compareTo(new BigDecimal(0)) == 0){
					orderDetail.setPrice(sku.getTbPrice());//商品实际出售单价
				}
				
				orderDetail.setOriginalPrice(sku.getTbPrice());
				
				//该商品原价总价
				BigDecimal itemOrderMoney = sku.getTbPrice().multiply(new BigDecimal(orderDetail.getNum()));
				//计算订单总金额，订单总金额为原价，不带促销
				if(orderInfo.getOrderMoney() == null){
					orderInfo.setOrderMoney(itemOrderMoney);
				}else{
					orderInfo.setOrderMoney(orderInfo.getOrderMoney().add(itemOrderMoney));
				}
				
				//该商品优惠金额 = (商品原价-商品实际出售价格)*商品数量
				//如果商品没有优惠的话，商品原价减去商品出售价格的值为0.即没有优惠
				BigDecimal itemDiscountMoney = (sku.getTbPrice().subtract(orderDetail.getPrice())).multiply(new BigDecimal(orderDetail.getNum()));
				//计算订单总优惠金额
				if(orderInfo.getDiscountMoney() == null){
					orderInfo.setDiscountMoney(itemDiscountMoney);
				}else{
					orderInfo.setDiscountMoney(orderInfo.getDiscountMoney().add(itemDiscountMoney));
				}
				
				//该商品全额支付的订单金额
				BigDecimal itemOughtPayMoney = orderDetail.getPrice().multiply(new BigDecimal(orderDetail.getNum()));
				
				//计算订单总应付金额
				if(orderInfo.getOughtPayMoney() == null){
					orderInfo.setOughtPayMoney(itemOughtPayMoney);
				}else if(orderInfo.getOughtPayMoney() != null){
					orderInfo.setOughtPayMoney(orderInfo.getOughtPayMoney().add(itemOughtPayMoney));
				}
				
				//添加商品销售属性
				orderDetail.setSalesProperty(sku.getSalesProperty());
				orderDetail.setSalesPropertyName(sku.getSalesPropertyName());
				
				allNum = allNum + orderDetail.getNum();
			}
			
			// 订单价格信息
			BigDecimal orderMoney = orderInfo.getOrderMoney();
			BigDecimal oughtPayMoney = orderInfo.getOughtPayMoney();
			BigDecimal discountMoney = orderInfo.getDiscountMoney();
			
			// 计算优惠券
			final List<TbCoupon> couponList = new ArrayList<TbCoupon>();
			if(StringUtils.isNotEmpty(coupons)){
				String[] couponIds = coupons.split(",");
				for (String couponId : couponIds) {
					TbCoupon tbCoupon = tbCouponDao.selectByTbCouponId(Integer.parseInt(couponId));
					if(tbCoupon != null && tbCoupon.getCouponState() == 0){
						if(tbCoupon.getCouponType() == 2){
							tbCoupon.setCouponAmount(0);
						}
						oughtPayMoney = oughtPayMoney.subtract(new BigDecimal(tbCoupon.getCouponAmount()));
						discountMoney = discountMoney.add(new BigDecimal(tbCoupon.getCouponAmount()));

						couponList.add(tbCoupon);
					}
				}
			}
			if(new BigDecimal(0).compareTo(oughtPayMoney) > 0){
				oughtPayMoney = new BigDecimal(0.00);
			}
			
			// 组装订单信息
			orderInfo.setOrderId(null);
			orderInfo.setOrderMoney(orderMoney);
			orderInfo.setOughtPayMoney(oughtPayMoney);
			orderInfo.setDiscountMoney(discountMoney);
			orderInfo.setDiscountInfo(JsonUtils.writeValue(promotionMap));
			orderInfo.setOrderTime(now);
			orderInfo.setOrderStatus(0);
			orderInfo.setCreated(now);
			orderInfo.setFromWhere(orderInfo.getFromWhere());
			orderInfo.setVenderUserId(venderUserId);
			
			// 组装收货人信息
			final List<OrderConsignee> orderConsigneeList = new ArrayList<OrderConsignee>();
			OrderConsignee orderConsignee = new OrderConsignee();
			orderConsignee.setUserId(orderInfo.getUserId());
			orderConsignee.setBuyNum(allNum);
			orderConsignee.setBuyMoney(orderInfo.getOughtPayMoney());
			orderConsignee.setVenderUserId(orderInfo.getVenderUserId());
			orderConsignee.setConsigneeAddress(orderInfo.getConsigneeAddress());
			orderConsignee.setConsigneeCity(orderInfo.getConsigneeCity());
			orderConsignee.setConsigneeCounty(orderInfo.getConsigneeCounty());
			orderConsignee.setConsigneeMobile(orderInfo.getConsigneeMobile());
			orderConsignee.setConsigneeName(orderInfo.getConsigneeName());
			orderConsignee.setConsigneeProvince(orderInfo.getConsigneeProvince());
			orderConsignee.setCreated(orderInfo.getCreated());
			orderConsignee.setYN(1);
			orderConsigneeList.add(orderConsignee);
			
			new TransactionTemplate(transactionManager).execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus arg0) {
					// 添加订单信息
					Integer orderId = orderInfoDao.insert(orderInfo);
					orderInfo.setOrderId(orderId);
					for(int i=0;i<orderDetailList.size();i++){
						// 添加订单详情
						OrderDetail orderDetail = orderDetailList.get(i);
						orderDetail.setOrderId(orderId);
						orderDetail.setSubOrderId(orderId);
						orderDetailDao.insert(orderDetail);
						// 扣除Sku库存  
						Sku sku = new Sku();
						sku.setSkuId(orderDetail.getSkuId());
						sku.setStock(orderDetail.getNum());
						Integer delState = skuDao.delStock(sku);
						if(delState == null || delState == 0){
							//抛出异常，程序停止运行
							throw new RuntimeException("商品库存不足");
						}
						
						// 删除购物车信息
						CartInfoQuery cartInfoQuery = new CartInfoQuery();
						cartInfoQuery.setUserId(orderInfo.getUserId());
						cartInfoQuery.setSkuId(orderDetail.getSkuId());
						List<CartInfo> cartList = cartInfoDao.selectByCondition(cartInfoQuery);
						if(cartList != null && !cartList.isEmpty()){
							cartInfoDao.delete(cartList.get(0).getCartId());
						}
					}
					
					// 添加收货人信息
					for (OrderConsignee orderConsignee : orderConsigneeList) {
						orderConsignee.setOrderId(orderId);
						orderConsigneeDao.insert(orderConsignee);
					}
					
					// 添加订单使用优惠券绑定信息
					if(couponList != null && !couponList.isEmpty()){
						for (TbCoupon tbCoupon : couponList) {
							tbCoupon.setCouponState(3);
							tbCoupon.setOrderId(orderId);
							tbCouponDao.modify(tbCoupon);
						}
					}
					
					//设置收货人地址为默认地址
		            if( null != orderInfo.getConsigneeId() ){
		            	ConsigneeInfo consigneeInfo = new ConsigneeInfo();
	                    consigneeInfo.setConsigneeId(orderInfo.getConsigneeId());
	                    consigneeInfo.setDefaultConsigneeFlag(1);
	                    consigneeInfoDao.modify(consigneeInfo);
		            }
					
					EcUtils.setSuccessResult(result);
					result.setResult(orderId);
				}
			});
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
			//接收throw new RuntimeException("商品库存不足");回滚异常
			String errMsg = e.getMessage();
			//判断异常信息，条件满足返回商品库存不足
			if(errMsg.equals("商品库存不足")){
				result.setResultMessage(errMsg);
			}
		}
		
		return result;
	}
	
	/**
	 * 用户所属订单列表查看（细分）
	 */
	@Override
	public Result getOrderInfosByOrderInfoQuery(OrderInfoQuery orderInfoQuery) {
		Result result = new Result();
		
		try{
			int total = orderInfoDao.countByCondition(orderInfoQuery);
			if(total == 0){
				result.setResultCode("9003");
				result.setResultMessage("该用户订单列表不存在");
				return result;
			}
			int totalPage = total / orderInfoQuery.getPageSize() + 1;
			if(orderInfoQuery.getPageNo() > totalPage){
				orderInfoQuery.setPageNo(totalPage);
			}
		
			List<OrderInfo> list = orderInfoDao.selectByConditionForPage(orderInfoQuery);
			
			for(int i=0;i<list.size();i++){
				OrderInfo orderInfo = list.get(i);
				List<OrderDetail> odList = null;
	            if(orderInfo.getVenderUserId() == -1){
	            	odList = orderDetailDao.selectByOrderId(orderInfo.getOrderId());
				}else{
					odList = orderDetailDao.selectBySubOrderId(orderInfo.getOrderId());
				}
				orderInfo.setOrderDetails(odList);
				
				//获取收货人订单信息中的userID
				OrderConsigneeQuery orderConsigneeQuery = new OrderConsigneeQuery();
				orderConsigneeQuery.setUserId(orderInfo.getUserId());
				//根据userID查询出收货人订单信息
				List<OrderConsignee> orderConsigneesList = orderConsigneeDao.selectByCondition(orderConsigneeQuery);
				if(orderConsigneesList == null || orderConsigneesList.isEmpty()){
					result.setResultCode("9003");
					result.setResultMessage("该用户收货人地址为空");
					return result;
				}else{
					OrderConsignee orderConsignee = orderConsigneesList.get(0);
					Address address1 = addressDao.selectByAddressId(orderConsignee.getConsigneeProvince());
	                if(address1 != null){
	                	orderInfo.setConsigneeProvinceName(address1.getAddressName());
	                }
	                Address address2 = addressDao.selectByAddressId(orderConsignee.getConsigneeCity());
	                if(address2 != null){
	                	orderInfo.setConsigneeCityName(address2.getAddressName());
	                }
	                Address address3 = addressDao.selectByAddressId(orderConsignee.getConsigneeCounty());
	                if(address3 != null){
	                	orderInfo.setConsigneeCountyName(address3.getAddressName());
	                }
	                orderInfo.setConsigneeAddress(orderConsignee.getConsigneeAddress());
	                orderInfo.setConsigneeMobile(orderConsignee.getConsigneeMobile());
	                orderInfo.setConsigneeName(orderConsignee.getConsigneeName());
				}
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", total);
			map.put("totalPage", totalPage);
			map.put("curPage", orderInfoQuery.getPageNo());
			map.put("list", list);
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	/**
	 * 商家所属订单列表查看（细分）
	 */
	@Override
	public Result getOrderInfosByVenderUserId(OrderInfoQuery orderInfoQuery) {
		Result result = new Result();
		
		try{
			BusinessUserExt businessUserExt = businessUserExtDao.selectByUserId(orderInfoQuery.getUserId());
			orderInfoQuery.setVenderUserId(businessUserExt.getId());
			orderInfoQuery.setUserId(null);
			int total = orderInfoDao.countByConditionForPage(orderInfoQuery);
			if(total == 0){
				result.setResultCode("9003");
				result.setResultMessage("该用户订单列表不存在");
				return result;
			}
			
			int totalPage = total / orderInfoQuery.getPageSize() + 1;
			if(orderInfoQuery.getPageNo() > totalPage){
				orderInfoQuery.setPageNo(totalPage);
				result.setSuccess(false);
				result.setResultMessage("没有更多信息");
				return result;
			}
		
			List<OrderInfo> list = orderInfoDao.selectByVenderUserIdForPage(orderInfoQuery);
				for (OrderInfo orderInfo : list) {
					// 查询订单详情
					List<OrderDetail> odList = orderDetailDao.selectByOrderId(orderInfo.getOrderId());
					orderInfo.setOrderDetails(odList);
					
					OrderConsigneeQuery orderConsigneeQuery = new OrderConsigneeQuery();
					orderConsigneeQuery.setUserId(orderInfo.getUserId());
					//根据userID查询出收货人订单信息
					List<OrderConsignee> orderConsigneesList = orderConsigneeDao.selectByCondition(orderConsigneeQuery);
					if(orderConsigneesList == null || orderConsigneesList.isEmpty()){
						result.setResultCode("9003");
						result.setResultMessage("该用户收货人地址为空");
						return result;
					}else{
						OrderConsignee orderConsignee = orderConsigneesList.get(0);
						Address address1 = addressDao.selectByAddressId(orderConsignee.getConsigneeProvince());
		                if(address1 != null){
		                	orderInfo.setConsigneeProvinceName(address1.getAddressName());
		                }
		                Address address2 = addressDao.selectByAddressId(orderConsignee.getConsigneeCity());
		                if(address2 != null){
		                	orderInfo.setConsigneeCityName(address2.getAddressName());
		                }
		                Address address3 = addressDao.selectByAddressId(orderConsignee.getConsigneeCounty());
		                if(address3 != null){
		                	orderInfo.setConsigneeCountyName(address3.getAddressName());
		                }
		                orderInfo.setConsigneeId(orderConsignee.getId());
		                orderInfo.setConsigneeAddress(orderConsignee.getConsigneeAddress());
		                orderInfo.setConsigneeMobile(orderConsignee.getConsigneeMobile());
		                orderInfo.setConsigneeName(orderConsignee.getConsigneeName());
					}
				}
			
			
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", total);
			map.put("totalPage", totalPage);
			map.put("curPage", orderInfoQuery.getPageNo());
			map.put("list", list);
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	/** 
	 * 商家根据订单号模糊查询 
	 */
	public Result searchByOrderId(Integer orderId,Integer userId){
		Result result = new Result();
		try {
			
			BusinessUserExt businessUserExt = businessUserExtDao.selectByUserId(userId);
			if(businessUserExt == null){
				result.setSuccess(false);
				result.setResultMessage("没有商家信息");
				return result;
			}
			
			OrderInfoQuery orderInfoQuery = new OrderInfoQuery();
			orderInfoQuery.setVenderUserId(businessUserExt.getId());
			orderInfoQuery.setOrderId(orderId);
			
			List<OrderInfo> list = orderInfoDao.selectByCondition(orderInfoQuery);
			if(list == null || list.isEmpty()){
				result.setSuccess(false);
				result.setResultMessage("订单列表不存在");
				return result;
			}
			
			for (OrderInfo orderInfo : list) {
				List<OrderDetail> odList = orderDetailDao.selectByOrderId(orderInfo.getOrderId());
				for (OrderDetail orderDetail : odList) {
					orderInfo.setNum(orderDetail.getNum());
				}
				BusinessUserExt businessUserExts = businessUserExtDao.selectById(orderInfo.getVenderUserId());
				if(businessUserExts != null){
					orderInfo.setMobile(businessUserExts.getMobile());
				}
				orderInfo.setOrderDetails(odList);
				OrderConsigneeQuery orderConsigneeQuery = new OrderConsigneeQuery();
				orderConsigneeQuery.setOrderId(orderInfo.getOrderId());
				List<OrderConsignee> orderConsigneesList = orderConsigneeDao.selectByCondition(orderConsigneeQuery);
				if(orderConsigneesList == null || orderConsigneesList.isEmpty()){
					result.setResultCode("9003");
					result.setResultMessage("该用户收货人地址为空");
					return result;
				}else{
					OrderConsignee orderConsignee = orderConsigneesList.get(0);
					Address address1 = addressDao.selectByAddressId(orderConsignee.getConsigneeProvince());
	                if(address1 != null){
	                	orderInfo.setConsigneeProvinceName(address1.getAddressName());
	                }
	                Address address2 = addressDao.selectByAddressId(orderConsignee.getConsigneeCity());
	                if(address2 != null){
	                	orderInfo.setConsigneeCityName(address2.getAddressName());
	                }
	                Address address3 = addressDao.selectByAddressId(orderConsignee.getConsigneeCounty());
	                if(address3 != null){
	                	orderInfo.setConsigneeCountyName(address3.getAddressName());
	                }
	                orderInfo.setConsigneeId(orderConsignee.getId());
	                orderInfo.setConsigneeAddress(orderConsignee.getConsigneeAddress());
	                orderInfo.setConsigneeMobile(orderConsignee.getConsigneeMobile());
	                orderInfo.setConsigneeName(orderConsignee.getConsigneeName());
				}
				
			}
			
			
			result.setResult(list);
			EcUtils.setSuccessResult(result);
		} catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	/**
	 * 根据订单号和用户id获取订单详细信息
	 */
	@Override
	public Result getOrderInfoByOrderIdAndUserId(Integer orderId, Integer userId) {
		Result result = new Result();
		try{
			OrderInfoQuery orderInfoQuery = new OrderInfoQuery();
			orderInfoQuery.setUserId(userId);
			orderInfoQuery.setOrderId(orderId);
			
			List<OrderInfo> list = this.orderInfoDao.selectByCondition(orderInfoQuery);
			
			if(this == null || list.size() == 0){
				result.setResultCode("9003");
				result.setResultMessage("该用户订单列表不存在");
				return result;
			}
			
			Integer num = 0;
			String mobile = null;
			OrderInfo orderInfo = list.get(0);
			
			//获取收货人订单信息中的userID和orderID
			OrderConsigneeQuery orderConsigneeQuery = new OrderConsigneeQuery();
			orderConsigneeQuery.setUserId(userId);
			orderConsigneeQuery.setOrderId(orderId);
			//根据orderID和userID查询出收货人订单信息
			List<OrderConsignee> orderConsigneesList = orderConsigneeDao.selectByCondition(orderConsigneeQuery);
			OrderConsignee orderConsignee = orderConsigneesList.get(0);
			
			Address address1 = addressDao.selectByAddressId(orderConsignee.getConsigneeProvince());
            if(address1 != null){
            	orderInfo.setConsigneeProvinceName(address1.getAddressName());
            }
            Address address2 = addressDao.selectByAddressId(orderConsignee.getConsigneeCity());
            if(address2 != null){
            	orderInfo.setConsigneeCityName(address2.getAddressName());
            }
            Address address3 = addressDao.selectByAddressId(orderConsignee.getConsigneeCounty());
            if(address3 != null){
            	orderInfo.setConsigneeCountyName(address3.getAddressName());
            }
            orderInfo.setConsigneeAddress(orderConsignee.getConsigneeAddress());
            orderInfo.setConsigneeMobile(orderConsignee.getConsigneeMobile());
            orderInfo.setConsigneeName(orderConsignee.getConsigneeName());
			
            List<OrderDetail> odList = null;
            odList = orderDetailDao.selectByOrderId(orderInfo.getOrderId());
			
			for (OrderDetail orderDetail : odList) {
				num = num + orderDetail.getNum();
			}
			orderInfo.setOrderDetails(odList);
			
			BusinessUserExt businessUserExt = businessUserExtDao.selectById(orderInfo.getVenderUserId());
			if(businessUserExt != null){
				mobile = businessUserExt.getMobile();
			}
			
			
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			// 添加订单信息
			resultMap.put("orderInfo", orderInfo);
			// 添加订单商品数量
			resultMap.put("num", num);
			// 添加订单支付时间
			resultMap.put("payTime", orderInfoQuery.getPayTime());
			// 添加商家手机
			resultMap.put("mobile", mobile);
			orderInfo.setMobile(mobile);
			orderInfo.setPayTime(orderInfoQuery.getPayTime());
			orderInfo.setNum(num);
			
			result.setResult(orderInfo);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	/**
	 * 商家确认收款
	 * @param orderId
	 * @return
	 */
	@Override
	public Result confirmGetPrice(Integer orderId, Integer venderUserId) {
		Result result = new Result();
		try{
			OrderInfo orderInfo = this.orderInfoDao.selectByOrderId(orderId);
			if(orderInfo == null){
				result.setResultCode("9004");
				result.setResultMessage("该订单不存在");
				return result;
			}
			orderInfo.setOrderStatus(2);//收款确认
			orderInfoDao.modify(orderInfo);
			
			result.setResult(true);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	/**
	 *  商家确认尾款
	 */
	@Override
	public Result confirmGetLastPrice(Integer orderId, Integer venderUserId) {
		Result result = new Result();
		try{
			OrderInfo orderInfo = this.orderInfoDao.selectByOrderId(orderId);
			if(orderInfo == null){
				result.setResultCode("9004");
				result.setResultMessage("该订单不存在");
				return result;
			}
			orderInfo.setOrderStatus(4);//尾款收款确认
			orderInfoDao.modify(orderInfo);
			
			result.setResult(true);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	/**
	 * 商家发货确认并补录配送信息
	 * @param orderId
	 * @return
	 */
	@Override
	public Result confirmSendOut(final Integer orderId ,final Integer userId ,final String express) {
		Result result = new Result();
		try{
			
			final OrderInfo orderInfo = this.orderInfoDao.selectByOrderId(orderId);
			if(orderInfo == null){
				result.setResultCode("9004");
				result.setResultMessage("该订单不存在");
				return result;
			}
			//组装订单信息
			orderInfo.setOrderStatus(5);// 商家发货
			orderInfo.setSendOutTime(new Date());
			
			final List<OrderConsignee> ocList = orderConsigneeDao.selectByOrderId(orderId);
			
			if(ocList.isEmpty() || ocList == null){
				result.setResultCode("9004");
				result.setResultMessage("该收货人不存在");
				return result;
			}
			
			new TransactionTemplate(transactionManager).execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus arg0) {
					for (OrderConsignee orderConsignee : ocList) {
						orderConsignee.setExpress(express);
						orderConsigneeDao.updateExpress(orderConsignee);
					}
					orderInfoDao.modify(orderInfo);
				  
				}
				
			});
			
			result.setResult(true);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	/**
	 * 用户收货确认
	 * @param orderId
	 * @return
	 */
	@Override
	public Result confirmGetGoods(Integer orderId, Integer userId) {
		Result result = new Result();
		try{
			OrderInfo orderInfo = this.orderInfoDao.selectByOrderId(orderId);
			if(orderInfo == null){
				result.setResultCode("9004");
				result.setResultMessage("该订单不存在");
				return result;
			}
			
			//组装信息
			orderInfo.setOrderStatus(50);
			orderInfo.setFinishTime(new Date());
			
			orderInfoDao.modify(orderInfo);
			
			result.setResult(true);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	/**
	 * 商家订单完成确认
	 * @param orderId
	 * @return
	 */
	@Override
	public Result orderSuccess(Integer orderId) {
		Result result = new Result();
		try{
			OrderInfo orderInfo = this.orderInfoDao.selectByOrderId(orderId);
			if(orderInfo == null){
				result.setResultCode("9004");
				result.setResultMessage("该订单不存在");
				return result;
			}
			orderInfo.setOrderStatus(50);// 订单完成
			orderInfo.setFinishTime(new Date());
			orderInfoDao.modify(orderInfo);
			
			result.setResult(true);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	/**
	 * 商家订单锁定
	 * @param orderId
	 * @param lockReason
	 * @return
	 */
	@Override
	public Result lockOrder(Integer orderId, String lockReason) {
		Result result = new Result();
		try{
			OrderInfo orderInfo = this.orderInfoDao.selectByOrderId(orderId);
			if(orderInfo == null){
				result.setResultCode("9004");
				result.setResultMessage("该订单不存在");
				return result;
			}
			orderInfo.setLockStatus(1);
			orderInfo.setLockReason(lockReason);
			orderInfoDao.modify(orderInfo);
			
			result.setResult(true);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	/**
	 * 商家订单解锁
	 * @param orderId
	 * @return
	 */
	@Override
	public Result unlockOrder(Integer orderId) {
		Result result = new Result();
		try{
			OrderInfo orderInfo = this.orderInfoDao.selectByOrderId(orderId);
			if(orderInfo == null){
				result.setResultCode("9004");
				result.setResultMessage("该订单不存在");
				return result;
			}
			orderInfo.setLockStatus(0);
			orderInfoDao.modify(orderInfo);
			result.setResult(true);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	/**
	 * 根据订单号和商家id获取订单详细信息
	 */
	@Override
	public Result getOrderInfoByOrderIdAndVenderUserId(Integer orderId,Integer userId) {
		Result result = new Result();
		try{
			OrderInfoQuery orderInfoQuery = new OrderInfoQuery();
			BusinessUserExt businessUserExt = businessUserExtDao.selectByUserId(userId);
			orderInfoQuery.setVenderUserId(businessUserExt.getId());
			orderInfoQuery.setOrderId(orderId);
			
			List<OrderInfo> list = this.orderInfoDao.selectByCondition(orderInfoQuery);
			
			if(this == null || list.size() == 0){
				result.setResultCode("9003");
				result.setResultMessage("该用户订单列表不存在");
				return result;
			}
			
			Integer num = 0;
			String mobile = null;
			OrderInfo orderInfo = list.get(0);
			
			//获取收货人订单信息中的userID和orderID
			OrderConsigneeQuery orderConsigneeQuery = new OrderConsigneeQuery();
			orderConsigneeQuery.setVenderUserId(businessUserExt.getId());
			orderConsigneeQuery.setOrderId(orderInfoQuery.getOrderId());
			//根据orderID和userID查询出收货人订单信息
			List<OrderConsignee> orderConsigneesList = orderConsigneeDao.selectByCondition(orderConsigneeQuery);
			OrderConsignee orderConsignee = orderConsigneesList.get(0);
			
			Address address1 = addressDao.selectByAddressId(orderConsignee.getConsigneeProvince());
            if(address1 != null){
            	orderInfo.setConsigneeProvinceName(address1.getAddressName());
            }
            Address address2 = addressDao.selectByAddressId(orderConsignee.getConsigneeCity());
            if(address2 != null){
            	orderInfo.setConsigneeCityName(address2.getAddressName());
            }
            Address address3 = addressDao.selectByAddressId(orderConsignee.getConsigneeCounty());
            if(address3 != null){
            	orderInfo.setConsigneeCountyName(address3.getAddressName());
            }
            orderInfo.setConsigneeAddress(orderConsignee.getConsigneeAddress());
            orderInfo.setConsigneeMobile(orderConsignee.getConsigneeMobile());
            orderInfo.setConsigneeName(orderConsignee.getConsigneeName());
			
            List<OrderDetail> odList = null;
            odList = orderDetailDao.selectByOrderId(orderInfo.getOrderId());
			
			for (OrderDetail orderDetail : odList) {
				num = num + orderDetail.getNum();
			}
			orderInfo.setOrderDetails(odList);
			
			BusinessUserExt businessUserExts = businessUserExtDao.selectById(orderInfo.getVenderUserId());
			if(businessUserExts != null){
				mobile = businessUserExts.getMobile();
			}
			
			orderInfo.setMobile(mobile);
			orderInfo.setPayTime(orderInfo.getPayTime());
			orderInfo.setNum(num);
			
			result.setResult(orderInfo);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	/**
	 * 根据订单号修改支付金额
	 */
	@Override
	public Result UpdateOderInfoPriceByOrderId(final OrderInfoQuery orderInfoQuery){
		Result result = new Result();
		try {
			
			BusinessUserExt businessUserExt = businessUserExtDao.selectByUserId(orderInfoQuery.getUserId());
			orderInfoQuery.setVenderUserId(businessUserExt.getId());
			orderInfoQuery.setUserId(null);
			
			final OrderInfo orderInfo = orderInfoDao.selectByOrderId(orderInfoQuery.getOrderId());
			
			//判断修改金额等于应付金额
			if(orderInfoQuery.getOughtPayMoney().compareTo(orderInfo.getOughtPayMoney()) ==0){
				result.setSuccess(true);
				result.setResult("修改价格与订单价格相同,请重新改价");
				return result;
			}
			//判断应付金额小于订单金额
			if(orderInfoQuery.getOughtPayMoney().compareTo(orderInfo.getOrderMoney()) < 0){
				orderInfo.setDiscountMoney(orderInfo.getOrderMoney().subtract(orderInfoQuery.getOughtPayMoney()));
				orderInfo.setOughtPayMoney(orderInfoQuery.getOughtPayMoney());
				//判断应付金额大于订单金额
			}else if(orderInfoQuery.getOughtPayMoney().compareTo(orderInfo.getOrderMoney()) > 0){
				orderInfo.setDiscountMoney(orderInfo.getOrderMoney().subtract(orderInfoQuery.getOughtPayMoney()));
				orderInfo.setOughtPayMoney(orderInfoQuery.getOughtPayMoney());
				
			}
			final List<OrderDetail> odList = orderDetailDao.selectByOrderId(orderInfoQuery.getOrderId());
			final List<OrderConsignee> ocList = orderConsigneeDao.selectByOrderId(orderInfoQuery.getOrderId());
			
			new TransactionTemplate(transactionManager).execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				  Integer orderId =	orderInfoDao.insert(orderInfo);
				  orderInfo.setOrderId(orderId);
				  orderInfoDao.delPriceByOrderId(orderInfoQuery.getOrderId());
				  for (OrderDetail orderDetail : odList) {
					  orderDetail.setOrderId(orderId);
					  orderDetailDao.updateOderIdByOrderId(orderDetail);
					}
				  for (OrderConsignee orderConsignee : ocList) {
					orderConsignee.setOrderId(orderId);
					orderConsigneeDao.updateOderIdByOrderId(orderConsignee);
				  	}
				  
				}
				
			});
			
			
			if(orderInfo.getOrderId() == orderInfoQuery.getOrderId()){
				result.setSuccess(false);
				result.setResult("价格修改失败");
			}else{
				result.setSuccess(true);
				result.setResult(orderInfo.getOrderId());
			}
			
			EcUtils.setSuccessResult(result);
		} catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	/**
	 * 此接口未实现
	 */
	@Override
	public Result createTradeNo(Integer orderId, Integer userId) {
		Result result = new Result();
		try{
			
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	

    @Override
	public Result getOrderStatusByOrderIdAndUserId(Integer orderId, Integer userId) {
		Result result = new Result();
		try{
			OrderInfoQuery orderInfoQuery = new OrderInfoQuery();
			orderInfoQuery.setUserId(userId);
			orderInfoQuery.setOrderId(orderId);

			List<OrderInfo> list = this.orderInfoDao.selectByCondition(orderInfoQuery);

			if(this == null || list.size() == 0){
				result.setResultCode("9003");
				result.setResultMessage("该用户订单列表不存在");
				return result;
			}
			OrderInfo orderInfo = list.get(0);
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("tradeNo", orderInfo.getTradeNo());
			resultMap.put("orderStatus", orderInfo.getOrderStatus());
            result.setResult(resultMap);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
    
    
    @Override
	public void payWX(Integer orderId, String tradeNo, BigDecimal price,String timeEnd,Integer orderFlag) {
    	final OrderInfo orderInfo = orderInfoDao.selectByOrderId(orderId);
    	orderInfo.setOrderStatus(1); //修改订单状态为已支付
    	orderInfo.setPayMoney(price);
    	orderInfo.setWxAppTradeNo(tradeNo);
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");                
    	Date payTime = null;
    	try {
			payTime = sdf.parse(timeEnd);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	orderInfo.setPayTime(payTime);
    	
    	PaymentInfoQuery paymentInfoQuery = new PaymentInfoQuery();
		paymentInfoQuery.setOrderId(orderId);
		List<PaymentInfo> paymentInfoList = paymentInfoDao.selectByCondition(paymentInfoQuery);
		if(paymentInfoList == null || paymentInfoList.isEmpty()){ //判断支付是否成功worker 24小时扫微信支付接口
    	
			//组装支付信息
			final PaymentInfo paymentInfo = new PaymentInfo();
			paymentInfo.setOrderId(orderId);
			paymentInfo.setOrderPayType(1);//（1-定金OR全款支付  2-尾款支付）
			paymentInfo.setPaymentMode(orderFlag); //（支付方式（1、微信APP支付   2、微信H5支付  3、线下支付  4、物流代收） ）
			paymentInfo.setPaymentInfoType(2);//(1、支付信息   2、支付成功确认信息)
			paymentInfo.setPaymentNumber(tradeNo);
			paymentInfo.setPaymentMoney(price);
			paymentInfo.setBusiPartner("109001");
			timeEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(payTime);
			paymentInfo.setDtOrder(timeEnd);
			
			new TransactionTemplate(transactionManager).execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus arg0) {
					orderInfoDao.modify(orderInfo);
					paymentInfoDao.insert(paymentInfo);
				}
			});
		}
	}
    
    @Override
	public OrderInfo seleclById(Integer orderId) {
		return orderInfoDao.selectByOrderId(orderId);
	}
    
    /**
     * 根据orderID修改价格
     */
    @Override
	public void modify(OrderInfo orderInfo) {
    	orderInfoDao.modify(orderInfo);
	}
    
    @Override
	public Result checkOrderId(Integer orderId) {
    	Result result = new Result();
    	
    	OrderInfo orderInfo = orderInfoDao.selectByOrderId(orderId);
    	PaymentInfoQuery paymentInfoQuery = new PaymentInfoQuery();
    	paymentInfoQuery.setOrderId(orderId);
    	List<PaymentInfo> PaymentInfoList = paymentInfoDao.selectByCondition(paymentInfoQuery);
    
    	
    	if(orderInfo != null&&orderInfo.getOrderStatus() == 1 && orderInfo.getWxAppTradeNo() != null && (orderInfo.getPayMoney().compareTo(orderInfo.getOughtPayMoney())==0
    			&& PaymentInfoList!=null && PaymentInfoList.size()>0)){
    		result.setResult("success");
    		result.setResultMessage("支付已完成！");
    		result.setSuccess(true);
    	}else{
    		result.setResult("false");
    		result.setResultMessage("false");
    		result.setSuccess(false);
    	}
		return result;
	}
    
    @Override
	public List<OrderInfo> selectByCondition(OrderInfoQuery orderInfoQuery) {
		return orderInfoDao.selectByCondition(orderInfoQuery);
	}
    
    
    
	public void setOrderInfoDao(OrderInfoDao orderInfoDao) {
		this.orderInfoDao = orderInfoDao;
	}

	public void setItemDao(ItemDao itemDao) {
		this.itemDao = itemDao;
	}

	public void setOrderDetailDao(OrderDetailDao orderDetailDao) {
		this.orderDetailDao = orderDetailDao;
	}

	public void setSkuDao(SkuDao skuDao) {
		this.skuDao = skuDao;
	}

	public void setPromotionSkuDao(PromotionSkuDao promotionSkuDao) {
		this.promotionSkuDao = promotionSkuDao;
	}

	public void setPromotionInfoDao(PromotionInfoDao promotionInfoDao) {
		this.promotionInfoDao = promotionInfoDao;
	}

    public void setConsigneeInfoDao(ConsigneeInfoDao consigneeInfoDao) {
        this.consigneeInfoDao = consigneeInfoDao;
    }

    public void setTransactionManager(
			DataSourceTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

    public void setAddressDao(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

	public void setTbCouponDao(TbCouponDao tbCouponDao) {
		this.tbCouponDao = tbCouponDao;
	}

	public void setCartInfoDao(CartInfoDao cartInfoDao) {
		this.cartInfoDao = cartInfoDao;
	}

	public void setBusinessUserExtDao(BusinessUserExtDao businessUserExtDao) {
		this.businessUserExtDao = businessUserExtDao;
	}

	public void setPaymentInfoDao(PaymentInfoDao paymentInfoDao) {
		this.paymentInfoDao = paymentInfoDao;
	}

	public void setOrderConsigneeDao(OrderConsigneeDao orderConsigneeDao) {
		this.orderConsigneeDao = orderConsigneeDao;
	}
}
