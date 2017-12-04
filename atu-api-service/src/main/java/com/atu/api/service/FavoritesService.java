package com.atu.api.service;

import com.atu.api.domain.Favorites;
import com.atu.api.domain.query.FavoritesQuery;
import com.atu.api.service.result.Result;

public interface FavoritesService {
	/**
	 * 分页查询
	 * @return
	 */
	public Result getFavoritesByPage(FavoritesQuery favoritesQuery);
	
	/**
	 * 添加商品关注
	 * @param favorites
	 * @return
	 */
	public Result addFavorites(Favorites favorites);
	
	/**
	 * 删除商品关注
	 * @param userId
	 * @param favorites
	 * @return
	 */
	public Result delFavorites(Favorites favorites);

}
