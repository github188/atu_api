package com.atu.api.service;

import com.atu.api.domain.CommentReply;
import com.atu.api.domain.query.CommentReplyQuery;
import com.atu.api.service.result.Result;

public interface CommentReplyService {
	/**
	 * 获取评论回复信息(分页)
	 * @param CommentQuery
	 * @return
	 */
	public Result getCommentReplyByPage(CommentReplyQuery commentReplyQuery);
	
	/**
	 * 添加评论回复信息
	 * @param CommentQuery
	 * @return
	 */
	public Result addCommentReply(CommentReply commentReply);
}
