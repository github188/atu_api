package com.atu.api.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atu.api.web.base.BaseController;
import com.atu.api.domain.Favorites;
import com.atu.api.domain.query.FavoritesQuery;
import com.atu.api.service.FavoritesService;
import com.atu.api.service.result.Result;

/** 收藏 商品信息  */

@Controller
@RequestMapping("/favorites")
public class FavoritesController extends BaseController {

	private FavoritesService favoritesService;
	
	/**
	 * 分页查询收藏商品信息
	 * @param favoritesQuery 收藏夹请求
	 * @return
	 */
	@RequestMapping(value="selectByPage", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getFavoritesByPage(FavoritesQuery favoritesQuery, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		favoritesQuery.setUserId(getUserId(request));
		return favoritesService.getFavoritesByPage(favoritesQuery);
	}
	
	/**
	 * 添加收藏商品信息
	 * @param favorites 收藏夹
	 * @return
	 */
	@RequestMapping(value="addFavorites", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result addFavorites(Favorites favorites, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		if(favorites.getItemId() == null){
			result.setResultCode("1001");
			result.setResultMessage("itemId不能为空");
			return result;
		}
		favorites.setUserId(getUserId(request));
		return favoritesService.addFavorites(favorites);
	}
	
	/**
	 * 删除收藏商品信息
	 * @param favorites 收藏夹
	 * @return
	 */
	@RequestMapping(value="delFavorites", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result delFavorites(Favorites favorites, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		if(favorites.getItemId() == null){
			result.setResultCode("1001");
			result.setResultMessage("itemId不能为空");
			return result;
		}
		favorites.setUserId(getUserId(request));
		return favoritesService.delFavorites(favorites);
	}
	
    public void setFavoritesService(FavoritesService favoritesService) {
		this.favoritesService = favoritesService;
	}
}
