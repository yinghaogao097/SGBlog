package com.sangeng.controller;

import com.sangeng.constants.SystemConstants;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddCommentDTO;
import com.sangeng.domain.entity.Comment;
import com.sangeng.service.CommentService;
import com.sangeng.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 评论接口
 *
 * @author Achen
 */
@RestController
@RequestMapping("/comment")
@Api(tags = "评论", value = "评论相关接口")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 查询评论
     *
     * @param articleId 文章id
     * @param pageNum   页码
     * @param pageSize  页面大小
     * @return
     */
    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT, articleId, pageNum, pageSize);
    }

    /**
     * 查询友链评论
     *
     * @param pageNum  页码
     * @param pageSize 页面大小
     * @return
     */
    @GetMapping("/linkCommentList")
    @ApiOperation(value = "友链评论列表", notes = "获取一页友链评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小")
    })
    public ResponseResult linkCommentList(Integer pageNum, Integer pageSize) {
        return commentService.commentList(SystemConstants.LINK_COMMENT, null, pageNum, pageSize);
    }

    /**
     * 发表文章评论
     *
     * @param addCommentDTO
     * @return
     */
    @PostMapping
    public ResponseResult addComment(@RequestBody AddCommentDTO addCommentDTO) {
        Comment comment = BeanCopyUtils.copyBean(addCommentDTO, Comment.class);
        return commentService.addComment(comment);
    }
}
