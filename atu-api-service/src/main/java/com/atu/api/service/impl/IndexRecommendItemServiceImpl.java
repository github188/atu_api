package com.atu.api.service.impl;

import java.math.BigDecimal;
import java.util.*;

import com.atu.api.dao.PromotionInfoDao;
import com.atu.api.dao.PromotionSkuDao;
import com.atu.api.domain.PromotionInfo;
import com.atu.api.domain.PromotionSku;
import com.atu.api.domain.query.PromotionSkuQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.atu.api.dao.IndexRecommendItemDao;
import com.atu.api.dao.SkuDao;
import com.atu.api.domain.IndexRecommendItem;
import com.atu.api.domain.Sku;
import com.atu.api.domain.query.IndexRecommendItemQuery;
import com.atu.api.service.IndexRecommendItemService;
import com.atu.api.service.result.Result;
import com.atu.api.service.utils.EcUtils;

@Service("indexRecommendItemService")
public class IndexRecommendItemServiceImpl implements IndexRecommendItemService {
	private static final Logger log = LoggerFactory.getLogger(IndexRecommendItemServiceImpl.class);
	
	private IndexRecommendItemDao indexRecommendItemDao;
	private SkuDao skuDao;
    private PromotionSkuDao promotionSkuDao;
	private PromotionInfoDao promotionInfoDao;

	/**
	 * 获取所有首页促销商品信息
	 */
	@Override
	public Result getIndexRecommendItemByPage(IndexRecommendItemQuery indexRecommendItemQuery) {
		Result result = new Result();
		Date now = new Date();
		try{
			indexRecommendItemQuery.setYn(1);
			int total = indexRecommendItemDao.countByCondition(indexRecommendItemQuery);
			if(total == 0){
				result.setResultCode("3001");
				result.setResultMessage("促销商品列表不存在");
				return result;
			}
			
			int totalPage = total/indexRecommendItemQuery.getPageSize() + 1;
			if(indexRecommendItemQuery.getPageNo() > totalPage){
				indexRecommendItemQuery.setPageNo(totalPage);
			}
			List<IndexRecommendItem> list = indexRecommendItemDao.selectByConditionForPage(indexRecommendItemQuery);
            List<IndexRecommendItem> removeList = new ArrayList<IndexRecommendItem>();
			for (IndexRecommendItem IndexRecommendItem : list) {
				Sku sku = skuDao.selectBySkuId(IndexRecommendItem.getSkuId());
                if( null == sku ) {
                    removeList.add(IndexRecommendItem);
                    continue;
                }
                //设置商品促销价 一个SKU可能有多个促销，取价格最优者
                setItemTbPromPrice(now, IndexRecommendItem, sku);
			}
            //删除SKU为空的首页促销商品
            list.removeAll(removeList);
            Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", total);
			map.put("totalPage", totalPage);
			map.put("curPage", indexRecommendItemQuery.getPageNo());
			map.put("list", list);
			result.setResult(map);
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
     * @param IndexRecommendItem
     * @param sku
     */
    private void setItemTbPromPrice(Date now, IndexRecommendItem indexRecommendItem, Sku sku) {
        //设置商品原价
        indexRecommendItem.setTbPrice(sku.getTbPrice());
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
	                indexRecommendItem.setPromPrice(sku.getTbPrice().subtract(promotionSku.getDeductionPrice()));
                }
            }
        }
    }

    public void setIndexRecommendItemDao(IndexRecommendItemDao indexRecommendItemDao) {
		this.indexRecommendItemDao = indexRecommendItemDao;
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
}
