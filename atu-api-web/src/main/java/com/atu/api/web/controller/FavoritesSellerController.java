package com.atu.api.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atu.api.web.base.BaseController;
import com.atu.api.domain.query.FavoritesSellerQuery;
import com.atu.api.service.FavoritesSellerService;
import com.atu.api.service.result.Result;

/** 收藏商铺信息  */

@Controller
@RequestMapping("/favoritesSeller")
public class FavoritesSellerController extends BaseController {
    
    private FavoritesSellerService favoritesSellerService;
    
    /**
     * 分页查询收藏商铺信息
     * @param favoritesSellerQuery 收藏商铺请求
     * @return
     */
    @RequestMapping(value="selectByPage", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result selectFavoritesSellerByPage(FavoritesSellerQuery favoritesSellerQuery, HttpServletRequest request,HttpServletResponse response, ModelMap context){
    	favoritesSellerQuery.setUserId(getUserId(request));
		return favoritesSellerService.selectFavoritesSellerByPage(favoritesSellerQuery);
	}
    
    /**
     * 添加收藏商铺信息
     * @param favoritesSellerQuery 收藏商铺请求
     * @return
     */
	@RequestMapping(value="addFavoritesSeller", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result addFavoritesSeller(FavoritesSellerQuery favoritesSellerQuery, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		if(favoritesSellerQuery.getVenderUserId() == null){
			result.setResultCode("1001");
			result.setResultMessage("venderUserId不能为空");
			return result;
		}
		favoritesSellerQuery.setUserId(getUserId(request));
		return favoritesSellerService.addFavoritesSeller(favoritesSellerQuery);
	}
	
	/**
	 * 删除收藏商铺信息
	 * @param favoritesSellerQuery 收藏商铺请求
	 * @return
	 */
	@RequestMapping(value="delFavoritesSeller", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result delFavoritesSeller(FavoritesSellerQuery favoritesSellerQuery, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		if(favoritesSellerQuery.getVenderUserId() == null){
			result.setResultCode("1001");
			result.setResultMessage("venderUserId不能为空");
			return result;
		}
		favoritesSellerQuery.setUserId(getUserId(request));
		return favoritesSellerService.delFavoritesSeller(favoritesSellerQuery);
	}

	public void setFavoritesSellerService(
			FavoritesSellerService favoritesSellerService) {
		this.favoritesSellerService = favoritesSellerService;
	}
}
