package com.atu.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.atu.api.dao.BusinessUserExtDao;
import com.atu.api.dao.CommentReplyDao;
import com.atu.api.domain.BusinessUserExt;
import com.atu.api.domain.CommentReply;
import com.atu.api.domain.query.CommentReplyQuery;
import com.atu.api.service.CommentReplyService;
import com.atu.api.service.result.Result;
import com.atu.api.service.utils.EcUtils;

@Service(value="commentReplyService")
public class CommentReplyServiceImpl implements CommentReplyService {
	private static final Logger log = LoggerFactory.getLogger(CommentReplyServiceImpl.class);
	
	private CommentReplyDao commentReplyDao;
	private BusinessUserExtDao businessUserExtDao;

	@Override
	public Result getCommentReplyByPage(CommentReplyQuery commentReplyQuery) {
		Result result = new Result();
		
		try{
			int total = commentReplyDao.countByCondition(commentReplyQuery);
			if(total == 0){
				result.setResultCode("6006");
				result.setResultMessage("评价回复列表不存在");
				return result;
			}
			
			int totalPage = total/commentReplyQuery.getPageSize() + 1;
			if(commentReplyQuery.getPageNo() > totalPage){
				commentReplyQuery.setPageNo(totalPage);
			}
			
			List<CommentReply> list = commentReplyDao.selectByConditionForPage(commentReplyQuery);
			
			for (CommentReply commentReply : list) {
				BusinessUserExt businessUserExt = businessUserExtDao.selectByUserId(commentReply.getUserId());
				if(businessUserExt != null){
					commentReply.setShopName(businessUserExt.getShopName());
				}
				if(commentReply.getParentId() != null){
					BusinessUserExt businessUserExts = businessUserExtDao.selectByUserId(commentReply.getParentId());
					if(businessUserExts != null){
						commentReply.setParentName(businessUserExts.getShopName());
					}
				}
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", total);
			map.put("totalPage", totalPage);
			map.put("curPage", commentReplyQuery.getPageNo());
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
	 * 添加评论回复信息
	 * ReplyId	评论id
	 * @param CommentQuery
	 * @return
	 */
	public Result addCommentReply(CommentReply commentReply){
		Result result = new Result();
		
		try{
			Integer ReplyId = commentReplyDao.insert(commentReply);
			
			if(null == ReplyId){
				result.setResult(false);
			}else{
				result.setResult(true);
				EcUtils.setSuccessResult(result);
			}
			
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	public void setCommentReplyDao(CommentReplyDao commentReplyDao) {
		this.commentReplyDao = commentReplyDao;
	}

	public void setBusinessUserExtDao(BusinessUserExtDao businessUserExtDao) {
		this.businessUserExtDao = businessUserExtDao;
	}

}
