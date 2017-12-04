package com.atu.api.service;

import com.atu.api.service.result.Result;

public interface AddressService {
	/**
	 * 获取一级地址
	 * @return
	 */
	public Result getProvinces();
	/**
	 * 获取二级地址
	 * @param province
	 * @return
	 */
	public Result getCities(Integer province);
	/**
	 * 获取三级地址
	 * @param city
	 * @return
	 */
	public Result getCounties(Integer city);
}
