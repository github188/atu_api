package com.atu.api.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.atu.api.dao.SearchHotwordsDao;
import com.atu.api.domain.SearchHotwords;
import com.atu.api.domain.query.SearchHotwordsQuery;
import com.atu.api.service.SearchHotwordsService;
import com.atu.api.service.result.Result;
import com.atu.api.service.utils.EcUtils;

@Service("searchHotwordsService")
public class SearchHotwordsServiceImpl implements SearchHotwordsService {
	private static final Logger log = LoggerFactory.getLogger(SearchHotwordsServiceImpl.class);
	
	private SearchHotwordsDao searchHotwordsDao;

	@Override
	public Result selectSearchHotwords() {
		Result result = new Result();
		
		try{
			SearchHotwordsQuery searchHotwordsQuery = new SearchHotwordsQuery();
			searchHotwordsQuery.setYn(1);
			List<SearchHotwords> list = searchHotwordsDao.selectByCondition(searchHotwordsQuery);
			
			if(list.isEmpty() || list == null){
				result.setResultCode("3008");
				result.setResultMessage("热搜词列表不存在");
				return result;
			}
			
			String[] sh = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				sh[i] = list.get(i).getHotwordName();
			}
			
			result.setResult(sh);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		
		return result;
	}

	public void setSearchHotwordsDao(SearchHotwordsDao searchHotwordsDao) {
		this.searchHotwordsDao = searchHotwordsDao;
	}
}
