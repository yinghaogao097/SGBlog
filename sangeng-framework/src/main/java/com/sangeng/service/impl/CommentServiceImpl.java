package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.constants.SystemConstants;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.Comment;
import com.sangeng.domain.vo.CommentVo;
import com.sangeng.domain.vo.PageVo;
import com.sangeng.mapper.CommentMapper;
import com.sangeng.service.CommentService;
import com.sangeng.service.UserService;
import com.sangeng.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author Achen
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserService userService;

    @Override
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
        // 查询对应文章的根评论
        LambdaQueryWrapper<Comment> queryWrapper = Wrappers.lambdaQuery(Comment.class)
                // 对articleId进行判断
                .eq(Comment::getArticleId, articleId)
                // 根评论 rootId为-1
                .eq(Comment::getRootId, SystemConstants.COMMENT_ROOT_ID);

        // 分页
        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());
        return ResponseResult.okResult(new PageVo(commentVoList, page.getTotal()));
    }

    private List<CommentVo> toCommentVoList(List<Comment> list) {
//        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
//        //遍历vo集合
//        for (CommentVo commentVo : commentVos) {
//
//            String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
//            commentVo.setUsername(nickName);
//
//            if (commentVo.getToCommentUserId() != -1) {
//                String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
//                commentVo.setToCommentUserName(toCommentUserName);
//            }
//        }
//        return commentVos;
        return list.stream()
                .map(comment -> BeanCopyUtils.copyBean(comment, CommentVo.class))
                .peek(commentVo -> {
                    // 通过creatyBy查询用户的昵称并赋值
                    String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
                    commentVo.setUsername(nickName);
                    // 通过toCommentUserId查询用户的昵称并赋值
                    // 如果toCommentUserId不为-1才进行查询
                    if (commentVo.getToCommentUserId() != -1) {
                        String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                        commentVo.setToCommentUserName(toCommentUserName);
                    }
                })
                .collect(Collectors.toList());
    }
}
