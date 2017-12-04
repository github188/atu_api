package com.atu.api.web.controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atu.api.web.base.BaseController;
import com.atu.api.common.utils.JsonUtils;
import com.atu.api.domain.Item;
import com.atu.api.domain.ItemImage;
import com.atu.api.domain.ItemSkuList;
import com.atu.api.domain.Sku;
import com.atu.api.domain.query.ItemQuery;
import com.atu.api.service.ItemService;
import com.atu.api.service.result.Result;

@Controller
@RequestMapping("/sell/item")
public class SellerItemController extends BaseController {

	private ItemService itemService;
	
	/**
	 * 根据条件查询商品详细信息
	 * @param itemQuery 商品信息
	 * @return
	 */
	@RequestMapping(value = "getItemByItemQuery", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody Result getItemByItemQuery(ItemQuery itemQuery, HttpServletRequest request,
			HttpServletResponse response, ModelMap context) {
		itemQuery.setVenderUserId(getUserId(request));
		return itemService.getItemByPage(itemQuery);
	}

	/**
	 * 新增商品信息
	 * @param item 商品信息
	 * @param skus
	 * @return
	 */
	@RequestMapping(value = "addItem", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody Result addItem(Item item, String skus, HttpServletRequest request,
			HttpServletResponse response, ModelMap context) {
		Result result = new Result();

		ItemSkuList itemSkuList = null;
		try {
			itemSkuList = JsonUtils.readValue("{\"list\":" + skus + "}",
					ItemSkuList.class);
		} catch (Exception e) {
			result.setResultCode("1001");
			result.setResultMessage("skus格式不正确");
			return result;
		}

		if (itemSkuList == null || itemSkuList.getList() == null
				|| itemSkuList.getList().size() == 0) {
			result.setResultCode("1001");
			result.setResultMessage("skus格式不正确");
			return result;
		}

		List<Sku> list = itemSkuList.getList();
		for (int i = 0; i < list.size(); i++) {
			Sku sku = list.get(i);
			if (sku.getTbPrice() == null) {
				result.setResultCode("1001");
				result.setResultMessage("skus格式不正确，tbPrice不能为空");
				return result;
			}
			if (sku.getStock() == null) {
				result.setResultCode("1001");
				result.setResultMessage("skus格式不正确，stock不能为空");
				return result;
			}
			if (sku.getLeastBuy() == null) {
				sku.setLeastBuy(1);// 起买量默认为1
			}
			sku.setYn(1);
		}

		if (StringUtils.isBlank(item.getItemName())) {
			result.setResultCode("1001");
			result.setResultMessage("itemName不能为空");
			return result;
		}
		if (item.getItemType() == null) {
			result.setResultCode("1001");
			result.setResultMessage("itemType不能为空");
			return result;
		}
		if (item.getCategoryId1() == null) {
			result.setResultCode("1001");
			result.setResultMessage("categoryId1不能为空");
			return result;
		}
		if (item.getCategoryId2() == null) {
			result.setResultCode("1001");
			result.setResultMessage("categoryId2不能为空");
			return result;
		}
		if (item.getCategoryId3() == null) {
			result.setResultCode("1001");
			result.setResultMessage("categoryId2不能为空");
			return result;
		}
		if (item.getCategoryId4() == null) {
			result.setResultCode("1001");
			result.setResultMessage("categoryId2不能为空");
			return result;
		}
		if (item.getLeastBuy() == null) {
			item.setLeastBuy(1);// 默认起买量为1 数据库标注为冗余
		}

		item.setVenderUserId(getUserId(request));
		item.setItemStatus(1);// 上架
		item.setYn(1);
		item.setCreated(new Date());
		item.setOnShelfTime(new Date());

		return this.itemService.addItem(item, list);
	}

	/**
	 * 修改商品信息
	 * @param item 商品信息
	 * @return
	 */
	@RequestMapping(value = "updateItem", method = { RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody Result updateItem(Item item, HttpServletRequest request,
			HttpServletResponse response, ModelMap context) {
		Result result = new Result();
		if (item.getItemId() == null) {
			result.setResultCode("1001");
			result.setResultMessage("itemId不能为空");
			return result;
		}
		item.setModified(new Date());
		return this.itemService.updateItem(item);
	}

	/**
	 * 商品图片上传，返回图片地址
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "imageUpload", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result imageUpload(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return itemService.imageUpload(request);
	}
	
	/**
	 * 设置图片地址接口
	 * @param itemImage 商品图片
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "addItemImage", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result addItemImage(ItemImage itemImage, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Result result = new Result();
		if(StringUtils.isBlank(itemImage.getImageUrl())){
			result.setResultCode("1001");
			result.setResultMessage("imageUrl不能为空");
			return result;
		}
		if(itemImage.getItemId() == null){
			result.setResultCode("1001");
			result.setResultMessage("itemId不能为空");
			return result;
		}
		if(itemImage.getSortNumber() == null){
			result.setResultCode("1001");
			result.setResultMessage("sortNumber不能为空");
			return result;
		}
		return itemService.addItemImage(itemImage);
	}

	/**
	 * 批量设置图片地址接口
	 * @param imageUrls 图片地址
	 * @param itemId 商品ID
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
    @RequestMapping(value = "addItemImages", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result addItemImages(String imageUrls,Integer itemId, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Result result = new Result();
		if(StringUtils.isBlank(imageUrls)){
			result.setResultCode("1001");
			result.setResultMessage("imageUrls不能为空");
			return result;
		}
		if(itemId == null){
			result.setResultCode("1001");
			result.setResultMessage("itemId不能为空");
			return result;
		}
        List<ItemImage> itemImageList = toList(imageUrls, itemId);
		return itemService.addItemImages(itemImageList);
	}
    
    /**
     * 更改sku 价格、库存、最低起买量
     * @param sku
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "updateSku", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result updateSku(Sku sku, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Result result = new Result();
		if(sku.getSkuId() == null){
			result.setResultCode("1001");
			result.setResultMessage("skuId不能为空");
			return result;
		}
		return itemService.updateSku(sku);
	}

     //商品图片
     private List<ItemImage> toList(String imageUrls, Integer itemId) {
        List<ItemImage> itemImageList = new ArrayList<ItemImage>();
        String[] imageUrlArr = imageUrls.split(",");
        int i = 1;
        for(String imageUrl : imageUrlArr ){
            ItemImage itemImage = new ItemImage();
            itemImage.setItemId(itemId);
            itemImage.setSortNumber(i++);
            itemImage.setImageUrl(imageUrl);
            itemImageList.add(itemImage);
        }
        return itemImageList;
    }

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

}
