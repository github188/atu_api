package com.atu.api.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atu.api.web.base.BaseController;
import com.atu.api.domain.query.CommentQuery;
import com.atu.api.service.CommentService;
import com.atu.api.service.result.Result;

/** 评论，评价信息  */

@Controller
@RequestMapping("comment")
public class CommentController extends BaseController {
	
	
	private CommentService commentService;
	
	/**
	 * 获取评论信息总数
	 * @param commentQuery 评论申请
	 * @return
	 */
	@RequestMapping(value="getCommentCount", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getCommentCount(CommentQuery commentQuery, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		
		if(commentQuery.getSkuId() == null){
			result.setResultCode("1001");
			result.setResultMessage("skuId不能为空");
			return result;
		}
		
		return commentService.getCommentCount(commentQuery);
	}
	
	/**
	 * 获取评论信息(分页)
	 * @param commentQuery 评价请求
	 * @return
	 */
	@RequestMapping(value="getCommentByPage", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getCommentByPage(CommentQuery commentQuery, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		
		if(commentQuery.getSkuId() == null){
			result.setResultCode("1001");
			result.setResultMessage("skuId不能为空");
			return result;
		}
		
		return commentService.getCommentByPage(commentQuery);
	}
	
	/**
	 * 修改评价的有用总数
	 * @param commentId 评价id
	 * @return
	 */
	@RequestMapping(value="updateUsefulCountById", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result updateUsefulCountById(Integer commentId, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		
		if(commentId == null){
			result.setResultCode("1001");
			result.setResultMessage("commentId不能为空");
			return result;
		}
		
		return commentService.updateUsefulCountById(commentId);
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}
	
}
