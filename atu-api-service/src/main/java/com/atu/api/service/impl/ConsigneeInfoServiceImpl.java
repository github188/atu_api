package com.atu.api.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.atu.api.dao.AddressDao;
import com.atu.api.dao.ConsigneeInfoDao;
import com.atu.api.domain.Address;
import com.atu.api.domain.ConsigneeInfo;
import com.atu.api.domain.query.ConsigneeInfoQuery;
import com.atu.api.service.ConsigneeInfoService;
import com.atu.api.service.result.Result;
import com.atu.api.service.utils.EcUtils;

@Service(value="consigneeInfoService")
public class ConsigneeInfoServiceImpl implements ConsigneeInfoService {
	private static final Logger log = LoggerFactory.getLogger(ConsigneeInfoServiceImpl.class);
	private DataSourceTransactionManager transactionManager;
	private ConsigneeInfoDao consigneeInfoDao;
    private AddressDao addressDao;
	
	@Override
	public Result getConsigneeInfosByUserId(Integer userId) {
		Result result = new Result();
		try{
			ConsigneeInfoQuery consigneeInfoQuery = new ConsigneeInfoQuery();
			consigneeInfoQuery.setUserId(userId);
			List<ConsigneeInfo> list = consigneeInfoDao.selectByCondition(consigneeInfoQuery);
			if(list == null || list.size() == 0){
				result.setResultCode("9005");
				result.setResultMessage("收货人列表不存在");
				return result;
			}
            for(ConsigneeInfo ci : list){
                if( null != ci.getConsigneeProvince() ){
                    Address address1 = addressDao.selectByAddressId(ci.getConsigneeProvince());
				    if(address1 != null){
					    ci.setConsigneeProvinceName(address1.getAddressName());
				    }
                }
                if( null != ci.getConsigneeCity() ){
                    Address address1 = addressDao.selectByAddressId(ci.getConsigneeCity());
				    if(address1 != null){
					    ci.setConsigneeCityName(address1.getAddressName());
				    }
                }
                if( null != ci.getConsigneeCounty() ){
                    Address address1 = addressDao.selectByAddressId(ci.getConsigneeCounty());
				    if(address1 != null){
					    ci.setConsigneeCountyName(address1.getAddressName());
				    }
                }
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
	public Result getConsigneeInfosByDefault(Integer userId) {
		Result result = new Result();
		
		try{
			ConsigneeInfo consigneeInfo = consigneeInfoDao.selectByUserIdForDefault(userId);
			if(consigneeInfo == null){
				result.setResultCode("9005");
				result.setResultMessage("默认收货人信息不存在");
				return result;
			}
			
			if( null != consigneeInfo.getConsigneeProvince() ){
                Address address1 = addressDao.selectByAddressId(consigneeInfo.getConsigneeProvince());
			    if(address1 != null){
			    	consigneeInfo.setConsigneeProvinceName(address1.getAddressName());
			    }
            }
            if( null != consigneeInfo.getConsigneeCity() ){
                Address address1 = addressDao.selectByAddressId(consigneeInfo.getConsigneeCity());
			    if(address1 != null){
			    	consigneeInfo.setConsigneeCityName(address1.getAddressName());
			    }
            }
            if( null != consigneeInfo.getConsigneeCounty() ){
                Address address1 = addressDao.selectByAddressId(consigneeInfo.getConsigneeCounty());
			    if(address1 != null){
			    	consigneeInfo.setConsigneeCountyName(address1.getAddressName());
			    }
            }
			result.setResult(consigneeInfo);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	@Override
	public Result addConsigneeInfo(final ConsigneeInfo consigneeInfo) {
		Result result = new Result();
		try{
			new TransactionTemplate(transactionManager).execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus arg0) {
					ConsigneeInfo consigneeInfos = consigneeInfoDao.selectByUserIdForDefault(consigneeInfo.getUserId());
					if(consigneeInfos != null){
						consigneeInfos.setDefaultConsigneeFlag(0);
						consigneeInfoDao.modify(consigneeInfos);
					}
					
					consigneeInfo.setDefaultConsigneeFlag(1);
					consigneeInfoDao.insert(consigneeInfo);
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

	@Override
	public Result updateConsigneeInfo(ConsigneeInfo consigneeInfo) {
		Result result = new Result();
		try{
			ConsigneeInfo ci = consigneeInfoDao.selectByConsigneeId(consigneeInfo.getConsigneeId());
			if(ci == null){
				result.setResultCode("9005");
				result.setResultMessage("收货人列表不存在");
				return result;
			}
			consigneeInfoDao.modify(consigneeInfo);
			result.setResult(true);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	@Override
	public Result delConsigneeInfo(Integer consigneeId) {
		Result result = new Result();
		try{
			final ConsigneeInfo consigneeInfo = consigneeInfoDao.selectByConsigneeId(consigneeId);
			if(consigneeInfo == null){
				result.setResultCode("9005");
				result.setResultMessage("收货人列表不存在");
				return result;
			}
			
			new TransactionTemplate(transactionManager).execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus arg0) {
					// 删除地址
					consigneeInfoDao.delConsigneeInfo(consigneeInfo.getConsigneeId());
					// 如果删除的为默认地址，取最近的地址更新为默认地址
					if(consigneeInfo.getDefaultConsigneeFlag() == 1){
						ConsigneeInfo consigneeInfos = consigneeInfoDao.selectByUserIdForLastUse(consigneeInfo.getUserId());
						consigneeInfos.setDefaultConsigneeFlag(1);
						consigneeInfoDao.modify(consigneeInfos);
					}
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
	 * 选择默认收货地址
	 */
	@Override
	public Result choiceConsigneeInfo(final ConsigneeInfo consigneeInfo){
		Result result = new Result();

		try{
			new TransactionTemplate(transactionManager).execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus arg0) {
					ConsigneeInfo consigneeInfos = consigneeInfoDao.selectByUserIdForDefault(consigneeInfo.getUserId());
					
					if(consigneeInfos.getConsigneeId() != consigneeInfo.getConsigneeId()){
						consigneeInfos.setDefaultConsigneeFlag(0);
						consigneeInfoDao.modify(consigneeInfos);
						
						consigneeInfo.setDefaultConsigneeFlag(1);
						consigneeInfoDao.modify(consigneeInfo);
					}
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
	 * 根据收货人Id获取收货人信息
	 */
	@Override
	public Result getByConsigneeId(Integer consigneeId) {
		Result result = new Result();
		try {
			ConsigneeInfo ci = consigneeInfoDao.selectByConsigneeId(consigneeId);
			if(ci == null){
				result.setResultCode("9005");
				result.setResultMessage("收货人列表不存在");
				return result;
			}
			Address address1 = addressDao.selectByAddressId(ci.getConsigneeProvince());
			if(address1 != null){
				 ci.setConsigneeProvinceName(address1.getAddressName());
			}
			Address address2 = addressDao.selectByAddressId(ci.getConsigneeCity());
			if(address2 != null){
			     ci.setConsigneeCityName(address2.getAddressName());
			}
			Address address3 = addressDao.selectByAddressId(ci.getConsigneeCounty());
			if(address3 != null){
			    ci.setConsigneeCountyName(address3.getAddressName());
			}
			result.setResult(ci);
			EcUtils.setSuccessResult(result);
		} catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	

	public void setConsigneeInfoDao(ConsigneeInfoDao consigneeInfoDao) {
		this.consigneeInfoDao = consigneeInfoDao;
	}

    public void setAddressDao(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

	public DataSourceTransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(
			DataSourceTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
}
