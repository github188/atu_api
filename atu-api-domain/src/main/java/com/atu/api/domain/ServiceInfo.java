package com.atu.api.domain;

import java.io.Serializable;
import java.util.Date;

public class ServiceInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;

    /** 服务ID */
    private Integer serviceId;

    /** 服务名称 */
    private String serviceName;
    
    /** 服务图片 */
    private String serviceImage;
    
    /** 服务电话 */
    private String serviceTel;

    /** 分类ID */
    private Integer categoryId;

    /** 排序号 */
    private Integer sortNumber;

    /** 有效性 */
    private Integer yn;

    /** 创建时间 */
    private Date created;

    /** 修改时间 */
    private Date modified;

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

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getSortNumber() {
		return sortNumber;
	}

	public void setSortNumber(Integer sortNumber) {
		this.sortNumber = sortNumber;
	}

	public Integer getYn() {
		return yn;
	}

	public void setYn(Integer yn) {
		this.yn = yn;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

}
