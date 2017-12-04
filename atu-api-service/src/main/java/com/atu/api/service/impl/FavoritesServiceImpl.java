package com.atu.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.atu.api.dao.AddressDao;
import com.atu.api.dao.FavoritesDao;
import com.atu.api.dao.ItemDao;
import com.atu.api.dao.PromotionInfoDao;
import com.atu.api.dao.PromotionSkuDao;
import com.atu.api.dao.SkuDao;
import com.atu.api.domain.Address;
import com.atu.api.domain.Favorites;
import com.atu.api.domain.Item;
import com.atu.api.domain.PromotionInfo;
import com.atu.api.domain.PromotionSku;
import com.atu.api.domain.Sku;
import com.atu.api.domain.query.FavoritesQuery;
import com.atu.api.domain.query.PromotionSkuQuery;
import com.atu.api.domain.query.SkuQuery;
import com.atu.api.domain.vo.FavoritesVO;
import com.atu.api.service.FavoritesService;
import com.atu.api.service.result.Result;
import com.atu.api.service.utils.EcUtils;

@Service(value="favoritesService")
public class FavoritesServiceImpl implements FavoritesService {
	private static final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);
	
	private FavoritesDao favoritesDao;
	private ItemDao itemDao;
    private SkuDao skuDao;
    private PromotionSkuDao promotionSkuDao;
    private PromotionInfoDao promotionInfoDao;
    private AddressDao addressDao;

	@Override
	public Result getFavoritesByPage(FavoritesQuery favoritesQuery) {
		Result result = new Result();
		Date now = new Date();
		try{
			int total = favoritesDao.countByCondition(favoritesQuery);
			if(total == 0){
				result.setResultCode("6002");
				result.setResultMessage("收藏列表不存在");
				return result;
			}
			
			int totalPage = total/favoritesQuery.getPageSize() + 1;
			if(favoritesQuery.getPageNo() > totalPage){
				favoritesQuery.setPageNo(totalPage);
			}
            List<Favorites> favoritesList = favoritesDao.selectByConditionForPage(favoritesQuery);
            List<FavoritesVO> voList = new ArrayList<FavoritesVO>();
            if( null != favoritesList && 0 < favoritesList.size()  ){
               for(Favorites favorites : favoritesList ){
            	   FavoritesVO favoritesVO = new FavoritesVO();
                   if( null != favorites.getItemId() && 0 < favorites.getItemId() ){
                       Item item = itemDao.selectByItemId(favorites.getItemId());
                       Address address1 = addressDao.selectByAddressId(item.getOriginProvince());
                       Address address2 = addressDao.selectByAddressId(item.getOriginCity());
                       
                       favoritesVO.setOriginProvince(item.getOriginProvince());
                       favoritesVO.setOriginProvinceName(address1.getAddressName());
                       favoritesVO.setOriginCity(item.getOriginCity());
       				   favoritesVO.setOriginCityName(address2.getAddressName());
                       favoritesVO.setFavoritesId(favorites.getFavoritesId());
                       favoritesVO.setItemId(item.getItemId());
                       favoritesVO.setItemImage(item.getItemImage());
                       favoritesVO.setItemName(item.getItemName());
                       
                       SkuQuery skuQuery = new SkuQuery();
                       skuQuery.setItemId(item.getItemId());
                       skuQuery.setYn(1);
                       List<Sku> list = skuDao.selectByCondition(skuQuery);
                       for (Sku sku : list) {
                    	   setItemTbPromPrice(now, favoritesVO, sku);
                       }
                       if(favoritesVO.getSkuId() == null){
                    	   Sku sku = list.get(0);
	                       favoritesVO.setSkuId(sku.getSkuId());
	                       favoritesVO.setSalesPropertyName(sku.getSalesPropertyName());
	                       favoritesVO.setTbPrice(sku.getTbPrice());
                       }
                       voList.add(favoritesVO);
                   }         
               }
            }
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", total);
			map.put("totalPage", totalPage);
			map.put("curPage", favoritesQuery.getPageNo());
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
	public Result addFavorites(Favorites favorites) {
		Result result = new Result();
		try{
			FavoritesQuery favoritesQuery = new FavoritesQuery();
			favoritesQuery.setItemId(favorites.getItemId());
            favoritesQuery.setUserId(favorites.getUserId());
			List<Favorites> list = favoritesDao.selectByCondition(favoritesQuery);
			if(list != null && list.size() > 0){
				result.setResultCode("6004");
				result.setResultMessage("已经收藏该商品");
				return result;
			}
			favoritesDao.insert(favorites);
			result.setResult(true);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	@Override
	public Result delFavorites(Favorites favorites) {
		Result result = new Result();
		try{
			FavoritesQuery favoritesQuery = new FavoritesQuery();
			favoritesQuery.setItemId(favorites.getItemId());
            favoritesQuery.setUserId(favorites.getUserId());
			List<Favorites> list = favoritesDao.selectByCondition(favoritesQuery);
			if(list == null || list.size() == 0){
				result.setResultCode("6003");
				result.setResultMessage("该商品不存在");
				return result;
			}
			favoritesDao.delete(list.get(0).getFavoritesId());
			result.setResult(true);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	/**
     * 计算SKU促销价，当一个SKU参加了多个促销时选择价格最优者
     * @param now
     * @param indexPromItem
     * @param sku
     */
    private void setItemTbPromPrice(Date now, FavoritesVO favoritesVO, Sku sku) {
    	favoritesVO.setTbPrice(sku.getTbPrice());
        PromotionSkuQuery promotionSkuQuery = new PromotionSkuQuery();
        promotionSkuQuery.setSkuId(sku.getSkuId());
        promotionSkuQuery.setYn(1);
        List<PromotionSku> psList = promotionSkuDao.selectByCondition(promotionSkuQuery);
        PromotionSku promotionSku = null;
        if( null != psList && 0 < psList.size() ){
            promotionSku = psList.get(0);
        }
        for(PromotionSku ps :psList ){
            if( ps.getDeductionPrice().compareTo( promotionSku.getDeductionPrice() ) ==1 ){
                promotionSku = ps;
            }
        }
        //如果带促销，计算带促销订单价格
        if(promotionSku != null && promotionSku.getDeductionPrice().compareTo(new BigDecimal(0)) > 0){
            PromotionInfo promotionInfo = promotionInfoDao.selectByPromotionId(promotionSku.getPromotionId());
            //判断是否有促销活动
            if(promotionInfo != null
                    && promotionInfo.getPromotionStatus()!=null && promotionInfo.getStartTime()!=null && promotionInfo.getEndTime() !=null
                    && promotionInfo.getYn() == 1 && promotionInfo.getPromotionStatus() == 1
                    && now.after(promotionInfo.getStartTime()) && now.before(promotionInfo.getEndTime())){
                if(sku.getTbPrice().compareTo(promotionSku.getDeductionPrice())>0){
                	favoritesVO.setPromPrice(sku.getTbPrice().subtract(promotionSku.getDeductionPrice()));
                	favoritesVO.setSkuId(sku.getSkuId());
                	favoritesVO.setSalesPropertyName(sku.getSalesPropertyName());
                }
            }
        }
    }

    public void setFavoritesDao(FavoritesDao favoritesDao) {
		this.favoritesDao = favoritesDao;
	}

	public void setItemDao(ItemDao itemDao) {
		this.itemDao = itemDao;
	}

    public void setSkuDao(SkuDao skuDao) {
        this.skuDao = skuDao;
    }

	public void setPromotionSkuDao(PromotionSkuDao promotionSkuDao) {
		this.promotionSkuDao = promotionSkuDao;
	}

	public void setPromotionInfoDao(PromotionInfoDao promotionInfoDao) {
		this.promotionInfoDao = promotionInfoDao;
	}

	public void setAddressDao(AddressDao addressDao) {
		this.addressDao = addressDao;
	}
	
}
