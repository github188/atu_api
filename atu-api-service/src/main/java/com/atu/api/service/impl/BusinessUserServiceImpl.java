package com.atu.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.atu.api.dao.AddressDao;
import com.atu.api.dao.BusinessUserExtDao;
import com.atu.api.dao.UserInfoDao;
import com.atu.api.domain.BusinessUserExt;
import com.atu.api.domain.UserInfo;
import com.atu.api.domain.query.BusinessUserExtQuery;
import com.atu.api.domain.vo.BusinessUserExtVO;
import com.atu.api.service.BusinessUserService;
import com.atu.api.service.result.Result;
import com.atu.api.service.utils.EcUtils;


@Service(value="businessUserService")
public class BusinessUserServiceImpl implements BusinessUserService {
    private static final Logger log = LoggerFactory.getLogger(BusinessUserServiceImpl.class);
    /** 商户信息DAO */
    private BusinessUserExtDao businessUserExtDao;
    private AddressDao addressDao;
    private UserInfoDao userInfoDao;

    //根据商家用户名查询商铺信息
	@Override
	public Result getBusinessUserByPage(BusinessUserExtQuery businessUserExtQuery) {
		//实例化result
		Result result = new Result();
		try{
			//商家用户信息请求Status店铺状态为2
			businessUserExtQuery.setStatus(2);
			//总页数等于从数据库中使用模糊查询，查询出的商户数量
			int total = businessUserExtDao.countByCondition(businessUserExtQuery);
			//判断总页数是否为空
			if(total == 0){
				result.setResultCode("8001");
				result.setResultMessage("商铺列表不存在");
				return result;
			}
			//获取分页数（计算公式：总页数除以页码条数加一）
			int totalPage = total/businessUserExtQuery.getPageSize() + 1;
			//判断分页  （ 判断条件：页码PageNo为1大于分页数totalPage）
			if(businessUserExtQuery.getPageNo() > totalPage){
				//总页数totalPage赋给页码PageNo
				businessUserExtQuery.setPageNo(totalPage);
			}
			
			//创建list集合
			List<BusinessUserExtVO> list = new ArrayList<BusinessUserExtVO>();
			//根据用户请求信息从数据库business_user_ext表中查询出商家用户信息用buList接收
			List<BusinessUserExt> buList = businessUserExtDao.selectByConditionForPage(businessUserExtQuery);
			//foreach循环 把buList接收到的信息赋给businessUserExt
			for (BusinessUserExt businessUserExt : buList) {
				//实例化domain.vo.BusinessUserExtVO类
				BusinessUserExtVO businessUserExtVO = new BusinessUserExtVO();
				//把商家信息businessUserExt中的自增ID赋给商铺信息VO类中ID
				businessUserExtVO.setId(businessUserExt.getId());
				//把商家信息businessUserExt中的店铺名称赋给商铺信息VO类中商铺名称
				businessUserExtVO.setShopName(businessUserExt.getShopName());
				//把商家信息businessUserExt中的店铺图片赋给商铺信息VO类中商铺图片
				businessUserExtVO.setShopImage(businessUserExt.getShopImage());
				//把businessUserExtVO接收到的信息添加到list集合中
				list.add(businessUserExtVO);
			}
			
			//实例化map集合
			Map<String, Object> map = new HashMap<String, Object>();
			//在map中插入总页数
			map.put("total", total);
			//在map中插入分页数
			map.put("totalPage", totalPage);
			//在map中插入当前页码
			map.put("curPage", businessUserExtQuery.getPageNo());
			//在map中插入出商家用户信息
			map.put("itemList", buList);
			//把map的值赋给result
			result.setResult(map);
			//成功输出result(success:true,resultCode:200,resultMessage:"",result)
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			//失败输出result(success:false,resultCode:500,resultMessage:""服务异常，请稍后重试",result：)
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	//根据商户ID,获得商户信息
    @Override
    public Result getBusinessUserExtByUserID(Integer userId) {
    	//实例化Result类
        Result result = new Result();
		try{
            log.info("BusinessUserServiceImpl.getBusinessUserExtByUserID.userId:"+userId);
            //从数据库中business_user_ext表依据商家用户ID：userId查询到的信息用businessUserExt接收
            BusinessUserExt businessUserExt = businessUserExtDao.selectByUserId(userId);
            //ui为从数据库中user_info表根据用户ID：userId查询出的信息
            UserInfo ui = userInfoDao.selectByUserId(userId);
            //判断商家信息中的商家用户信息是否为空
            if( null == businessUserExt ){
				result.setResultCode("4001");
				result.setResultMessage("用户不存在");
				return result;
			}
            //判断ui是否为空
            if( null != ui ){
            	//如果不是空，将用户手机号赋给商家用户手机号
                businessUserExt.setMobile(ui.getMobile());
            }
            //判断供货地省份是否为空 
            if( null != businessUserExt.getSupplyProvince() ){
            	//如果不是空，从数据库address表中根据供货地省份查询得到的地址名称赋给供货地省份名称
                businessUserExt.setSupplyProvinceName(addressDao.selectByAddressId(businessUserExt.getSupplyProvince()).getAddressName());
            }
            //判断供货地市区是否为空
            if( null != businessUserExt.getSupplyCity() ){
            	//如果不是空，从数据库address表中根据供货地市区查询得到的地址名称赋给供货地市区名称
                businessUserExt.setSupplyCityName(addressDao.selectByAddressId(businessUserExt.getSupplyCity()).getAddressName());
            }
            //判断供货地县是否为空
            if( null != businessUserExt.getSupplyCounty() ){
            	//如果不是空，从数据库address表中根据供货地县查询得到的地址名称赋给供货地县名称
                businessUserExt.setSupplyCountyName(addressDao.selectByAddressId(businessUserExt.getSupplyCounty()).getAddressName());
            }
            log.info("BusinessUserServiceImpl.getBusinessUserExtByUserID.businessUserExt==="+businessUserExt);
            //实例化Map集合
			Map<String, Object> map = new HashMap<String, Object>();
			//给Map赋值
			map.put("businessUser", businessUserExt);
			//把map的值赋给result
			result.setResult(map);
			//成功输出result(success:true,resultCode:200,resultMessage:"",result)
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			//失败输出result(success:false,resultCode:500,resultMessage:""服务异常，请稍后重试",result：)
			log.error("BusinessUserServiceImpl.getBusinessUserExtByUserID.userId:"+userId, e);
			EcUtils.setExceptionResult(result);
		}
		return result;
    }

    
    public void setBusinessUserExtDao(BusinessUserExtDao businessUserExtDao) {
        this.businessUserExtDao = businessUserExtDao;
    }

    public void setAddressDao(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    public void setUserInfoDao(UserInfoDao userInfoDao) {
        this.userInfoDao = userInfoDao;
    }
}
