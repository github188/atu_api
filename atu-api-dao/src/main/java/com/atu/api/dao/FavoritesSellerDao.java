package com.atu.api.dao;

import java.util.List;

import com.atu.api.domain.FavoritesSeller;
import com.atu.api.domain.query.FavoritesSellerQuery;

public interface FavoritesSellerDao {
	/**
	 * 根据相应的条件查询信息
	 * @param FavoritesSellerQuery
	 * @return
	 */
	public List<FavoritesSeller> selectByCondition(FavoritesSellerQuery favoritesSellerQuery);
	
	/**
	 * 根据相应的条件查询信息-分页查询
	 * @param FavoritesSellerQuery
	 * @return
	 */
	public List<FavoritesSeller> selectByConditionForPage(FavoritesSellerQuery favoritesSellerQuery);
	
	/**
	 * 根据相应的条件查询满足条件的信息的总数
	 * @param FavoritesSellerQuery
	 * @return
	 */
	public int countByCondition(FavoritesSellerQuery favoritesSellerQuery);
	
	/**
	 * 根据相应的条件查询信息
	 * @param favoritesId
	 * @return
	 */
	public FavoritesSeller selectById(Integer favoritesId);
	
	/**
	 * 添加信息
	 * @param Favorites
	 * @return
	 */
	public Integer insert(FavoritesSeller favoritesSeller);
	
	/**
	 * 添加信息
	 * @param Favorites
	 * @return
	 */
	public Integer update(FavoritesSeller favoritesSeller);

	/**
	 * 根据id主键删除信息
	 * @param collectionId
	 */
	public void delete(Integer favoritesId);
}
