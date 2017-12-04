package com.atu.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.atu.api.dao.SearchHotwordsDao;
import com.atu.api.domain.SearchHotwords;
import com.atu.api.domain.query.SearchHotwordsQuery;

@SuppressWarnings("unchecked")
public class SearchHotwordsDaoImpl extends SqlMapClientTemplate implements SearchHotwordsDao {

	@Override
	public List<SearchHotwords> selectByCondition(SearchHotwordsQuery searchHotwordsQuery) {
		return (List<SearchHotwords>)queryForList("SearchHotwords.selectByCondition",searchHotwordsQuery);
	}

}
