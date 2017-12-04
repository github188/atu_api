package com.atu.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.atu.api.dao.AddressDao;
import com.atu.api.dao.BusinessUserExtDao;
import com.atu.api.dao.CartInfoDao;
import com.atu.api.dao.ConsigneeInfoDao;
import com.atu.api.dao.ItemDao;
import com.atu.api.dao.PromotionInfoDao;
import com.atu.api.dao.PromotionSkuDao;
import com.atu.api.dao.SkuDao;
import com.atu.api.domain.Address;
import com.atu.api.domain.BusinessUserExt;
import com.atu.api.domain.CartInfo;
import com.atu.api.domain.ConsigneeInfo;
import com.atu.api.domain.Item;
import com.atu.api.domain.PromotionInfo;
import com.atu.api.domain.PromotionSku;
import com.atu.api.domain.Sku;
import com.atu.api.domain.been.CartUpdateBeen;
import com.atu.api.domain.query.CartInfoQuery;
import com.atu.api.domain.query.PromotionSkuQuery;
import com.atu.api.domain.vo.CartBusinessVO;
import com.atu.api.domain.vo.CartItemVO;
import com.atu.api.domain.vo.CartOrderItemVO;
import com.atu.api.domain.vo.CartOrderVO;
import com.atu.api.domain.vo.CartSkuVO;
import com.atu.api.service.CartInfoService;
import com.atu.api.service.result.Result;
import com.atu.api.service.utils.EcUtils;

@Service(value="cartInfoService")
public class CartInfoServiceImpl implements CartInfoService {
	private static final Logger log = LoggerFactory.getLogger(CartInfoServiceImpl.class);
	
	private CartInfoDao cartInfoDao;
	private AddressDao addressDao;
	private ConsigneeInfoDao consigneeInfoDao;
	private DataSourceTransactionManager transactionManager;
	private BusinessUserExtDao businessUserExtDao;
	private SkuDao skuDao;
	private ItemDao itemDao;
	private PromotionSkuDao promotionSkuDao;
	private PromotionInfoDao promotionInfoDao;
	
