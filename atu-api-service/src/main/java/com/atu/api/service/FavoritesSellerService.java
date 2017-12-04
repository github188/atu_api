package com.atu.api.service;

import com.atu.api.domain.query.FavoritesSellerQuery;
import com.atu.api.service.result.Result;

public interface FavoritesSellerService {
	/**
	 * 查询收藏商铺信息-分页
	 * @param favoritesSellerQuery
	 * @return
	 */
	public Result selectFavoritesSellerByPage(FavoritesSellerQuery favoritesSellerQuery);
	
	/**
	 * 收藏商铺信息
	 * @param favoritesSellerQuery
	 * @return
	 */
	public Result addFavoritesSeller(FavoritesSellerQuery favoritesSellerQuery);
	
	/**
	 * 删除收藏商铺信息
	 * @param favoritesSellerQuery
	 * @return
	 */
	public Result delFavoritesSeller(FavoritesSellerQuery favoritesSellerQuery);
}
