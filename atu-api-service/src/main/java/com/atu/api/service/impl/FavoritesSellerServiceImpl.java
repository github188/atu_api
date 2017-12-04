package com.atu.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.atu.api.dao.BusinessUserExtDao;
import com.atu.api.dao.FavoritesSellerDao;
import com.atu.api.domain.BusinessUserExt;
import com.atu.api.domain.FavoritesSeller;
import com.atu.api.domain.query.FavoritesSellerQuery;
import com.atu.api.domain.vo.FavoritesSellerVO;
import com.atu.api.service.FavoritesSellerService;
import com.atu.api.service.result.Result;
import com.atu.api.service.utils.EcUtils;

@Service(value="favoritesSellerService")
public class FavoritesSellerServiceImpl implements FavoritesSellerService {
	private static final Logger log = LoggerFactory.getLogger(FavoritesSellerServiceImpl.class);
	
	private FavoritesSellerDao favoritesSellerDao;
	private BusinessUserExtDao businessUserExtDao;

	@Override
	public Result selectFavoritesSellerByPage(FavoritesSellerQuery favoritesSellerQuery) {
		Result result = new Result();
		
		try{
			int total = favoritesSellerDao.countByCondition(favoritesSellerQuery);
			if(total == 0){
				result.setResultCode("6005");
				result.setResultMessage("收藏商铺列表不存在");
				return result;
			}
			
			int totalPage = total/favoritesSellerQuery.getPageSize() + 1;
			if(favoritesSellerQuery.getPageNo() > totalPage){
				favoritesSellerQuery.setPageNo(totalPage);
			}
			
			List<FavoritesSeller> list = favoritesSellerDao.selectByConditionForPage(favoritesSellerQuery);
			List<FavoritesSellerVO> voList = new ArrayList<FavoritesSellerVO>();
			if(list == null || list.isEmpty()){
				result.setResultCode("6005");
				result.setResultMessage("收藏商铺列表不存在");
				return result;
			}
			
			for (FavoritesSeller favoritesSeller : list) {
				FavoritesSellerVO favoritesSellerVO = new FavoritesSellerVO();
				BusinessUserExt businessUserExt = businessUserExtDao.selectById(favoritesSeller.getVenderUserId());
				if(businessUserExt != null){
					favoritesSellerVO.setFavoritesId(favoritesSeller.getFavoritesId());
					favoritesSellerVO.setShopName(businessUserExt.getShopName());
					favoritesSellerVO.setShopImage(businessUserExt.getShopImage());
					favoritesSellerVO.setVenderUserId(favoritesSeller.getVenderUserId());
					voList.add(favoritesSellerVO);
				}
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", total);
			map.put("totalPage", totalPage);
			map.put("curPage", favoritesSellerQuery.getPageNo());
			map.put("list", voList);
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		
		return result;
	}

	@Override
	public Result addFavoritesSeller(FavoritesSellerQuery favoritesSellerQuery) {
		Result result = new Result();
		try{
			BusinessUserExt businessUserExt = businessUserExtDao.selectById(favoritesSellerQuery.getVenderUserId());
			if(businessUserExt == null){
				result.setResultCode("6004");
				result.setResultMessage("该商铺信息不存在");
				return result;
			}
			
			List<FavoritesSeller> list = favoritesSellerDao.selectByCondition(favoritesSellerQuery);
			if(list != null && list.size() > 0){
				result.setResultCode("6004");
				result.setResultMessage("已收藏该商铺信息");
				return result;
			}
			
			FavoritesSeller favoritesSeller = new FavoritesSeller();
			favoritesSeller.setUserId(favoritesSellerQuery.getUserId());
			favoritesSeller.setVenderUserId(favoritesSellerQuery.getVenderUserId());
			favoritesSellerDao.insert(favoritesSeller);
			
			result.setResult(true);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	@Override
	public Result delFavoritesSeller(FavoritesSellerQuery favoritesSellerQuery) {
		Result result = new Result();
		
		try{
			BusinessUserExt businessUserExt = businessUserExtDao.selectById(favoritesSellerQuery.getVenderUserId());
			if(businessUserExt == null){
				result.setResultCode("6004");
				result.setResultMessage("该商铺信息不存在");
				return result;
			}
			
			List<FavoritesSeller> list = favoritesSellerDao.selectByCondition(favoritesSellerQuery);
			if(list == null || list.isEmpty()){
				result.setResultCode("6004");
				result.setResultMessage("未收藏该商铺信息");
				return result;
			}
			FavoritesSeller favoritesSeller = list.get(0);
			favoritesSellerDao.delete(favoritesSeller.getFavoritesId());
			
			result.setResult(true);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		
		return result;
	}

	public void setFavoritesSellerDao(FavoritesSellerDao favoritesSellerDao) {
		this.favoritesSellerDao = favoritesSellerDao;
	}

	public void setBusinessUserExtDao(BusinessUserExtDao businessUserExtDao) {
		this.businessUserExtDao = businessUserExtDao;
	}

}
