package com.atu.api.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.atu.api.dao.AddressDao;
import com.atu.api.domain.Address;
import com.atu.api.domain.query.AddressQuery;
import com.atu.api.service.AddressService;
import com.atu.api.service.result.Result;
import com.atu.api.service.utils.EcUtils;

@Service(value="addressService")
public class AddressServiceImpl implements AddressService {
	private static final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);
	
	//声明AddressDao接口
	private AddressDao addressDao;
	
	//查询一级地址列表
	@Override
	public Result getProvinces() {
		
		//实例化Result类
		Result result = new Result();
		try{
			//实例化地址信息
			AddressQuery addressQuery = new AddressQuery();
			//调用地址信息类中地址等级为1
			addressQuery.setAddressLevel(1);
			//创建一个地址列表集合，调用addressDao接口中selectByCondition抽象方法查询条件是addressQuery
			List<Address> list = addressDao.selectByCondition(addressQuery);
			//判断list集合是否为空，判断list中值的个数是否为零
			if(list == null || list.size() ==0){
				//为Result类中resultCode属性赋值
				result.setResultCode("6001");
				//为Result类中resultMessage属性赋值
				result.setResultMessage("地址列表不存在");
				//返回result对象为null
				return result;
			}
			//把list的值赋给Result类的对象result
			result.setResult(list);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	//查询二级地址列表
	@Override
	public Result getCities(Integer province) {
		Result result = new Result();
		try{
			AddressQuery addressQuery = new AddressQuery();
			addressQuery.setAddressLevel(2);
			addressQuery.setAddressFid(province);
			List<Address> list = addressDao.selectByCondition(addressQuery);
			if(list == null || list.size() ==0){
				result.setResultCode("6001");
				result.setResultMessage("地址列表不存在");
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

	//查询三级地址列表
	@Override
	public Result getCounties(Integer city) {
		Result result = new Result();
		try{
			AddressQuery addressQuery = new AddressQuery();
			addressQuery.setAddressLevel(3);
			addressQuery.setAddressFid(city);
			List<Address> list = addressDao.selectByCondition(addressQuery);
			
			if(list == null || list.size() ==0){
				result.setResultCode("6001");
				result.setResultMessage("地址列表不存在");
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

	//发送addressDao传给接口AddressDao
	public void setAddressDao(AddressDao addressDao) {
		this.addressDao = addressDao;
	}
	
}
