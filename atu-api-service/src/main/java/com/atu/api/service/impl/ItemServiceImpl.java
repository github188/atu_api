package com.atu.api.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.atu.api.dao.AddressDao;
import com.atu.api.dao.BusinessUserExtDao;
import com.atu.api.dao.CategoryDao;
import com.atu.api.dao.CommentDao;
import com.atu.api.dao.FavoritesDao;
import com.atu.api.dao.ItemDao;
import com.atu.api.dao.ItemDescriptionDao;
import com.atu.api.dao.ItemImageDao;
import com.atu.api.dao.PromotionInfoDao;
import com.atu.api.dao.PromotionSkuDao;
import com.atu.api.dao.SkuDao;
import com.atu.api.dao.UserInfoDao;
import com.atu.api.domain.Address;
import com.atu.api.domain.BusinessUserExt;
import com.atu.api.domain.Category;
import com.atu.api.domain.Favorites;
import com.atu.api.domain.Item;
import com.atu.api.domain.ItemDescription;
import com.atu.api.domain.ItemImage;
import com.atu.api.domain.PromotionInfo;
import com.atu.api.domain.PromotionSku;
import com.atu.api.domain.Sku;
import com.atu.api.domain.UserInfo;
import com.atu.api.domain.query.CommentQuery;
import com.atu.api.domain.query.FavoritesQuery;
import com.atu.api.domain.query.ItemQuery;
import com.atu.api.domain.query.PromotionSkuQuery;
import com.atu.api.domain.query.SkuQuery;
import com.atu.api.domain.vo.CommentCountVO;
import com.atu.api.domain.vo.ItemOriginVO;
import com.atu.api.domain.vo.ItemVO;
import com.atu.api.service.ItemService;
import com.atu.api.service.result.Result;
import com.atu.api.service.utils.EcUtils;

