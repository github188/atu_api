package com.atu.api.dao;

import java.util.List;

import com.atu.api.domain.Address;
import com.atu.api.domain.query.AddressQuery;

public interface AddressDao{
	
	/**
	 * 添加地址信息
	 * @param address
	 * @return
	 */
	public Integer insert(Address address);

	/**
	 * 依据地址ID修改地址信息
	 * @param address
	 */
	public void modify(Address address);

	/**
	 * 依据地址ID查询地址信息
	 * @param addressId
	 * @return
	 */
	public Address selectByAddressId(Integer addressId);
	
	/**
	 * 根据相应的条件查询满足条件的地址信息的总数
	 * @param addressQuery
	 * @return
	 */
	public Integer countByCondition(AddressQuery addressQuery);
	
	/**
	 * 根据相应的条件查询地址信息
	 * @param addressQuery
	 * @return
	 */
	public List<Address> selectByCondition(AddressQuery addressQuery);
}