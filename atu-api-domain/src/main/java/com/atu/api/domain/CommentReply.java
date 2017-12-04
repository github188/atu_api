package com.atu.api.domain;

import java.io.Serializable;
import java.util.Date;

public class CommentReply implements Serializable{
	private static final long serialVersionUID = 1L; 
	
	/** 回复ID */
	private Integer id;
	
	/** 评价ID */
	private Integer commentId;
	
	/** 用户ID */
	private Integer userId;
	
	/** 父回复ID */
	private Integer parentId;
	
	/** 回复内容 */
	private String content;
	
	/** 删除状态 */
	private Integer deleted;
	
	/** 用户IP */
	private String ip;
	
	/** 创建时间 */
	private Date created;
	
	/** 修改时间 */
	private Date modified;
	
	/** 增加字段 */
	private String shopName;	// 用户商铺名称
	private String parentName;	// 评价用户名称

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
}
