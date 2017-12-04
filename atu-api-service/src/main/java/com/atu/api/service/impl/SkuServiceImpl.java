package com.atu.api.service.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.atu.api.dao.PropertyDao;
import com.atu.api.dao.PropertyValueDao;
import com.atu.api.dao.SkuDao;
import com.atu.api.domain.Sku;
import com.atu.api.service.SkuService;
import com.atu.api.service.result.Result;
import com.atu.api.service.utils.EcUtils;

@Service(value="skuService")
public class SkuServiceImpl implements SkuService {
	private static final Logger log = LoggerFactory.getLogger(SkuServiceImpl.class);
	
	private SkuDao skuDao;
	
	private PropertyDao propertyDao;
	
	private PropertyValueDao propertyValueDao;
	
	@Override
	public Result getSkuBySkuId(Integer skuId) {
		Result result = new Result();
		try{
			Sku sku = skuDao.selectBySkuId(skuId);
			if(sku == null){
				result.setResultCode("8006");
				result.setResultMessage("sku信息不存在");
				return result;
			}
			
			String salesProperty = sku.getSalesProperty();
			if(StringUtils.isNotBlank(salesProperty)){
				try{
					String value = salesProperty.split("\\^")[0];
					String [] values = value.split(":");
					String id = propertyDao.selectByPropertyId(Integer.parseInt(values[0])).getPropertyName();
					String idValue = propertyValueDao.selectByPropertyValueId(Integer.parseInt(values[1])).getPropertyValueName();
					sku.setSalesPropertyName(id+":"+idValue);
				}catch (Exception e) {
					log.error("", e);
				}
			}
			result.setResult(sku);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	public void setSkuDao(SkuDao skuDao) {
		this.skuDao = skuDao;
	}
	public void setPropertyDao(PropertyDao propertyDao) {
		this.propertyDao = propertyDao;
	}
	public void setPropertyValueDao(PropertyValueDao propertyValueDao) {
		this.propertyValueDao = propertyValueDao;
	}

}
