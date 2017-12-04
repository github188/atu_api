package com.atu.api.domain;


import java.io.Serializable;
import java.util.Date;

/**
 * 收藏商铺信息
 */
public class FavoritesSeller implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/** 自增ID */
    private Integer favoritesId;

	/** 用户ID */
    private Integer userId;

	/** 商铺ID */
    private Integer venderUserId;

	/** 创建时间 */
    private Date created;

	/** 修改时间 */
    private Date modified;
    
	public Integer getFavoritesId() {
		return favoritesId;
	}

	public void setFavoritesId(Integer favoritesId) {
		this.favoritesId = favoritesId;
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