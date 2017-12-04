package com.atu.api.domain.query;

import java.io.Serializable;

public class CommentReplyQuery extends BaseSearchForMysqlVo implements Serializable{
	private static final long serialVersionUID = 1L; 
	
	/** 回复ID */
	private Integer id;
	
	/** 评价ID */
	private Integer commentId;
	
	/** 用户ID */
	private Integer userId;
	
	/** 父回复ID */
	private Integer parentId;
	
	/** 删除状态 */
	private Integer deleted;
	
	/** 用户IP */
	private String ip;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
