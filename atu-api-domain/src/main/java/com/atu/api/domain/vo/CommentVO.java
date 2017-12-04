package com.atu.api.domain.vo;

public class CommentVO {
	
	/** 评价ID */
	private Integer id;
	
	/** 属性ID */
	private Integer skuId;
	
	/** 商品ID */
	private Integer itemId;
	
	/** 用户ID */
	private Integer userId;
	
	/** 评价标题 */
	private String title;
	
	/** 评价内容 */
	private String content;
	
	/** 评价分数 */
	private Integer score;
	
	/** 无用的总数 */
	private Integer uselessCount;
	
	/** 有用的总数 */
	private Integer usefulCount;
	
	/** 优点 */
	private String advantage;
	
	/** 缺点 */
	private String disadvantage;
	
	/** 订单ID */
	private Integer orderId;
	
	/** 所得积分 */
	private Integer integral;
	
	/** 用户IP */
	private String ip;
	
	/** 回复信息数量 */
	private Integer replyCount;

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

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(Integer replyCount) {
		this.replyCount = replyCount;
	}
}
