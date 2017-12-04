package com.atu.api.service;

import com.atu.api.domain.ConsigneeInfo;
import com.atu.api.service.result.Result;

public interface ConsigneeInfoService {
	/**
	 * 用户收货人信息列表查询
	 * @return
	 */
	public Result getConsigneeInfosByUserId(Integer userId);
	/**
	 * 用户收货人默认信息
	 * @return
	 */
	public Result getConsigneeInfosByDefault(Integer userId);
	/**
	 * 新增
	 * @return
	 */
	public Result addConsigneeInfo(ConsigneeInfo consigneeInfo);
	/**
	 * 修改
	 * @return
	 */
	public Result updateConsigneeInfo(ConsigneeInfo consigneeInfo);
	/**
	 * 删除
	 * @return
	 */
	public Result delConsigneeInfo(Integer consigneeId);
	/**
	 * 选择默认收货地址
	 * @return
	 */
	public Result choiceConsigneeInfo(ConsigneeInfo consigneeInfo);
	/**
	 * 根据Id查询收货人信息
	 * @param consigneeId
	 * @return
	 */
	public Result getByConsigneeId(Integer consigneeId);
}
