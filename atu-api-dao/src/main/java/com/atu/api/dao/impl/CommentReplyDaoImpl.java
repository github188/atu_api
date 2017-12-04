package com.atu.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.atu.api.dao.CommentReplyDao;
import com.atu.api.domain.CommentReply;
import com.atu.api.domain.query.CommentReplyQuery;

@SuppressWarnings("unchecked")
public class CommentReplyDaoImpl extends SqlMapClientTemplate implements CommentReplyDao {
	
	@Override
	public List<CommentReply> selectByCondition(CommentReplyQuery commentReplyQuery) {
		return (List<CommentReply>)queryForList("CommentReply.selectByCondition", commentReplyQuery);
	}

	@Override
	public List<CommentReply> selectByConditionForPage(
			CommentReplyQuery commentReplyQuery) {
		return (List<CommentReply>)queryForList("CommentReply.selectByConditionForPage", commentReplyQuery);
	}

	@Override
	public int countByCondition(CommentReplyQuery commentReplyQuery) {
		return (Integer)queryForObject("CommentReply.countByCondition", commentReplyQuery);
	}

	@Override
	public CommentReply selectById(Integer id) {
		return (CommentReply) queryForObject("CommentReply.selectById",id);
	}
	
	@Override
	public Integer insert(CommentReply commentReply) {
		return (Integer) insert("CommentReply.insert", commentReply);
	}

	@Override
	public Integer update(CommentReply commentReply) {
		return update("CommentReply.update", commentReply);
	}

	@Override
	public void delete(Integer id) {
		delete("CommentReply.delete", id);
	}

}
