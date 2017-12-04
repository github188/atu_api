package demo;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atu.api.common.utils.MD5Util;

public class HttpTest {
	private static final Logger log = LoggerFactory.getLogger(HttpTest.class);
	
//	private static String domain = "www.tbny.net";
	private static String domain = "http://localhost:80";
//	private static String domain = "http://124.202.157.2:8001";
	private static String secret = "d18ed7feb9db4621b95da86943f7717f";
	private static String token = "a9ed53890379bad522c5edeabd1f648e83119d11a67dce98";
	//a9ed53890379bad522c5edeabd1f648e83119d11a67dce98(高程鹏userId=64) 386b0950d9dd3e23b37236bc71c3443e(商家userId=2)
	
	public static void main(String[] args) throws Exception {
<<<<<<< .mine
		String url = "/payOrder/payWX";
=======


		/** 商品分类相关  */
		// 获取所有一级分类信息
		String url = "/category/getAllParentCategory";
		// 根据父分类ID获取分类信息
//		String url = "/category/getCategoryByParentId?parentId=1";
		// 查询二级分类下属性销售属性信息
//		String url = "/category/getSalePropertiesByCategoryId?categoryId=358";
		
>>>>>>> .r3378
<<<<<<< .mine
		//我的优惠券
		//String url = "/orderToGetTradeNo/getWxTradeNo";
		//获取所有首页新品推荐商品信息
//		String url = "/indexRecommendItem/getIndexRecommendItemByPage?pageNo=1&pageSize=20";
		//获取所有首页热销商品信息
		//String url = "/indexSellItem/getIndexSellItemByPage?pageNo=1&pageSize=20";
		
		
		/** 商品搜索  */
		// 搜索商品
//		String url = "/item/getItemByPage?queryItem={\"pageNo\":1,\"pageSize\":10,\"itemName\":\"苹果\"}";
		// 查询商品产地
//		String url = "/item/getItemOrigin";
		// 搜索商铺
//		String url = "/businessUser/getBusinessUserByPage?shopName=12&pageNo=1&pageSize=4";
		// 根据商铺编号获取商品信息
//		String url = "/item/getItemsByVenderUserId?venderUserId=1&pageNo=2&pageSize=2";
		// 根据商品编号获取商品信息
//		String url = "/item/getItemByItemId?itemId=10";
		
		/** 收藏 商品信息  */
		// 添加收藏
//		String url = "/favorites/addFavorites?itemId=1";
		// 删除收藏
//		String url = "/favorites/delFavorites?favoritesId=4";
		// 分页查询收藏信息
	//	String url = "/favorites/selectByPage?pageNo=1&pageSize=10";
				
		/** 收藏商铺信息  */
		// 添加收藏
//		String url = "/favoritesSeller/addFavoritesSeller?venderUserId=4";
		// 删除收藏
//		String url = "/favoritesSeller/delFavoritesSeller?venderUserId=4";
		// 分页查询收藏信息
//		String url = "/favoritesSeller/selectByPage?pageNo=1&pageSize=10";
		
		/** 评论信息  */
		// 查询评论信息
//		String url = "/comment/getCommentByPage?pageNo=1&pageSize=3&skuId=43999";
		// 查询评论数量
//		String url = "/comment/getCommentCount?skuId=1";
		// 有用评论加1
//		String url = "/comment/updateUsefulCountById?commentId=1";
		// 查询评论回复信息
//		String url = "/commentReply/getCommentReplyByPage?pageNo=1&pageSize=5&commentId=1";
		// 添加评论回复信息
//		String url = "/commentReply/addCommentReply?commentId=1&userId=3&content=11111&parentId=2";
		
=======
>>>>>>> .r3378
		/** 购物车相关  */
		// 立即购买
//		String url = "/cartInfo/buyNow?skuId=106&skuNum=13";
		// 确认购物车订单
//		String url = "/cartInfo/confirmCartInfo?cartIds=[48]";
		// 添加购物车信息
//		String url = "/cartInfo/addCartInfo?skuId=140";
		// 修改购物车信息
//		String url = "/cartInfo/updateCartInfo?cartInfo=[{\"cartId\":1,\"skuNum\":20},{\"cartId\":2,\"skuNum\":10}]";
		// 删除购物车信息
//		String url = "/cartInfo/delCartInfo?cartIds=[28,29,27]";
		// 根据登陆用户获取购物车信息
//		String url = "/cartInfo/selectCartInfo";
		
		/** 收货人相关  */
		// 用户收货人信息列表查询
//		String url = "/consigneeInfo/getConsigneeInfosByUserId";
		// 查询默认收货人信息
//		String url = "/consigneeInfo/getConsigneeInfosByDefault";
		// 根据consigneeId获取收货人信息
//		String url = "/consigneeInfo/getByConsigneeId?consigneeId=42";
		// 选择收货人信息
//		String url = "/consigneeInfo/choiceConsigneeInfo?consigneeId=15";
		// 添加收货人信息
//		String url = "/consigneeInfo/addConsigneeInfo?consigneeName=石瑀2&consigneeProvince=1&consigneeCity=2805&consigneeCounty=2855&consigneeAddress=名流大厦806室&consigneeMobile=18801262807";
		// 修改收货人信息
//		String url = "/consigneeInfo/updateConsigneeInfo?consigneeName=aaa&consigneeProvince=1&consigneeCity=2&consigneeCounty=3&consigneeAddress=123asdc&consigneeMobile=13423123214";
		// 删除收货人信息
//		String url = "/consigneeInfo/delConsigneeInfo?consigneeId=34";
		
		/** 首页相关  */
		// 获取所有热搜词
//		String url = "/searchHotwords/selectSearchHotwords";
		// 首页轮播图接口
//		String url = "/indexImage/getIndexImages?fromWhere=1";
		// 获取所有首页促销商品信息
//		String url = "/indexPromItem/getIndexPromItemByPage?pageNo=1&pageSize=20";
		// 获取所有首页新品推荐商品信息
//		String url = "/indexRecommendItem/getIndexRecommendItemByPage?pageNo=1&pageSize=20&fromWhere=1";
		// 获取所有首页热销商品信息
//		String url = "/indexSellItem/getIndexSellItemByPage?pageNo=1&pageSize=20";
		// 获得首页折扣区top6促销商品信息
//		String url = "/promotionInfo/getIndexTop6DiscountPromotionInfo";
		// 获得首页产地特供区top6促销商品信息
//		String url = "/promotionInfo/getIndexTop6SpecialPromotionInfo";
		// 买家APP首页，促销-更多A标签分页查询
//		String url = "/promotionInfo/getPromotionInfos?specialFlag=1";

		
		/** 买家登陆 */
		//买家登陆
//		String url = "/user/buy/login?mobile=13488710918&password=e10adc3949ba59abbe56e057f20f883e";
		//发送注册验证码
//		String url = "/user/sendCode?mobile=13051158827&sendType=2";
		//买家注册
//		String url = "/user/buy/reg?shopName=jiangli&mobile=13051158827&password=123456&code=166028";
		//判断手机是否能够注册
//		String url = "/user/checkMobile?mobile=13911194204";
		//发送找回密码验证码
//		String url = "/user/sendCode?mobile=13051158827&sendType=1";
		//校验找回密码验证码
//		String url = "/user/validFindPwdCode?mobile=13051158827&code=187766";
		//买家修改密码
//		String url = "/userInfo/updatePwd?oldPwd=654321&newPwd=123456";
		//找回密码
//		String url = "/user/retrievePwd?k=fd5892c1229973924a7b0d150f161400&password=111111";
		//发送原手机号验证码
//		String url = "/user/sendCode?mobile=13051158827&sendType=3";
		//校验原手机号时验证码
//		String url = "/user/validOldMobileCode?mobile=13051158827&code=532830";
		//发送新手机号验证码
//		String url = "/user/sendCode?mobile=15101568617&sendType=4";
		//重置手机号
//		String url = "/user/resetMobile?k=9db06bcff9248837f86d1a6bcf41c9e7&code=111111&mobile=13051158827";
		
		/** 买家信息查询 */

		//依据商品ID查看商品详情
//		String url = "/item/getItemByItemId?itemId=2223&fromWhere=1";
		//依据商品ID查看商品介绍
//		String url = "/item/description/getItemDescriptionByItemId?itemId=3";
		//根据用户id获取用户信息
//      String url = "/user/getUserInfoByUserId?userId=87";
		//根据skuId查询促销信息
//		String url = "/promotionInfo/getPromotionInfoBySkuId?skuId=10001";
		//根据itemId查询促销信息
//		String url = "/promotionInfo/getPromotionInfoByItemId?itemId=4";
		
		/** 买家订单相关 */
		//下单
//		String url = "/orderInfo/submit?skus=[{\"skuId\":1,\"itemId\":1,\"num\":2}]&orderType=1&consigneeName=曹威&consigneeProvince=1&consigneeCity=72&consigneeCounty=4213&consigneeAddress=名流未来大厦&consigneeMobile=18613825855&remark=";
		//用户所属订单列表查看（细分）
//		String url = "/orderInfo/getOrderInfosByUserId";
		//根据orderId查看商品详情
//		String url = "/orderInfo/getOrderInfoByOrderIdAndVenderUserId?orderId=200286";
		//用户收货确认
//		String url = "/orderInfo/confirmGetGoods?orderId=10080124";
		//生成结算订单号
//		String url = "/orderInfo/createTradeNo?orderId=10801";
		//订单支付接口
//		String url = "/paymentInfo/addPaymentInfo?orderId=4&orderPayType=1&paymentMode=1&paymentInfoType=1&paymentMoney=100&paymentNumber=1&busiPartner=a&dtOrder=1&validOrder=1&settleDate=1&bankName=1&bankCode=1";
		//订单支付成功接口
//		String url = "/paymentInfo/addPaymentInfo?orderId=4&orderPayType=1&paymentMode=1&paymentInfoType=2&paymentMoney=100&paymentNumber=1&busiPartner=a&dtOrder=1&validOrder=1&settleDate=1&bankName=1&bankCode=1";
		//尾款支付接口
//		String url = "/paymentInfo/addPaymentInfo?orderId=4&orderPayType=2&paymentMode=1&paymentInfoType=1&paymentMoney=100&paymentNumber=1&busiPartner=a&dtOrder=1&validOrder=1&settleDate=1&bankName=1&bankCode=1";
		//尾款支付成功接口
//		String url = "/paymentInfo/addPaymentInfo?orderId=4&orderPayType=2&paymentMode=1&paymentInfoType=2&paymentMoney=100&paymentNumber=1&busiPartner=a&dtOrder=1&validOrder=1&settleDate=1&bankName=1&bankCode=1";
		//查询支付接口
//		String url = "/paymentInfo/getPaymentInfos?orderId=10080020&paymentInfoType=1&orderPayType=1";
		//协议支付确认支付
//		String url = "/paymentInfo/submit?mer_cust_id=111111&usr_pay_agreement_id=222222&orderId=10000820&verify_code=15478";
		//发送一键支付短信验证码
//		String url = "/paymentInfo/sendCode?mer_cust_id=111111&usr_pay_agreement_id=222222&orderId=10000820";
		
		/** 买家商品搜索  */
		// 搜索商品
//		String url = "/item/getItemByPage?queryItem={\"pageNo\":1,\"pageSize\":10,\"itemName\":\"苹果\"}";
		// 查询商品产地
//		String url = "/item/getItemOrigin";
		// 依据ID查询商品下SKU信息
//		String url = "/item/getSkusByItemId?itemId=3";
		// 根据商品编号获取商品信息
//		String url = "/item/getItemByItemId?itemId=2223&fromWhere=1";
		// 根据商铺编号获取商品信息
//		String url = "/item/getItemsByVenderUserId?venderUserId=1&pageNo=2&pageSize=2";
		// 依据商品ID查询商品轮播图
//		String url = "/item/getItemImages?itemId=40";
		// 搜索商铺
//		String url = "/businessUser/getBusinessUserByPage?shopName=12&pageNo=1&pageSize=4";
		// 依据SKU_ID查看SKU信息
//		String url = "/sku/getSkuBySkuId?skuId=150";
		
		/** 商家登陆 */
		//卖家注册
//	    String url = "/user/sell/reg?mobile=13333333338&password=123456&code=1113&shopName=asd1234&fromWhere=1";
		//发送注册验证码
//		String url = "/user/sendCode?mobile=13051158827&sendType=2";
		//卖家登陆
//		String url = "/user/sell/login?mobile=15313539790&password=e10adc3949ba59abbe56e057f20f883e";
		//判断手机是否能够注册
//		String url = "/user/checkMobile?mobile=13911194204";	
		//发送找回密码验证码
//		String url = "/user/sendCode?mobile=13051158827&sendType=1";
		//找回密码
//		String url = "/user/findPwd?k=fd5892c1229973924a7b0d150f161400&password=111111";
		//判断商家名称是否可以注册
//		String url = "/user/checkShopName?shopName=海博纳";
		//根据用户ID获取用户信息
//		String url = "/user/getUserInfoByUserId?userId=64";
		
		/** 商家信息查询 */
        //根据商户ID,查看商家信息  1为erp用户，2为卖家，3为买家
//      String url = "/businessUser/getBusinessUserByUserId?userId=87";
		//依据商家ID获取所有该商家商品信息
//		String url = "/sell/item/getItemsByVenderUserId";
		//根据条件查询商品详情
//		String url = "/sell/item/getItemByItemQuery";
		//记账功能
//		String url = "/sellerBookkeeping/addSellerBookkeeping?linkman=sl&mobile=13242312311&paymentMoney=1000&tradeMoney=2000&companyName=tbny&itemPrice=50&itemName=asd123";
		//分页查询记账功能接口
//		String url = "/sellerBookkeeping/getSellerBookkeepingByPage?pageNo=1&startTime="+URLEncoder.encode(" 2015-04-08 11:11:11", "utf-8")+"&endTime="+URLEncoder.encode(" 2015-04-10 10:35:00", "utf-8");
		//统计金额接口
//		String url = "/sellerBookkeeping/selectSellerBookkeepingForCountMoney?startTime="+URLEncoder.encode("2015-04-08 11:11:11", "utf-8")+"&endTime="+URLEncoder.encode("2015-04-10 10:35:00", "utf-8");
	
		/** 商家订单管理 */
		//商家所属订单列表查看（分页）
//		String url = "/orderInfo/getOrderInfosByVenderUserId";
		//根据订单号和商家id获取订单详细信息
//		String url = "/orderInfo/getOrderInfoByOrderIdAndVenderUserId?orderId=10080123";
        //根据订单号和用户id获取订单状态信息
//		String url = "/orderInfo/getOrderStatusByOrderIdAndUserId?orderId=10000200";
		//根据orderId修改价格
//		String url = "/orderInfo/UpdateOderInfoPriceByOrderId?oughtPayMoney=5&orderId=200272";
		//商家确认收款
//		String url = "/orderInfo/confirmGetPrice?orderId=10080007";
		//商家确认发货并补录配送信息
//		String url = "/orderInfo/confirmSendOut?orderId=10080020";
		//商家订单完成确认
//		String url = "/orderInfo/orderSuccess?orderId=10080020";
		//商家订单锁定
//		String url = "/orderInfo/lockOrder?orderId=10080020&lockReason=买家要求延迟发货";
		//商家订单解锁
//		String url = "/orderInfo/unlockOrder?orderId=10080020";
		//商家补录订单金额接口
//		String url = "/sellerEntry/addSellerEntry?orderId=10080020&orderPayType=1&paymentMode=2&paymentMoney=1000&busiPartner=109001&remark=尽快到达";
		//商家查询支付接口
//		String url = "/sellerEntry/getSellerEntrys?orderId=10080020&orderPayType=1";
	
		/** 商家商品管理 */
		//查看促销详细信息
//		String url = "/promotionInfo/getPromotionInfoByPromotionId?promotionId=24";
		//更改sku 价格、库存、最低起买量
//		String url = "/sell/item/updateSku?skuId=1&leastBuy=2&tbPrice=101&stock=1001";
		//新增商品信息
//		String url = "/sell/item/addItem?itemName=aaa&itemType=1&categoryId1=1&categoryId2=5&skus=[{\"tbPrice\":100,\"stock\":1000},{\"tbPrice\":100,\"stock\":1000}]";
		//修改商品信息
//		String url = "/sell/item/updateItem?itemId=114&itemType=2";
		//商品图片上传，返回图片地址
//		String url = "/sell/item/imageUpload";
		//设置图片地址接口
//		String url = "/sell/item/addItemImage?itemId=150&imageUrl=http://www.tbny.net/img/2015/4/6/ph684396.jpg&sortNumber=1";
        //批量设置图片地址接口
//		String url = "/sell/item/addItemImages?itemId=150&imageUrls=www.123.com,www.456.com";
		//商家添加商品介绍
//		String url = "/sell/item/description/addItemDescription?itemId=3&appDescriptionInfo=33";
		//商家修改商品介绍
//		String url = "/sell/item/description/updateItemDescription?itemId=3&appDescriptionInfo=44";
		//商家修改商品介绍
//		String url = "/sell/item/description/insertOrUpdate?itemId=11&appDescriptionInfo=66";
		//依据商家ID查询商家所属促销列表查看
//		String url = "/sell/promotionInfo/getPromotionInfos";
		//新增促销信息
//		String url = "/sell/promotionInfo/addPromotionInfo?promotionName=421&itemId=3&promotionType=1&purchaseNumberMin=1&purchaseNumberMax=1000";
		//查看促销详细信息
//		String url = "/sell/promotionInfo/updatePromotionInfo?promotionId=24&promotionName=222";
		//商家关闭促销
//		String url = "/sell/promotionInfo/closePromotion?promotionId=24";
		//依据属性ID及商家ID查询自定义属性值信息列表
//		String url = "/sell/category/getPropertyValuesByPropertyId?propertyId=640";
		//添加自定义属性值信息
//		String url = "/sell/category/addPropertyValue?propertyId=2&propertyValueName=shangpin&sortNumber=1";
		//修改自定义属性值信息
//		String url = "/sell/category/updatePropertyValue?propertyValueId=680&sortNumber=2";
		
		/** 服务相关  */
		//获取所有一级分类信息
//		String url = "/serviceCategory/getAllParentCategory";
		//根据父分类ID获取分类信息
//		String url = "/serviceCategory/getCategoryByParentId?parentId=2";
		//根据分类ID获取服务信息
//		String url = "/serviceInfo/getServiceInfoByPage?categoryId=7&pageNo=1&pageSize=2";
		
		/** 查询地址信息 */
		//获取一级地址
//		String url = "/address/getProvinces";
		//获取二级地址
//		String url = "/address/getCities?province=1";
		//获取三级地址
//		String url = "/address/getCounties?city=83";
		
		/** 优惠券 */
		//我的优惠券
//		String url = "/coupon/init";
		//使用我的优惠券
//		String url = "/coupon/use";

		
		/** 评论信息  */
		// 查询评论信息
//		String url = "/comment/getCommentByPage?pageNo=1&pageSize=3&skuId=43999";
		// 查询评论数量
//		String url = "/comment/getCommentCount?skuId=1";
		// 有用评论加1
//		String url = "/comment/updateUsefulCountById?commentId=1";
		// 查询评论回复信息
//		String url = "/commentReply/getCommentReplyByPage?pageNo=1&pageSize=5&commentId=1";
		// 添加评论回复信息
//		String url = "/commentReply/addCommentReply?commentId=1&userId=3&content=11111&parentId=2";
		
		/** 收藏,关注 商品信息  */
		// 买家添加收藏
//		String url = "/favorites/addFavorites?itemId=1";
		// 买家删除收藏
//		String url = "/favorites/delFavorites?favoritesId=4";
		// 买家分页查询收藏信息
//		String url = "/favorites/selectByPage?pageNo=1&pageSize=10";
		// 买家添加关注
//		String url = "/favorites/addFavorites?itemId=2";
		// 买家判断是否关注商品
//		String url = "/favorites/checkItemFavorites?itemId=167";
		// 买家批量判断是否关注商品
//		String url = "/favorites/checkItemsFavorites?itemId=167,168,169";
		// 买家查询关注
//		String url = "/favorites/getFavoritesByFavoritesId?favoritesId=1";
		// 买家分页查询关注
//		String url = "/favorites/getFavoritesByPage";
		// 买家删除关注
//		String url = "/favorites/delFavoritesBtFavoritesId?favoritesId=5";
		// 添加收藏商铺信息
//		String url = "/favoritesSeller/addFavoritesSeller?venderUserId=4";
		// 删除收藏商铺信息
//		String url = "/favoritesSeller/delFavoritesSeller?venderUserId=4";
		// 分页查询收藏商铺信息
//		String url = "/favoritesSeller/selectByPage?pageNo=1&pageSize=10";
		
		/** 附加功能相关  */
		//页面
//		String url = "/user/demo";
		//清楚缓存
//		String url = "/user/test?mobile=13051158827";
        //获得最新版本
//      String url = "/v/getVersion?versionType=1&versionNo=3";
		
		
		String dUrl = "";
		String data = "";
		//System.out.println(domain + url+"&" +getSign() + "&token="+token);
		if(url.indexOf("?") <= 0){
			System.out.println(domain + url+"?" +getSign() + "&token="+token);
			dUrl = domain + url;
			data = getSign() + "&token="+token;
		}else{
			System.out.println(domain + url+"&" +getSign() + "&token="+token);
			dUrl = domain + url.substring(0, url.indexOf("?"));
			data = url.substring(url.indexOf("?")+1, url.length()) + "&" + getSign() + "&token="+token;
		}
		System.out.println(HttpUtils.httpPostData(dUrl, data, null));
		
		log.error("OK");                                                                       
	}
	
	private static String getSign() throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timestamp = sdf.format(new Date());
		String v = "1.0";
		String sign = MD5Util.md5Hex(secret + timestamp + v + secret).toUpperCase();
		
		StringBuilder sb = new StringBuilder();
		sb.append("timestamp=").append(URLEncoder.encode(timestamp, "utf-8"));
		sb.append("&").append("v=").append(v);
		sb.append("&").append("sign=").append(sign);
		
		return sb.toString();
	}
	
	
}
