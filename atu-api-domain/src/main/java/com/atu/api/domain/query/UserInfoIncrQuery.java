package com.atu.api.domain.query;

import java.io.Serializable;
import java.util.Date;

public class UserInfoIncrQuery extends BaseSearchForMysqlVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/** 自增ID */
	private Integer id;
	
	/** 用户ID */
	private Integer userId;
	
	/** 用户名称 */
	private String userName;
	
	/** 用户头像 */
	private String headImageUrl;
	
	/** 性别 */
	private Integer sex;
	
	/** 来源(0-H5  1-android 2-IOS 3-PC) */
	private Integer fromWhere;
	
	/** 是否可用 */
	private Integer yn;
	
	/** 创建时间 */
	private Date created;
	
	/** 修改时间 */
	private Date modified;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getHeadImageUrl() {
		return headImageUrl;
	}

	public void setHeadImageUrl(String headImageUrl) {
		this.headImageUrl = headImageUrl;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getFromWhere() {
		return fromWhere;
	}

	public void setFromWhere(Integer fromWhere) {
		this.fromWhere = fromWhere;
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
