package com.atu.api.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atu.api.web.base.BaseController;
import com.atu.api.domain.CommentReply;
import com.atu.api.domain.query.CommentReplyQuery;
import com.atu.api.service.CommentReplyService;
import com.atu.api.service.result.Result;

/** 评论回复信息  */

@Controller
@RequestMapping("commentReply")
public class CommentReplyController extends BaseController {
	
	
	private CommentReplyService commentReplyService;
	
	/**
	 * 获取评论回复信息(分页)
	 * @param commentReplyQuery 评论回复请求
	 * @return
	 */
	@RequestMapping(value="getCommentReplyByPage", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getCommentReplyByPage(CommentReplyQuery commentReplyQuery, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		
		if(commentReplyQuery.getCommentId() == null){
			result.setResultCode("1001");
			result.setResultMessage("commentId不能为空");
			return result;
		}
		
		return commentReplyService.getCommentReplyByPage(commentReplyQuery);
	}
	
	/**
	 * 添加评论回复信息
	 * @param commentReply 评论回复
	 * @return
	 */
	@RequestMapping(value="addCommentReply", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result addCommentReply(CommentReply commentReply, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		
		if(commentReply.getCommentId() == null){
			result.setResultCode("1001");
			result.setResultMessage("commentId不能为空");
			return result;
		}
		
		return commentReplyService.addCommentReply(commentReply);
	}

	public void setCommentReplyService(CommentReplyService commentReplyService) {
		this.commentReplyService = commentReplyService;
	}
	
}
