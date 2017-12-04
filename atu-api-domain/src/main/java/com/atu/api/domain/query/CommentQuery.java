package com.atu.api.domain.query;

import java.io.Serializable;

public class CommentQuery extends BaseSearchForMysqlVo implements Serializable{
	private static final long serialVersionUID = 1L; 
	
	/** 评价ID */
	private Integer id;
	
	/** 属性ID */
	private Integer skuId;
	
	/** 商品ID */
	private Integer itemId;
	
	/** 用户ID */
	private Integer userId;
	
	/** 评价分数 */
	private Integer score;
	
	/** 无效的个数 */
	private Integer uselessCount;
	
	/** 有效的个数 */
	private Integer usefulCount;
	
	/** 优点 */
	private String advantage;
	
	/** 缺点 */
	private String disadvantage;
	
	/** 订单ID */
	private Integer orderId;
	
	/** 审核状态 */
	private Integer status;
	
	/** 用户IP */
	private String ip;
	
	/** 增加字段 */
	private Integer type;		// 0=全部，1=好评，2=中评，3=差评
	private Integer maxScore;	// 最大分数
	private Integer minScore;	// 最小分数

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSkuId() {
		return skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getUselessCount() {
		return uselessCount;
	}

	public void setUselessCount(Integer uselessCount) {
		this.uselessCount = uselessCount;
	}

	public Integer getUsefulCount() {
		return usefulCount;
	}

	public void setUsefulCount(Integer usefulCount) {
		this.usefulCount = usefulCount;
	}

	public String getAdvantage() {
		return advantage;
	}

	public void setAdvantage(String advantage) {
		this.advantage = advantage;
	}

	public String getDisadvantage() {
		return disadvantage;
	}

	public void setDisadvantage(String disadvantage) {
		this.disadvantage = disadvantage;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(Integer maxScore) {
		this.maxScore = maxScore;
	}

	public Integer getMinScore() {
		return minScore;
	}

	public void setMinScore(Integer minScore) {
		this.minScore = minScore;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

}
