package com.atu.api.domain.query;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SkuQuery extends BaseSearchForMysqlVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** SKU_ID */
    private Integer skuId;

    /** 商品ID */
    private Integer itemId;

    /** 销售属性 */
    private String salesProperty;
    
    /** 起买量 */
    private Integer leastBuy;
    
    /** 条形码 */
    private String barCode;

    /** 成本价 */
    private BigDecimal costPrice;

    /** 销售价 */
    private BigDecimal tbPrice;

    /** 库存数量 */
    private Integer stock;

    /** 创建时间 */
    private Date created;

    /** 修改时间 */
    private Date modified;
    
    private Integer yn;

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getSalesProperty() {
		return salesProperty;
	}

	public void setSalesProperty(String salesProperty) {
		this.salesProperty = salesProperty;
	}

	public Integer getLeastBuy() {
		return leastBuy;
	}

	public void setLeastBuy(Integer leastBuy) {
		this.leastBuy = leastBuy;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}


    public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	public BigDecimal getTbPrice() {
		return tbPrice;
	}

	public void setTbPrice(BigDecimal tbPrice) {
		this.tbPrice = tbPrice;
	}

	public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
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

	public Integer getYn() {
		return yn;
	}

	public void setYn(Integer yn) {
		this.yn = yn;
	}
    
}