	/**
	 * 获取购物车信息
	 */
	@Override
	public Result selectCartInfo(Integer userId) {
		Result result = new Result();
		
		try{
			// 查询购物车信息
			CartInfoQuery cartInfoQuery = new CartInfoQuery();
			cartInfoQuery.setUserId(userId);

			// 查询该用户的购物车中所有商铺信息
			List<CartBusinessVO> bList = this.selectCartBusiness(cartInfoQuery);
			
			result.setResult(bList);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		
		return result;
	}
	
	/**
	 * 添加购物车信息
	 */
	@Override
	public Result addCartInfo(CartInfo cartInfo){
		Result result = new Result();
		
		try{
			// 商品最低起买量、最大购买量、库存
			Integer minBuy = 0;
			Integer maxBuy = 0;
			Integer stock = 0;
			
			// 根据skuId获取商品sku信息
			Sku sku = skuDao.selectBySkuId(cartInfo.getSkuId());
			if(sku == null){
				result.setResultCode("1001");
				result.setResultMessage("商品属性信息为空");
				return result;
			}else{
				cartInfo.setSkuId(sku.getSkuId());
			}
			minBuy = sku.getLeastBuy();
			maxBuy = sku.getStock();
			stock = sku.getStock();
			// 判断商品是否存在促销
			/*PromotionSkuQuery promotionSkuQuery = new PromotionSkuQuery();
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
	        if(promotionSku != null && promotionSku.getDeductionPrice().compareTo(new BigDecimal(0)) > 0){
	            PromotionInfo promotionInfo = promotionInfoDao.selectByPromotionId(promotionSku.getPromotionId());
	            minBuy = promotionInfo.getPurchaseNumberMin();
	            maxBuy = promotionInfo.getPurchaseNumberMax();
	            if(promotionInfo.getPromotionStock() == null){
	            	stock = sku.getStock();
	            }else{
	            	stock = promotionInfo.getPromotionStock();
	            }
	        }else{
	        	minBuy = sku.getLeastBuy();
	        	maxBuy = 0;
	        	stock = sku.getStock();
	        }*/
	        
	        // 判断起买量
	        if(cartInfo.getSkuNum() == null){
				cartInfo.setSkuNum(minBuy);
			}else{
				if(cartInfo.getSkuNum() < minBuy){
		    		result.setResultCode("1001");
					result.setResultMessage("该商品最少起买量"+minBuy);
					return result;
		    	}
			}
	        
	        // 根据用户id获取商品信息
 			Item item = itemDao.selectByItemId(sku.getItemId());
 			
 			if(item == null){
 				result.setResultCode("1001");
 				result.setResultMessage("商品信息为空");
 				return result;
 			}else{
 				cartInfo.setItemId(item.getItemId());
 				cartInfo.setVenderUserId(item.getVenderUserId());
 			}
	        
			// 根据用户id、属性id获得购物车数据
			CartInfoQuery cartInfoQuery = new CartInfoQuery();
			cartInfoQuery.setUserId(cartInfo.getUserId());
			cartInfoQuery.setSkuId(cartInfo.getSkuId());
			List<CartInfo> cartList = cartInfoDao.selectByCondition(cartInfoQuery);
			
			if(cartList != null && !cartList.isEmpty()){
				// 修改购物车数量
				CartInfo cartInfos = cartList.get(0);
				Integer skuNum = cartInfos.getSkuNum() + cartInfo.getSkuNum();
				// 判断最大购买量
				if(maxBuy > 0 && skuNum > maxBuy){
					result.setResultCode("1001");
					result.setResultMessage("该商品最大购买量"+maxBuy);
					return result;
				}
				// 判断库存
				if(skuNum > stock){
		    		result.setResultCode("1001");
					result.setResultMessage("该商品库存"+stock);
					return result;
		    	}
				// 更新数据
				cartInfos.setSkuNum(skuNum);
				cartInfoDao.updateSkuNum(cartInfos);
			}else{
				// 判断最大购买量
				if(maxBuy > 0 && cartInfo.getSkuNum() > maxBuy){
					result.setResultCode("1001");
					result.setResultMessage("该商品最大购买量"+maxBuy);
					return result;
				}
				// 判断库存
				if(cartInfo.getSkuNum() > stock){
		    		result.setResultCode("1001");
					result.setResultMessage("该商品库存"+stock);
					return result;
		    	}
				// 添加数据
				Integer cartId = cartInfoDao.insert(cartInfo);
				result.setResult(cartId);
			}
			
			result.setResult(true);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		
		return result;
	}
	
	/**
	 * 修改购物车信息
	 */
	public Result updateCartInfo(final List<CartUpdateBeen> cartUpdateList){
		Result result = new Result();
		
		try{
			for (CartUpdateBeen cartUpdateBeen : cartUpdateList) {
				CartInfo cartInfo = cartInfoDao.selectById(cartUpdateBeen.getCartId());
				Sku sku = skuDao.selectBySkuId(cartInfo.getSkuId());
				// 判断起买量
				if(cartUpdateBeen.getSkuNum() < sku.getLeastBuy()){
					Item item = itemDao.selectByItemId(sku.getItemId());
		    		result.setResultCode("1001");
					result.setResultMessage("商品 "+item.getItemName()+" 最少起买量"+sku.getLeastBuy());
					return result;
		    	}
				// 判断库存
				if(cartUpdateBeen.getSkuNum() > sku.getStock()){
					Item item = itemDao.selectByItemId(sku.getItemId());
		    		result.setResultCode("1001");
		    		result.setResultMessage("商品 "+item.getItemName()+" 库存不足");
					return result;
		    	}
			}
			
			new TransactionTemplate(transactionManager).execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus arg0) {
					for (CartUpdateBeen cartUpdateBeen : cartUpdateList) {
						CartInfo cartInfo = new CartInfo();
						cartInfo.setCartId(cartUpdateBeen.getCartId());
						cartInfo.setSkuNum(cartUpdateBeen.getSkuNum());
						cartInfoDao.updateSkuNum(cartInfo);
					}
				}
			});
			
			result.setResult(true);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		
		return result;
	}
	
