package com.atu.api.domain.vo;

public class CommentCountVO {
	
	/** 总评价数 */
	private Integer totalCount;
	
	/** 好评评价数 */
	private Integer positiveCount;
	
	/** 中评评价数 */
	private Integer neutralCount;
	
	/** 差评评价数 */
	private Integer negativeCount;
	
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Integer getPositiveCount() {
		return positiveCount;
	}
	public void setPositiveCount(Integer positiveCount) {
		this.positiveCount = positiveCount;
	}
	public Integer getNeutralCount() {
		return neutralCount;
	}
	public void setNeutralCount(Integer neutralCount) {
		this.neutralCount = neutralCount;
	}
	public Integer getNegativeCount() {
		return negativeCount;
	}
	public void setNegativeCount(Integer negativeCount) {
		this.negativeCount = negativeCount;
	}
}
