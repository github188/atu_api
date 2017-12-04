package com.atu.api.service;

import com.atu.api.domain.SellerEntry;
import com.atu.api.domain.query.SellerEntryQuery;
import com.atu.api.service.result.Result;

public interface SellerEntryService {
	/**
	 * 商家补录金额
	 * @return
	 */
	public Result addSellerEntry(SellerEntry sellerEntry);
	
	/**
	 * 查看补录信息
	 * @param sellerEntryQuery
	 * @return
	 */
	public Result getSellerEntrys(SellerEntryQuery sellerEntryQuery);
}
