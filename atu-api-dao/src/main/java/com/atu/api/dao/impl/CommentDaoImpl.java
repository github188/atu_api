package com.atu.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.atu.api.dao.CommentDao;
import com.atu.api.domain.Comment;
import com.atu.api.domain.query.CommentQuery;

@SuppressWarnings("unchecked")
public class CommentDaoImpl extends SqlMapClientTemplate implements CommentDao {
	
	@Override
	public List<Comment> selectByCondition(CommentQuery commentQuery) {
		return (List<Comment>)queryForList("Comment.selectByCondition", commentQuery);
	}
	
	@Override
	public List<Comment> selectByConditionForPage(CommentQuery commentQuery) {
		return (List<Comment>)queryForList("Comment.selectByConditionForPage", commentQuery);
	}
	
	@Override
	public int countByCondition(CommentQuery commentQuery) {
		return (Integer)queryForObject("Comment.countByCondition", commentQuery);
	}
	
	@Override
	public Comment selectById(Integer favoritesId) {
		return (Comment)queryForObject("Comment.selectById",favoritesId);
	}

	@Override
	public Integer insert(Comment comment) {
		return (Integer)insert("Comment.insert",comment);
	}
	
	@Override
	public Integer update(Comment comment) {
		return update("Comment.update", comment);
	}

	@Override
	public void delete(Integer favoritesId) {
		delete("Comment.delete", favoritesId);
	}

	@Override
	public void updateUsefulCountById(Integer commentId) {
		update("Comment.updateUsefulCountById", commentId);
	}

}
