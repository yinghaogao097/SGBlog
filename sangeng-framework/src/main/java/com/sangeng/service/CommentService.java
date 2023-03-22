package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.Comment;


/**
 * 评论 业务层
 *
 * @author Achen
 */
public interface CommentService extends IService<Comment> {
    /**
     * 查询评论
     *
     *
     * @param commentType
     * @param articleId 文章id
     * @param pageNum   页码
     * @param pageSize  页面大小
     * @return
     */
    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    /**
     * 发表文章评论
     *
     * @param comment
     * @return
     */
    ResponseResult addComment(Comment comment);
}

