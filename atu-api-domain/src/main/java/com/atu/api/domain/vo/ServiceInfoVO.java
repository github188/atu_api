package com.atu.api.domain.vo;

import java.io.Serializable;

public class ServiceInfoVO implements Serializable {
	
	private static final long serialVersionUID = 1L;

    /** 服务ID */
    private Integer serviceId;

    /** 服务名称 */
    private String serviceName;
    
    /** 服务图片 */
    private String serviceImage;
    
    /** 服务电话 */
    private String serviceTel;

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceImage() {
		return serviceImage;
	}

	public void setServiceImage(String serviceImage) {
		this.serviceImage = serviceImage;
	}

	public String getServiceTel() {
		return serviceTel;
	}

	public void setServiceTel(String serviceTel) {
		this.serviceTel = serviceTel;
	}
}
