package com.atu.api.domain.query;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderDetailQuery extends BaseSearchForMysqlVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 自增ID */
    private Integer id;

    /** 订单ID */
    private Integer orderId;

    /** SKU_ID */
    private Integer skuId;
    
    /** 商品名称 */
    private String itemName;
    
    /**
     * 销售属性
     */
    private String salesProperty;

    /** 价格 */
    private BigDecimal price;

    /** 数量 */
    private Integer num;

    /** 商品图片 */
    private String itemImage;

    /** 创建时间 */
    private Date created;

    /** 修改时间 */
    private Date modified;
    
    /** 商品标题 */
    private String itemTitle;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
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

	public String getSalesProperty() {
		return salesProperty;
	}

	public void setSalesProperty(String salesProperty) {
		this.salesProperty = salesProperty;
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}
    
    
}