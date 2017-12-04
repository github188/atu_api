package com.atu.api.service.impl;

import java.util.LinkedHashMap;
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

import com.atu.api.dao.OrderInfoDao;
import com.atu.api.dao.PaymentBindbankcardDao;
import com.atu.api.dao.PaymentInfoDao;
import com.atu.api.dao.UmpInfoDao;
import com.atu.api.domain.OrderInfo;
import com.atu.api.domain.PaymentBindbankcard;
import com.atu.api.domain.PaymentInfo;
import com.atu.api.domain.query.OrderInfoQuery;
import com.atu.api.domain.query.PaymentInfoQuery;
import com.atu.api.service.PaymentInfoService;
import com.atu.api.service.result.Result;
import com.atu.api.service.utils.EcUtils;

@Service(value="paymentInfoService")
public class PaymentInfoServiceImpl implements PaymentInfoService {
	private static final Logger log = LoggerFactory.getLogger(PaymentInfoServiceImpl.class);

	private PaymentInfoDao paymentInfoDao;
	private OrderInfoDao orderInfoDao;
	private UmpInfoDao umpInfoDao;
	private PaymentBindbankcardDao paymentBindbankcardDao;
	private DataSourceTransactionManager transactionManager;
	
	
	@Override
	public Result addPaymentInfo(final PaymentInfo paymentInfo) {
		final Result result = new Result();
		try{
			final OrderInfo orderInfo = orderInfoDao.selectByOrderId(paymentInfo.getOrderId());
			if(orderInfo == null){
				result.setResultCode("9004");
				result.setResultMessage("该订单不存在");
				return result;
			}
			PaymentInfoQuery query = new PaymentInfoQuery();
			query.setOrderPayType(paymentInfo.getOrderPayType());
			query.setPaymentInfoType(2);//支付成功标示，如果支付成功了，则不让再支付了。
			query.setOrderId(paymentInfo.getOrderId());
			List<PaymentInfo> list = paymentInfoDao.selectByCondition(query);
			
			if(list != null && list.size() > 0){
				result.setResultCode("3006");
				result.setResultMessage("该订单已经被支付，不能重复支付");
				return result;
			}
			
			new TransactionTemplate(transactionManager).execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus arg0) {
					if(paymentInfoDao.insert(paymentInfo) <= 0){
						throw new RuntimeException("添加支付信息失败");
					}
					
					//修改订单实际支付金额
					OrderInfo dbOrderInfo = new OrderInfo();
					dbOrderInfo.setVenderUserId(orderInfo.getVenderUserId());
					dbOrderInfo.setOrderId(orderInfo.getOrderId());
					dbOrderInfo.setPayMoney(paymentInfo.getPaymentMoney());
					if(orderInfoDao.modifyPayMoney(dbOrderInfo) <= 0){
						throw new RuntimeException("修改订单应付金额失败");
					}
					
					result.setResult(true);
					EcUtils.setSuccessResult(result);
				}
			});
			
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	/**
	 * 转账账户信息
	 */
	@Override
	public Result accountInfo(){
		Result result = new Result();
		try {
			String openAccount ="北京天宝流通农业科技有限公司";
			String openBank = "中国民生银行";
			String bankAccount = "695151836";
			String openBranch = "中国民生银行北京媒体村支行";
			String remark = "转账须知：线下支付的用户在转账时，请在备注栏填写订单编号，通知手机为13801390436（暂只能通过银行柜台办理或银行手机端及PC端网银，以防转账失败），短信提示后财务会及时查账并处理您的订单，感谢您的配合，谢谢！";
			
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("openAccount", openAccount);
			map.put("openBank", openBank);
			map.put("bankAccount", bankAccount);
			map.put("openBranch", openBranch);
			map.put("remark", remark);
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		} catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		
		return result;
	}
	

	@Override
	public Result getPaymentInfos(PaymentInfoQuery paymentInfoQuery) {
		Result result = new Result();
		try{
			List<PaymentInfo> list = paymentInfoDao.selectByCondition(paymentInfoQuery);
			if(list == null || list.size() == 0){
				result.setResultCode("3004");
				result.setResultMessage("支付信息不存在");
				return result;
			}
			result.setResult(list);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	
	@Override
	public Result sendCode(PaymentBindbankcard paymentBindbankcard,PaymentInfoQuery paymentInfoQuery) {
		Result result = new Result();
		try{
			OrderInfoQuery orderInfoQuery = new OrderInfoQuery();
			orderInfoQuery.setUserId(paymentBindbankcard.getMer_cust_id());
			orderInfoQuery.setOrderId(paymentInfoQuery.getOrderId());
			List<OrderInfo> list = this.orderInfoDao.selectByCondition(orderInfoQuery);
			List<PaymentBindbankcard>  list2 = this.paymentBindbankcardDao.selectByCondition(paymentBindbankcard);
			
			if(this == null || list.size() == 0){
				result.setResultCode("9003");
				result.setResultMessage("该用户订单列表不存在");
				return result;
			}
			
			final OrderInfo orderInfo_temp = list.get(0);
			
			if(StringUtils.isEmpty(orderInfo_temp.getTradeNo()) || orderInfo_temp.getTradeNo() == null){
				result.setResultCode("9003");
				result.setResultMessage("联动优势平台交易支付号不存在");
				return result;
			}
			
			if(this == null || list2 == null || list2.size() == 0){
				result.setResultCode("9003");
				result.setResultMessage("绑定银行卡信息不存在");
				return result;
			}
			
			final PaymentBindbankcard paymentBindbankcard_temp = list2.get(0);
			
			if(StringUtils.isEmpty(paymentBindbankcard_temp.getUsr_pay_agreement_id()) || paymentBindbankcard_temp.getUsr_pay_agreement_id() == null){
				result.setResultCode("9003");
				result.setResultMessage("联动优势平台支付协议号不存在");
				return result;
			}

		}catch (Exception e) {
			log.error("发送短信验证码异常", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	
	@Override
	public Result submit(PaymentBindbankcard paymentBindbankcard,PaymentInfoQuery paymentInfoQuery,Integer verify_code) {
		Result result = new Result();
		try{
			
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}


	public void setPaymentInfoDao(PaymentInfoDao paymentInfoDao) {
		this.paymentInfoDao = paymentInfoDao;
	}

	public void setOrderInfoDao(OrderInfoDao orderInfoDao) {
		this.orderInfoDao = orderInfoDao;
	}

	public void setTransactionManager(
			DataSourceTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	
	public PaymentBindbankcardDao getPaymentBindbankcardDao() {
		return paymentBindbankcardDao;
	}

	public void setPaymentBindbankcardDao(
			PaymentBindbankcardDao paymentBindbankcardDao) {
		this.paymentBindbankcardDao = paymentBindbankcardDao;
	}

	public UmpInfoDao getUmpInfoDao() {
		return umpInfoDao;
	}

	public void setUmpInfoDao(UmpInfoDao umpInfoDao) {
		this.umpInfoDao = umpInfoDao;
	}

	public PaymentInfoDao getPaymentInfoDao() {
		return paymentInfoDao;
	}

	public OrderInfoDao getOrderInfoDao() {
		return orderInfoDao;
	}

	public DataSourceTransactionManager getTransactionManager() {
		return transactionManager;
	}
	
}
