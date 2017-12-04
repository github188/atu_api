package com.atu.api.domain.query;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderConsigneeQuery extends BaseSearchForMysqlVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** 编号 */
	private Integer id;
	
	/** 用户ID */
	private Integer userId;
	
	/** 商家ID */
	private Integer venderUserId;
	
	/** 订单号 */
	private Integer orderId;
	
	/** 购买数量 */
	private Integer buyNum;
	
	/** 购买金额 */
	private BigDecimal buyMoney;
	
	/** 收货人名称 */
	private String consigneeName;
	
	/** 收货人省份 */
	private Integer consigneeProvince;
	
	/** 收货人市区 */
	private Integer consigneeCity;
	
	/** 收货人县 */
	private Integer consigneeCounty;
	
	/** 收货人详细地址 */
	private String consigneeAddress;
	
	/** 收货人手机 */
	private String consigneeMobile;
	
	/** 快递信息 */
	private String express;
	
	/** 数据是否可用 */
	private Integer YN;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getVenderUserId() {
		return venderUserId;
	}

	public void setVenderUserId(Integer venderUserId) {
		this.venderUserId = venderUserId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(Integer buyNum) {
		this.buyNum = buyNum;
	}

	public BigDecimal getBuyMoney() {
		return buyMoney;
	}

	public void setBuyMoney(BigDecimal buyMoney) {
		this.buyMoney = buyMoney;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public Integer getConsigneeProvince() {
		return consigneeProvince;
	}

	public void setConsigneeProvince(Integer consigneeProvince) {
		this.consigneeProvince = consigneeProvince;
	}

	public Integer getConsigneeCity() {
		return consigneeCity;
	}

	public void setConsigneeCity(Integer consigneeCity) {
		this.consigneeCity = consigneeCity;
	}

	public Integer getConsigneeCounty() {
		return consigneeCounty;
	}

	public void setConsigneeCounty(Integer consigneeCounty) {
		this.consigneeCounty = consigneeCounty;
	}

	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}

	public String getConsigneeMobile() {
		return consigneeMobile;
	}

	public void setConsigneeMobile(String consigneeMobile) {
		this.consigneeMobile = consigneeMobile;
	}

	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
	}

	public Integer getYN() {
		return YN;
	}

	public void setYN(Integer yN) {
		YN = yN;
	}
	
	

}
