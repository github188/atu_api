package com.atu.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.atu.api.dao.FavoritesSellerDao;
import com.atu.api.domain.FavoritesSeller;
import com.atu.api.domain.query.FavoritesSellerQuery;

@SuppressWarnings("unchecked")
public class FavoritesSellerDaoImpl extends SqlMapClientTemplate implements FavoritesSellerDao {
	
	@Override
	public List<FavoritesSeller> selectByCondition(FavoritesSellerQuery favoritesSellerQuery) {
		return (List<FavoritesSeller>)queryForList("FavoritesSeller.selectByCondition", favoritesSellerQuery);
	}
	
	@Override
	public List<FavoritesSeller> selectByConditionForPage(FavoritesSellerQuery favoritesSellerQuery) {
		return (List<FavoritesSeller>)queryForList("FavoritesSeller.selectByConditionForPage", favoritesSellerQuery);
	}
	
	@Override
	public int countByCondition(FavoritesSellerQuery favoritesSellerQuery) {
		return (Integer)queryForObject("FavoritesSeller.countByCondition", favoritesSellerQuery);
	}
	
	@Override
	public FavoritesSeller selectById(Integer favoritesId) {
		return (FavoritesSeller)queryForObject("FavoritesSeller.selectById",favoritesId);
	}

	@Override
	public Integer insert(FavoritesSeller favoritesSeller) {
		return (Integer)insert("FavoritesSeller.insert",favoritesSeller);
	}
	
	@Override
	public Integer update(FavoritesSeller favoritesSeller) {
		return update("FavoritesSeller.update", favoritesSeller);
	}

	@Override
	public void delete(Integer favoritesId) {
		delete("FavoritesSeller.delete", favoritesId);
	}
}