	/**
	 * 确认购物车订单
	 */
	public Result confirmCartInfo(Integer userId, List<Integer> cartIdList){
		List<CartInfo> cartInfoList = new ArrayList<CartInfo>();
		
		for (Integer cartId : cartIdList) {
			CartInfo cartInfo = cartInfoDao.selectById(cartId);
			cartInfoList.add(cartInfo);
		}
		
		return this.comfirmCartOrder(userId, cartInfoList);
	}
	
	/**
	 * 立即购买
	 */
	public Result buyNow(CartInfo cartInfo){
		List<CartInfo> cartInfoList = new ArrayList<CartInfo>();
		cartInfoList.add(cartInfo);
		
		return this.comfirmCartOrder(cartInfo.getUserId(), cartInfoList);
	}
	
	/**
	 * 删除购物车信息
	 */
	@Override
	public Result delCartInfo(final List<Integer> cartIdList){
		Result result = new Result();
		
		try{
			if(cartIdList.isEmpty() || cartIdList == null){
				result.setResultCode("1001");
				result.setResultMessage("参数为空");
				return result;
			}
			new TransactionTemplate(transactionManager).execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus arg0) {
					for (Integer cartId : cartIdList) {
						// 删除购物车
						Integer flag = cartInfoDao.delete(cartId);
						// 抛个异常，回滚事务
						if(flag == 0 || flag == null){
							throw new RuntimeException("删除失败");
						}
					}
				}
			});
			
			result.setResult(true);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
			result.setResultMessage("参数不正确");
		}
		
		return result;
	}
	
	/**
	 * 查询购物车信息-商铺对应商品信息
	 */
	private List<CartBusinessVO> selectCartBusiness(CartInfoQuery cartInfoQuery){
		Date now = new Date();
		List<CartBusinessVO> bList = new ArrayList<CartBusinessVO>();
		
		// 查询该用户的购物车中所有商铺编号
		List<Integer> bIdList = cartInfoDao.selectBusinessIdByCondition(cartInfoQuery);
		
		if(!bIdList.isEmpty() && bIdList != null){
			for (Integer bId : bIdList) {
				Integer skuNum = 0;
				BigDecimal promPrice = new BigDecimal(0);
			    BigDecimal tbPrice = new BigDecimal(0);
				CartBusinessVO cartBusinessVO = new CartBusinessVO();
				List<CartItemVO> iList = new ArrayList<CartItemVO>();
				
				BusinessUserExt businessUserExt = businessUserExtDao.selectById(bId);
				cartInfoQuery.setVenderUserId(bId);
				List<CartInfo> cartInfoList = cartInfoDao.selectByCondition(cartInfoQuery);
				
				List<CartInfo> removeList = new ArrayList<CartInfo>();
				for (CartInfo cartInfo : cartInfoList) {
					Sku sku = skuDao.selectBySkuId(cartInfo.getSkuId());
	                if( null == sku ) {
	                    removeList.add(cartInfo);
	                    continue;
	                }
	                //设置商品促销价 一个SKU可能有多个促销，取价格最优者
	                setItemTbPromPrice(now, cartInfo, sku);
				}
				
				//删除SKU为空的首页促销商品
				cartInfoList.removeAll(removeList);
				
				for (CartInfo cartInfo : cartInfoList) {
					Item item = itemDao.selectByItemId(cartInfo.getItemId());
					if(item.getItemStatus() == 1){
						Sku sku = skuDao.selectBySkuId(cartInfo.getSkuId());
						CartItemVO cartItemVO = new CartItemVO();
						cartItemVO.setCartId(cartInfo.getCartId());
						cartItemVO.setItemId(cartInfo.getItemId());
						cartItemVO.setItemImage(item.getItemImage());
						cartItemVO.setItemName(item.getItemName());
						cartItemVO.setSalesPropertyName(sku.getSalesPropertyName());
						cartItemVO.setLeastBuy(sku.getLeastBuy());
						cartItemVO.setStock(sku.getStock());
						cartItemVO.setSkuId(sku.getSkuId());
						cartItemVO.setSkuNum(cartInfo.getSkuNum());
						cartItemVO.setTbPrice(cartInfo.getTbPrice());
						cartItemVO.setPromPrice(cartInfo.getPromPrice());
						iList.add(cartItemVO);
						skuNum = skuNum + cartInfo.getSkuNum();
						promPrice = promPrice.add(cartInfo.getPromPrice());
						tbPrice = tbPrice.add(cartInfo.getTbPrice());
					}
				}
				if(iList.size() > 0){
					cartBusinessVO.setVenderUserId(bId);
					cartBusinessVO.setShopName(businessUserExt.getShopName());
					cartBusinessVO.setShopImage(businessUserExt.getShopImage());
					cartBusinessVO.setItemList(iList);
					cartBusinessVO.setSkuNum(skuNum);
					cartBusinessVO.setPromPrice(promPrice);
					cartBusinessVO.setTbPrice(tbPrice);
					bList.add(cartBusinessVO);
				}
			}
		}
		
		return bList;
	}
	
	/**
	 *  确认购物车订单信息
	 */
	private Result comfirmCartOrder(Integer userId, List<CartInfo> cartInfoList){
		Result result = new Result();
		
		try{
			// 查询购物车商品信息，并统计信息
			Date now = new Date();
			Integer cartCount = 0;
			BigDecimal cartPromPrice = new BigDecimal(0);
			BigDecimal cartDeductionPrice = new BigDecimal(0);
			List<CartOrderItemVO> cartList = new ArrayList<CartOrderItemVO>();
			List<CartSkuVO> skuList = new ArrayList<CartSkuVO>();
			
			for (CartInfo cartInfo : cartInfoList) {
				Sku sku = skuDao.selectBySkuId(cartInfo.getSkuId());
				if(sku == null){
					result.setResultCode("1001");
					result.setResultMessage("商品属性信息不存在");
					return result;
				}
				
				Item item = itemDao.selectByItemId(sku.getItemId());
				if(item == null){
					result.setResultCode("1001");
					result.setResultMessage("商品信息不存在");
					return result;
				}
				
				// 判断起买量
				if(cartInfo.getSkuNum() < sku.getLeastBuy()){
		    		result.setResultCode("1001");
					result.setResultMessage("商品 "+item.getItemName()+" 最少起买量"+sku.getLeastBuy());
					return result;
		    	}
				// 判断库存
				if(cartInfo.getSkuNum() > sku.getStock()){
		    		result.setResultCode("1001");
		    		result.setResultMessage("商品 "+item.getItemName()+" 库存不足");
					return result;
		    	}
				// 判断促销价格
				setItemTbPromPrice(now, cartInfo, sku);
				// 封装数据
				CartOrderItemVO cartOrderItemVO = new CartOrderItemVO();
				cartOrderItemVO.setItemName(item.getItemName());
				cartOrderItemVO.setItemImage(item.getItemImage());
				cartOrderItemVO.setSkuNum(cartInfo.getSkuNum());
				cartOrderItemVO.setItemId(item.getItemId());
				cartOrderItemVO.setSkuId(cartInfo.getSkuId());
				if(cartInfo.getPromPrice() == null){
					cartOrderItemVO.setItemPrice(cartInfo.getTbPrice());
					cartPromPrice = cartPromPrice.add(cartInfo.getTbPrice().multiply(new BigDecimal(cartInfo.getSkuNum())));
				}else{
					cartOrderItemVO.setItemPrice(cartInfo.getPromPrice());
					cartPromPrice = cartPromPrice.add(cartInfo.getPromPrice().multiply(new BigDecimal(cartInfo.getSkuNum())));
					cartDeductionPrice = cartDeductionPrice.add(cartInfo.getDeductionPrice().multiply(new BigDecimal(cartInfo.getSkuNum())));
				}
				cartList.add(cartOrderItemVO);
				cartCount = cartCount + cartInfo.getSkuNum();
				
				CartSkuVO cartSkuVO = new CartSkuVO();
				cartSkuVO.setItemId(sku.getItemId());
				cartSkuVO.setSkuId(sku.getSkuId());
				cartSkuVO.setNum(cartInfo.getSkuNum());
				skuList.add(cartSkuVO);
			}
			
			// 查询用户默认地址
			ConsigneeInfo consigneeInfo = consigneeInfoDao.selectByUserIdForDefault(userId);
			
			if(consigneeInfo != null){
				if( null != consigneeInfo.getConsigneeProvince() ){
		            Address address1 = addressDao.selectByAddressId(consigneeInfo.getConsigneeProvince());
				    if(address1 != null){
					    consigneeInfo.setConsigneeProvinceName(address1.getAddressName());
				    }
		        }
		        if( null != consigneeInfo.getConsigneeCity() ){
		            Address address1 = addressDao.selectByAddressId(consigneeInfo.getConsigneeCity());
				    if(address1 != null){
					    consigneeInfo.setConsigneeCityName(address1.getAddressName());
				    }
		        }
		        if( null != consigneeInfo.getConsigneeCounty() ){
		            Address address1 = addressDao.selectByAddressId(consigneeInfo.getConsigneeCounty());
				    if(address1 != null){
					    consigneeInfo.setConsigneeCountyName(address1.getAddressName());
				    }
		        }
			}
			
			// 准备数据
			CartOrderVO cartOrderVO = new CartOrderVO();
			cartOrderVO.setCartList(cartList);
			cartOrderVO.setConsigneeInfo(consigneeInfo);
			cartOrderVO.setCartCount(cartCount);
			cartOrderVO.setCartPromPrice(cartPromPrice);
			cartOrderVO.setCartDeductionPrice(cartDeductionPrice);
			cartOrderVO.setCartFreight(new BigDecimal(0.00));
			cartOrderVO.setSkus(skuList);
			
			result.setResult(cartOrderVO);
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
    private void setItemTbPromPrice(Date now, CartInfo cartInfo, Sku sku) {
        //设置商品原价
    	cartInfo.setTbPrice(sku.getTbPrice());
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
        boolean flag = false;
        //如果带促销，计算带促销订单价格
        if(promotionSku != null && promotionSku.getDeductionPrice().compareTo(new BigDecimal(0)) > 0){
            PromotionInfo promotionInfo = promotionInfoDao.selectByPromotionId(promotionSku.getPromotionId());
            //判断是否有促销活动
            if(promotionInfo != null
                    && promotionInfo.getPromotionStatus()!=null && promotionInfo.getStartTime()!=null && promotionInfo.getEndTime() !=null
                    && promotionInfo.getYn() == 1 && promotionInfo.getPromotionStatus() == 1
                    && now.after(promotionInfo.getStartTime()) && now.before(promotionInfo.getEndTime())){
                if(sku.getTbPrice().compareTo(promotionSku.getDeductionPrice())>0){
                	flag = true;
                }else{
                	flag = false;
                }
            }else{
            	flag = false;
            }
        }else{
        	flag = false;
        }
        
        if(flag){
        	//商品直降价
        	cartInfo.setDeductionPrice(promotionSku.getDeductionPrice());
        	//商品促销价
        	cartInfo.setPromPrice(sku.getTbPrice().subtract(promotionSku.getDeductionPrice()));
        }else{
        	//商品直降价
        	cartInfo.setDeductionPrice(new BigDecimal(0.00));
        	//商品促销价
        	cartInfo.setPromPrice(sku.getTbPrice());
        }
    }

	public void setCartInfoDao(CartInfoDao cartInfoDao) {
		this.cartInfoDao = cartInfoDao;
	}

	public void setAddressDao(AddressDao addressDao) {
		this.addressDao = addressDao;
	}

	public void setConsigneeInfoDao(ConsigneeInfoDao consigneeInfoDao) {
		this.consigneeInfoDao = consigneeInfoDao;
	}

	public void setTransactionManager(
			DataSourceTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public void setBusinessUserExtDao(BusinessUserExtDao businessUserExtDao) {
		this.businessUserExtDao = businessUserExtDao;
	}

	public void setSkuDao(SkuDao skuDao) {
		this.skuDao = skuDao;
	}

	public void setItemDao(ItemDao itemDao) {
		this.itemDao = itemDao;
	}

	public void setPromotionSkuDao(PromotionSkuDao promotionSkuDao) {
		this.promotionSkuDao = promotionSkuDao;
	}

	public void setPromotionInfoDao(PromotionInfoDao promotionInfoDao) {
		this.promotionInfoDao = promotionInfoDao;
	}

}
