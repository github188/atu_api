package com.atu.api.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.atu.api.domain.Item;
import com.atu.api.domain.ItemImage;
import com.atu.api.domain.Sku;
import com.atu.api.domain.query.ItemQuery;
import com.atu.api.service.result.Result;

public interface ItemService {
	
	/**
	 * 根据条件获取商品列表
	 * @param itemQuery
	 * @return
	 */
	public Result getItemByPage(ItemQuery itemQuery);
	
	/**
	 * 获取商品列表的所有产地(省级)
	 * @return
	 */
	public Result getItemOrigin();
	
	/**
	 * 根据商品编号获取商品信息
	 * @param itemId
	 * @param userId
	 * @return
	 */
	public Result getItemByItemId(Integer itemId, Integer userId);
	
	/**
	 * 依据条件查询商家所属所有商品信息
	 * @return
	 */
	public Result getItemsByVenderUserId(ItemQuery itemQuery);
	
	/**
	 * 依据商品ID查询商品下SKU信息
	 * @param itemId
	 * @return
	 */
	public Result getSkusByItemId(Integer itemId);
	
	/**
	 * 添加商品
	 * @param item
	 * @return
	 */
	public Result addItem(Item item, List<Sku> skus);
	
	/**
	 * 修改商品
	 * @param item
	 * @return
	 */
	public Result updateItem(Item item);
	
	/**
	 * 查看手机端商品介绍
	 * @param itemId
	 * @return
	 */
	public Result getappDescriptionInfo(Integer itemId);
	
	/**
	 * 根据商品编号获取商品图片
	 * @param itemId
	 * @return
	 */
	public Result getItemImages(Integer itemId);

	/**
	 * 上传商品图片
	 * @param request
	 * @return
	 */
	public Result imageUpload(HttpServletRequest request);
	
	/**
	 * 添加一个商品图片
	 * @param itemImage
	 * @return
	 */
	public Result addItemImage(ItemImage itemImage);
	
	/**
	 * 修改sku信息
	 * 价格、库存、最低起买量等
	 * @param sku
	 * @return
	 */
	public Result updateSku(Sku sku);

    /**
	 * 添加多个商品图片
	 * @param itemImageList
	 * @return
	 */
	public Result addItemImages(List<ItemImage> itemImageList);
}