@Service("itemService")
public class ItemServiceImpl implements ItemService {
	private static final Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);
	
	private String uploadFile = "/www.tbny.net/img/";
	private String tempPath = "/www.tbny.net/img/tmp/";
	
	private ItemDao itemDao;
	private CategoryDao categoryDao;
	private AddressDao addressDao;
	private SkuDao skuDao;
	private ItemDescriptionDao itemDescriptionDao;
	private ItemImageDao itemImageDao;
    private PromotionSkuDao promotionSkuDao;
    private PromotionInfoDao promotionInfoDao;
    private CommentDao commentDao;
	private FavoritesDao favoritesDao;
	private BusinessUserExtDao businessUserExtDao;
	private UserInfoDao userInfoDao;
	
	@Override
	public Result getItemByPage(ItemQuery itemQuery) {
		Date now = new Date();
		Result result = new Result();
		try{
			itemQuery.setYn(1);
			itemQuery.setItemType(2);
			itemQuery.setItemStatus(1);
			int total = itemDao.countVOByCondition(itemQuery);
			
			if(total == 0){
				result.setResultCode("8001");
				result.setResultMessage("商品列表信息不存在");
				return result;
			}
			
			int totalPage = total/itemQuery.getPageSize() + 1;
			if(itemQuery.getPageNo() > totalPage){
				itemQuery.setPageNo(totalPage);
			}
			
			List<ItemVO> voList = itemDao.selectVOByConditionForPage(itemQuery);
			List<ItemVO> removeList = new ArrayList<ItemVO>();
			
			for (ItemVO itemVO : voList) {
				Address address1 = addressDao.selectByAddressId(itemVO.getOriginProvince());
				itemVO.setOriginProvinceName(address1.getAddressName());
				Address address2 = addressDao.selectByAddressId(itemVO.getOriginCity());
				itemVO.setOriginCityName(address2.getAddressName());
				
				if(itemQuery.getUserId() == null){
					itemVO.setIsFavorites(false);
				}else{
					FavoritesQuery favoritesQuery = new FavoritesQuery();
					favoritesQuery.setItemId(itemVO.getItemId());
					favoritesQuery.setUserId(itemQuery.getUserId());
					List<Favorites> fList = favoritesDao.selectByCondition(favoritesQuery);
					if(fList == null || fList.isEmpty()){
						itemVO.setIsFavorites(false);
					}else{
						itemVO.setIsFavorites(true);
					}
				}
				
				Sku sku = skuDao.selectBySkuId(itemVO.getSkuId());
                if( null == sku ) {
                    removeList.add(itemVO);
                    continue;
                }
                //设置商品促销价 一个SKU可能有多个促销，取价格最优者
                setItemTbPromPrice(now, itemVO, sku);
			}
			
			//删除SKU为空的首页促销商品
			voList.removeAll(removeList);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", total);
			map.put("totalPage", totalPage);
			map.put("curPage", itemQuery.getPageNo());
			map.put("itemList", voList);
			
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	@Override
	public Result getItemOrigin(){
		Result result = new Result();
		
		try{
			ItemQuery itemQuery = new ItemQuery();
			itemQuery.setYn(1);
			itemQuery.setItemType(2);
			List<Integer> originList = itemDao.selectOriginProvinceByCondition(itemQuery);
			
			List<ItemOriginVO> oList = new ArrayList<ItemOriginVO>();
			for (Integer id : originList) {
				ItemOriginVO itemOriginVO = new ItemOriginVO();
				Address address = addressDao.selectByAddressId(id);
				itemOriginVO.setAddressName(address.getAddressName());
				itemOriginVO.setAddressId(address.getAddressId());
				oList.add(itemOriginVO);
			}
			
			result.setResult(oList);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		
		return result;
	}
	
	@Override
	public Result getItemByItemId(Integer itemId, Integer userId) {
		Result result = new Result();
		try{
			boolean isFavorites = false;
			Item item = itemDao.selectByItemId(itemId);
			if(item == null){
				result.setResultCode("8004");
				result.setResultMessage("商品属性不存在");
				return result;
			}
			
			if(userId != null){
				FavoritesQuery favoritesQuery = new FavoritesQuery();
				favoritesQuery.setItemId(itemId);
				favoritesQuery.setUserId(userId);
				List<Favorites> fList = favoritesDao.selectByCondition(favoritesQuery);
				if(fList == null || fList.isEmpty()){
					isFavorites = false;
				}else{
					isFavorites = true;
				}
			}
			
			BusinessUserExt businessUserExt = businessUserExtDao.selectById(item.getVenderUserId());
			if(businessUserExt == null){
				result.setResultCode("8004");
				result.setResultMessage("商家信息不存在");
				return result;
			}
			item.setShopName(businessUserExt.getShopName());
			
			UserInfo userInfo = userInfoDao.selectByUserId(businessUserExt.getUserId());
			if(userInfo == null){
				result.setResultCode("8004");
				result.setResultMessage("用户信息不存在");
				return result;
			}
			item.setMobile(userInfo.getMobile());
			
			SkuQuery skuQuery = new SkuQuery();
			skuQuery.setItemId(itemId);
			skuQuery.setYn(1);
			
			List<Sku> skuList = skuDao.selectByCondition(skuQuery);
            Date now = new Date();
            for(Sku sku : skuList){
                //计算SKU促销价格
               //一个SKU可能有多个促销，取价格最优者
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
                //如果带促销，计算带促销价格
				if(promotionSku != null && promotionSku.getDeductionPrice().compareTo(new BigDecimal(0)) > 0){
					PromotionInfo promotionInfo = promotionInfoDao.selectByPromotionId(promotionSku.getPromotionId());
					//判断是否有促销活动
					 if(promotionInfo != null
				                && promotionInfo.getPromotionStatus()!=null && promotionInfo.getStartTime()!=null && promotionInfo.getEndTime() !=null
				                && promotionInfo.getYn() == 1 && promotionInfo.getPromotionStatus() == 1
				                && now.after(promotionInfo.getStartTime()) && now.before(promotionInfo.getEndTime())){
                            	if(sku.getTbPrice().compareTo(promotionSku.getDeductionPrice())>0){
	                                sku.setPromPrice(sku.getTbPrice().subtract(promotionSku.getDeductionPrice())); //商品实际出售单价
	                                if(promotionInfo.getPromotionType() == 1){
	                                	sku.setPromotionType("直降");
	                                }
                            	}
					}
				}
				if(StringUtils.isEmpty(sku.getPromotionType())){
					sku.setPromotionType("-");
				}
            }            
			Map<String, Object> map = new HashMap<String, Object>();
			
			Category category1 = categoryDao.selectByCategoryId(item.getCategoryId1());
			if(category1 != null){
				item.setCategoryId1Name(category1.getCategoryName());
			}
			Category category2 = categoryDao.selectByCategoryId(item.getCategoryId2());
			if(category2 != null){
				item.setCategoryId2Name(category2.getCategoryName());
			}
			
			Category category3 = categoryDao.selectByCategoryId(item.getCategoryId3());
			if(category3 != null){
				item.setCategoryId3Name(category3.getCategoryName());
			}
			
			Category category4 = categoryDao.selectByCategoryId(item.getCategoryId4());
			if(category4 != null){
				item.setCategoryId4Name(category4.getCategoryName());
			}
			
			Address address1 = addressDao.selectByAddressId(item.getOriginProvince());
			if(address1 != null){
				item.setOriginProvinceName(address1.getAddressName());
			}
			
			Address address2 = addressDao.selectByAddressId(item.getOriginCity());
			if(address2 != null){
				item.setOriginCityName(address2.getAddressName());
			}
			
			Address address3 = addressDao.selectByAddressId(item.getOriginCounty());
			if(address3 != null){
				item.setOriginCountyName(address3.getAddressName());
			}
			
			CommentQuery commentQuery = new CommentQuery();
			commentQuery.setItemId(itemId);
			
			// 总评价数
			int totalCount = commentDao.countByCondition(commentQuery);
			
			// 好评评价数
			commentQuery.setMaxScore(5);
			commentQuery.setMinScore(5);
			int positiveCount = commentDao.countByCondition(commentQuery);
			
			// 中评评价数
			commentQuery.setMaxScore(4);
			commentQuery.setMinScore(3);
			int neutralCount = commentDao.countByCondition(commentQuery);
			
			// 差评评价数
			commentQuery.setMaxScore(2);
			commentQuery.setMinScore(1);
			int negativeCount = commentDao.countByCondition(commentQuery);
			
			CommentCountVO commentCountVO = new CommentCountVO();
			commentCountVO.setTotalCount(totalCount);
			commentCountVO.setPositiveCount(positiveCount);
			commentCountVO.setNegativeCount(negativeCount);
			commentCountVO.setNeutralCount(neutralCount);
			
			map.put("item", item);
			map.put("skuList", skuList);
			map.put("commentCount", commentCountVO);
			map.put("isFavorites", isFavorites);
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	@Override
	public Result getItemsByVenderUserId(ItemQuery itemQuery) {
		Result result = new Result();
		Date now = new Date();
		try{
			itemQuery.setYn(1);
			itemQuery.setItemType(2);
			itemQuery.setItemStatus(1);
			int total = itemDao.countByCondition(itemQuery);
			
			if(total == 0){
				result.setResultCode("8001");
				result.setResultMessage("商品列表信息不存在");
				return result;
			}
			
			int totalPage = total/itemQuery.getPageSize() + 1;
			if(itemQuery.getPageNo() > totalPage){
				itemQuery.setPageNo(totalPage);
			}
			
			List<Item> itemList = itemDao.selectByConditionForPage(itemQuery);
			
			List<ItemVO> voList = new ArrayList<ItemVO>();
			SkuQuery skuQuery = new SkuQuery();
			
			for (Item item : itemList) {
				ItemVO itemVO = new ItemVO();
				
				skuQuery.setItemId(item.getItemId());
				List<Sku> skuList = skuDao.selectByCondition(skuQuery);
				if(skuList != null && !skuList.isEmpty()){
					for (Sku sku : skuList) {
						//设置商品促销价 一个SKU可能有多个促销，取价格最优者
		                setItemTbPromPrice(now, itemVO, sku);
					}
					
					if(itemVO.getPromPrice() == null){
						Sku pSku = skuList.get(0);
						for(Sku sku : skuList){
				            if(pSku.getTbPrice().compareTo(sku.getTbPrice()) ==1 ){
				            	pSku = sku;
				            }
				        }
						itemVO.setSkuId(pSku.getSkuId());
		            	itemVO.setSalesPropertyName(pSku.getSalesPropertyName());
		            	itemVO.setTbPrice(pSku.getTbPrice());
			        }
					
					Address address1 = addressDao.selectByAddressId(item.getOriginProvince());
					Address address2 = addressDao.selectByAddressId(item.getOriginCity());
					
					itemVO.setItemId(item.getItemId());
					itemVO.setItemImage(item.getItemImage());
					itemVO.setItemName(item.getItemName());
					itemVO.setOriginProvince(item.getOriginProvince());
					itemVO.setOriginProvinceName(address1.getAddressName());
					itemVO.setOriginCity(item.getOriginCity());
					itemVO.setOriginCityName(address2.getAddressName());
					itemVO.setVenderUserId(itemQuery.getVenderUserId());
					
					voList.add(itemVO);
				}
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", total);
			map.put("totalPage", totalPage);
			map.put("curPage", itemQuery.getPageNo());
			map.put("itemList", voList);
			
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	@Override
	public Result getSkusByItemId(Integer itemId) {
		Result result = new Result();
		try{
			Item item = itemDao.selectByItemId(itemId);
			if(item == null){
				result.setResultCode("8004");
				result.setResultMessage("商品属性不存在");
				return result;
			}
			SkuQuery skuQuery = new SkuQuery();
			skuQuery.setItemId(itemId);
			skuQuery.setYn(1);
			List<Sku> list = skuDao.selectByCondition(skuQuery);
			if(list == null || list.size() == 0){
				result.setResultCode("8002");
				result.setResultMessage("sku列表不存在");
				return result;
			}
			
			for (Sku sku : list) {
				sku.setSalesPropertyName(sku.getSalesPropertyName().split("\\:")[1].split("\\^")[0]);
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("skus", list);
			map.put("item", item);
			map.put("sku", list.get(0));
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	@Override
	public Result addItem(Item item, List<Sku> skus) {
		Result result = new Result();
		try{
			Integer itemId = itemDao.insert(item);
			for(int i=0;i<skus.size();i++){
				Sku sku = skus.get(i);
				sku.setItemId(itemId);
				skuDao.insert(sku);
			}
			result.setResult(itemId);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	@Override
	public Result updateItem(Item item) {
		Result result = new Result();
		try{
			itemDao.modify(item);
			result.setResult(true);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	@Override
	public Result getappDescriptionInfo(Integer itemId) {
		Result result = new Result();
		try{
			ItemDescription itemDescription = itemDescriptionDao.selectByItemId(itemId);
			if(itemDescription == null){
				result.setResultCode("8003");
				result.setResultMessage("商品介绍不存在");
				return result;
			}
			
			result.setResult(itemDescription.getAppDescriptionInfo());
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("");
			EcUtils.setExceptionResult(result);
		}
		
		return result;
	}
	
	@Override
	public Result getItemImages(Integer itemId) {
		Result result = new Result();
		try{
			List<ItemImage> imgList = new ArrayList<ItemImage>();
			List<ItemImage> list = itemImageDao.selectByItemId(itemId);
			
			if(list == null || list.size() ==0){
				result.setResultCode("8007");
				result.setResultMessage("商品图片不存在");
				return result;
			}
			
			for (ItemImage itemImage : list) {
				if(StringUtils.isNotEmpty(itemImage.getImageUrl())){
					imgList.add(itemImage);
				}
			}
			result.setResult(imgList);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		
		return result;
	}
	
	@Override
	public Result imageUpload(HttpServletRequest request) {
		Result result = new Result();
		String path = null;
		String savefilePath=null;
		List<String> list = new ArrayList<String>();
		try {
			DiskFileUpload fu = new DiskFileUpload();   
	        // 设置最大文件尺寸，这里是4MB   
	        fu.setSizeMax(4194304);
	        // 设置缓冲区大小，这里是4kb   
	        fu.setSizeThreshold(4096);   
	        // 设置临时目录：   
	        fu.setRepositoryPath(tempPath);  
	  
	        // 得到所有的文件：   
	        List fileItems = fu.parseRequest(request);   
	        Iterator i = fileItems.iterator();
	        
	        while(i.hasNext()) {
				FileItem fi = (FileItem)i.next();
				if(!fi.isFormField()){
					
					String fileName = fi.getName();
					fileName = fileName.toLowerCase();
					String fileType=fileName.substring(fileName.lastIndexOf("."));
					int choice=97;
			        Random random=new Random();
			        char var = (char) (choice + random.nextInt(26)); 
			        String prd = "p"+ var +(int)(Math.random() * 1000000);
			        fileName = prd + fileType;
//			        String fileName50 = prd + "_50_50"+ fileType;
//			        String fileName100 = prd + "_100_100"+ fileType;
//			        String fileName200 = prd + "_200_200"+ fileType;
			        
			        Calendar cal = Calendar.getInstance();
					int year=cal.get(Calendar.YEAR);//得到年
					int month=cal.get(Calendar.MONTH)+1;//得到月，因为从0开始的，所以要加1
					int day=cal.get(Calendar.DAY_OF_MONTH);//得到天
					
					path = year +"/" + month+"/"+day+"/";
					savefilePath = uploadFile + path;

					File file = new File(savefilePath);
					if(!file.exists()){
						file.mkdirs();
					}
					
					File savedFile = new File(savefilePath, fileName);
					fi.write(savedFile);
					
					/*ImageUtils.resize(savefilePath + fileName, savefilePath + fileName50, 50, 50);
					ImageUtils.resize(savefilePath + fileName, savefilePath + fileName100, 100, 100);
					ImageUtils.resize(savefilePath + fileName, savefilePath + fileName200, 200, 200);*/
					
					list.add("http:/" + savefilePath + fileName);
				}
			}
	        
	        result.setResult(list);
			EcUtils.setSuccessResult(result);
		} catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	@Override
	public Result addItemImage(ItemImage itemImage) {
		Result result = new Result();
		try{
			itemImageDao.insert(itemImage);
			result.setResult(true);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	@Override
	public Result updateSku(Sku sku) {
		Result result = new Result();
		try{
			if(skuDao.selectBySkuId(sku.getSkuId()) == null){
				result.setResultCode("8002F");
				result.setResultMessage("商品图片不存在");
				return result;
			}
			
			skuDao.modify(sku);
			result.setResult(true);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		
		return result;
	}

    @Override
    public Result addItemImages(List<ItemImage> itemImageList) {
        Result result = new Result();
		try{
            for( ItemImage itemImage : itemImageList ){
                itemImageDao.insert(itemImage);
            }
            result.setResult(true);
		    EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
        return result;  //To change body of implemented methods use File | Settings | File Templates.
    }
    
    /**
     * 计算SKU促销价，当一个SKU参加了多个促销时选择价格最优者
     * @param now
     * @param indexPromItem
     * @param sku
     */
    private void setItemTbPromPrice(Date now, ItemVO itemVO, Sku sku) {
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
                	//商品促销价
                	itemVO.setPromPrice(sku.getTbPrice().subtract(promotionSku.getDeductionPrice()));
                	//设置商品属性 
                	itemVO.setSkuId(sku.getSkuId());
                	itemVO.setSalesPropertyName(sku.getSalesPropertyName());
                	itemVO.setTbPrice(sku.getTbPrice());
                }
            }
        }
    }

    public void setItemDao(ItemDao itemDao) {
		this.itemDao = itemDao;
	}

	public void setSkuDao(SkuDao skuDao) {
		this.skuDao = skuDao;
	}

	public void setItemDescriptionDao(ItemDescriptionDao itemDescriptionDao) {
		this.itemDescriptionDao = itemDescriptionDao;
	}

	public void setItemImageDao(ItemImageDao itemImageDao) {
		this.itemImageDao = itemImageDao;
	}

	public void setUploadFile(String uploadFile) {
		this.uploadFile = uploadFile;
	}

	public void setTempPath(String tempPath) {
		this.tempPath = tempPath;
	}

	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	public void setAddressDao(AddressDao addressDao) {
		this.addressDao = addressDao;
	}

    public void setPromotionSkuDao(PromotionSkuDao promotionSkuDao) {
        this.promotionSkuDao = promotionSkuDao;
    }

    public void setPromotionInfoDao(PromotionInfoDao promotionInfoDao) {
        this.promotionInfoDao = promotionInfoDao;
    }

	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}

	public void setFavoritesDao(FavoritesDao favoritesDao) {
		this.favoritesDao = favoritesDao;
	}

	public void setBusinessUserExtDao(BusinessUserExtDao businessUserExtDao) {
		this.businessUserExtDao = businessUserExtDao;
	}

	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}
}
