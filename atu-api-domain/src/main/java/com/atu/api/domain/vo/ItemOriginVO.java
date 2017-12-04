package com.atu.api.domain.vo;


public class ItemOriginVO {
	/** 产地编号 */
	private Integer addressId;
	
	/** 产地名称 */
	private String addressName;

	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
}
