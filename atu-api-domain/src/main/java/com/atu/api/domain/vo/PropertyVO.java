package com.atu.api.domain.vo;

import java.io.Serializable;

/**
 * 类目属性
 *
 */
public class PropertyVO implements Serializable{
	private static final long serialVersionUID = 1L;

    /** 属性ID */
    private Integer propertyId;

    /** 属性名 */
    private String propertyName;

	public Integer getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(Integer propertyId) {
		this.propertyId = propertyId;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
}