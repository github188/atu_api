package com.atu.api.domain.query;

import java.io.Serializable;

public class ServiceInfoQuery extends BaseSearchForMysqlVo implements Serializable {
	
	private static final long serialVersionUID = 1L;

    /** 服务ID */
    private Integer serviceId;

    /** 分类ID */
    private Integer categoryId;
    
    /** 有效性 */
    private Integer yn;

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getYn() {
		return yn;
	}

	public void setYn(Integer yn) {
		this.yn = yn;
	}

}
