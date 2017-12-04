package com.atu.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.atu.api.dao.CommentDao;
import com.atu.api.dao.CommentReplyDao;
import com.atu.api.domain.Comment;
import com.atu.api.domain.query.CommentQuery;
import com.atu.api.domain.query.CommentReplyQuery;
import com.atu.api.domain.vo.CommentCountVO;
import com.atu.api.service.CommentService;
import com.atu.api.service.result.Result;
import com.atu.api.service.utils.EcUtils;

@Service(value="commentService")
public class CommentServiceImpl implements CommentService {
	private static final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);
	
	private CommentDao commentDao;
	private CommentReplyDao commentReplyDao;

	/**
	 * 获取评论信息(分页)
	 */
	@Override
	public Result getCommentByPage(CommentQuery commentQuery) {
		Result result = new Result();
		
		try{
			Integer type = commentQuery.getType();
			if(type == null){			//全部
				commentQuery.setMinScore(1);
				commentQuery.setMaxScore(5);
			}else{
				if(type == 1){			// 差评
					commentQuery.setMinScore(5);
					commentQuery.setMaxScore(5);
				}else if(type == 2){	//中评
					commentQuery.setMinScore(3);
					commentQuery.setMaxScore(4);
				}else if(type == 3){	//好评
					commentQuery.setMinScore(1);
					commentQuery.setMaxScore(2);
				}else{					//全部
					commentQuery.setMinScore(1);
					commentQuery.setMaxScore(5);
				}
			}
			int total = commentDao.countByCondition(commentQuery);
			if(total == 0){
				result.setResultCode("6005");
				result.setResultMessage("评价列表不存在");
				return result;
			}
			
			int totalPage = total/commentQuery.getPageSize() + 1;
			if(commentQuery.getPageNo() > totalPage){
				commentQuery.setPageNo(totalPage);
			}
			
			List<Comment> list = commentDao.selectByConditionForPage(commentQuery);
			
			CommentReplyQuery commentReplyQuery = new CommentReplyQuery();
			for (Comment comment : list) {
				commentReplyQuery.setCommentId(comment.getId());
				Integer replyCount = commentReplyDao.countByCondition(commentReplyQuery);
				comment.setReplyCount(replyCount);
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", total);
			map.put("totalPage", totalPage);
			map.put("curPage", commentQuery.getPageNo());
			map.put("list", list);
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	/**
	 * 获取评论总数、好评数、中评数、差评数
	 */
	@Override
	public Result getCommentCount(CommentQuery commentQuery) {
		Result result = new Result();
		
		try{
			// 总评价数
			int totalCount = commentDao.countByCondition(commentQuery);
			
			// 好评评价数
			commentQuery.setMaxScore(5);
			commentQuery.setMinScore(5);
			int positiveCount = commentDao.countByCondition(commentQuery);
			
			// 中评评价数
			commentQuery.setMaxScore(4);
			commentQuery.setMinScore(3);
			int neutralCount = commentDao.countByCondition(commentQuery);
			
			// 差评评价数
			commentQuery.setMaxScore(2);
			commentQuery.setMinScore(1);
			int negativeCount = commentDao.countByCondition(commentQuery);
			
			CommentCountVO commentCountVO = new CommentCountVO();
			commentCountVO.setTotalCount(totalCount);
			commentCountVO.setPositiveCount(positiveCount);
			commentCountVO.setNegativeCount(negativeCount);
			commentCountVO.setNeutralCount(neutralCount);
			
			result.setResult(commentCountVO);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
		
	}
	
	/**
	 * 修改评价的有用总数
	 */
	@Override
	public Result updateUsefulCountById(Integer commentId){
		Result result = new Result();
		
		try{
			commentDao.updateUsefulCountById(commentId);
			
			result.setResult(true);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}

	public void setCommentReplyDao(CommentReplyDao commentReplyDao) {
		this.commentReplyDao = commentReplyDao;
	}

}
