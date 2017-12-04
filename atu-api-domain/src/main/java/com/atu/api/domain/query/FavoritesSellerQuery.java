package com.atu.api.domain.query;


import java.io.Serializable;

/**
 * 收藏商铺
 */
public class FavoritesSellerQuery extends BaseSearchForMysqlVo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/** 自增ID */
    private Integer favoritesId;

	/** 用户ID */
    private Integer userId;

	/** 商铺ID */
    private Integer venderUserId;
    
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
}