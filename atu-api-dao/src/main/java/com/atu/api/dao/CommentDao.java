package com.atu.api.dao;

import java.util.List;

import com.atu.api.domain.Comment;
import com.atu.api.domain.query.CommentQuery;

public interface CommentDao {
	/**
	 * 根据相应的条件查询信息
	 * @param commentQuery
	 * @return
	 */
	public List<Comment> selectByCondition(CommentQuery commentQuery);
	
	/**
	 * 根据相应的条件查询信息-分页查询
	 * @param commentQuery
	 * @return
	 */
	public List<Comment> selectByConditionForPage(CommentQuery commentQuery);
	
	/**
	 * 根据相应的条件查询满足条件的信息的总数
	 * @param commentQuery
	 * @return
	 */
	public int countByCondition(CommentQuery commentQuery);
	
	/**
	 * 根据相应的条件查询信息
	 * @param commentId
	 * @return
	 */
	public Comment selectById(Integer commentId);
	
	/**
	 * 添加信息
	 * @param comment
	 * @return
	 */
	public Integer insert(Comment comment);
	
	/**
	 * 更新信息
	 * @param comment
	 * @return
	 */
	public Integer update(Comment comment);

	/**
	 * 根据id主键删除信息
	 * @param commentId
	 */
	public void delete(Integer commentId);
	
	/**
	 * 修改评价的有用总数
	 * @param Comment
	 * @return
	 */
	public void updateUsefulCountById(Integer commentId);
}
