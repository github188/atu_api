package com.atu.api.dao;

import java.util.List;

import com.atu.api.domain.CommentReply;
import com.atu.api.domain.query.CommentReplyQuery;

public interface CommentReplyDao {

	/**
	 * 根据条件获取信息
	 * @param CommentReplyQuery
	 * @return
	 */
	public List<CommentReply> selectByCondition(CommentReplyQuery commentReplyQuery);
	
	/**
	 * 根据条件获取信息(分页)
	 * @param CommentReplyQuery
	 * @return
	 */
	public List<CommentReply> selectByConditionForPage(CommentReplyQuery commentReplyQuery);
	
	/**
	 * 根据相应的条件获取信息的总数
	 * @param CommentReplyQuery
	 * @return
	 */
	public int countByCondition(CommentReplyQuery commentReplyQuery);
	
	/**
	 * 根据编号获取信息
	 * @param id
	 * @return
	 */
	public CommentReply selectById(Integer id);
	
	/**
	 * 添加信息
	 * @param commentReply
	 * @return
	 */
	public Integer insert(CommentReply commentReply);
	
	/**
	 * 更新信息
	 * @param comment
	 * @return
	 */
	public Integer update(CommentReply commentReply);

	/**
	 * 删除信息
	 * @param commentId
	 */
	public void delete(Integer id);
}
