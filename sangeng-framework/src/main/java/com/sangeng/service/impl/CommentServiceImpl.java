package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.constants.SystemConstants;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.Comment;
import com.sangeng.domain.entity.User;
import com.sangeng.domain.vo.CommentVo;
import com.sangeng.domain.vo.PageVo;
import com.sangeng.enums.AppHttpCodeEnum;
import com.sangeng.exception.SystemException;
import com.sangeng.mapper.CommentMapper;
import com.sangeng.service.CommentService;
import com.sangeng.service.UserService;
import com.sangeng.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 评论 业务层处理
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
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        // 查询对应文章的根评论
        LambdaQueryWrapper<Comment> queryWrapper = Wrappers.lambdaQuery(Comment.class)
                // 对commentType进行判断 commentType为0是文章的评论 必须根据文章查询 如果为1就是友链查询 不用查询文章id
                .eq(SystemConstants.ARTICLE_COMMENT.equals(commentType), Comment::getArticleId, articleId)
                // 根评论 rootId为-1
                .eq(Comment::getRootId, SystemConstants.COMMENT_ROOT_ID)
                .eq(Comment::getType, commentType);

        // 分页
        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());
        // 根据根评论的id查询所对应的子评论的集合
        commentVoList.forEach(commentVO -> commentVO.setChildren(getChildren(commentVO.getId())));
        return ResponseResult.okResult(new PageVo(commentVoList, page.getTotal()));
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        if (!StringUtils.hasText(comment.getContent())) throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        save(comment);
        return ResponseResult.okResult();
    }

    /**
     * 根据根评论的id查询所对应的子评论的集合
     *
     * @param id 根评论的id
     * @return
     */
    private List<CommentVo> getChildren(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = Wrappers.lambdaQuery(Comment.class)
                // 根据根评论id查询对应子评论
                .eq(Comment::getRootId, id)
                // 根据时间升序排序
                .orderByAsc(Comment::getCreateTime);
        List<Comment> comments = list(queryWrapper);
        return toCommentVoList(comments);
        // Unchecked generics array creation for varargs parameter
    }

    /**
     * @param list 评论集合
     * @return 评论vo集合
     */
    private List<CommentVo> toCommentVoList(List<Comment> list) {
        return list.stream()
                .map(comment -> BeanCopyUtils.copyBean(comment, CommentVo.class))
                .peek(commentVo -> {
                    // 通过creatyBy查询用户的昵称并赋值
                    User user = userService.getById(commentVo.getCreateBy());
                    if (!Objects.isNull(user)) {
                        String nickName = user.getNickName();
                        commentVo.setUsername(nickName);
                    }
                    // 通过toCommentUserId查询用户的昵称并赋值
                    // 如果toCommentUserId不为-1才进行查询
                    if (commentVo.getToCommentUserId() != -1) {
                        User user2 = userService.getById(commentVo.getToCommentUserId());
                        if (!Objects.isNull(user2)) {
                            String toCommentUserName = user2.getNickName();
                            commentVo.setToCommentUserName(toCommentUserName);
                        }
                    }
                })
                .collect(Collectors.toList());
    }
}
