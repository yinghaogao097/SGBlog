package com.sangeng.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author：Achen
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetailVo {
    private Long id;
    // 标题
    private String title;
    // 所属分类Id
    private String categoryId;
    // 所属分类名
    private String categoryName;
    // 是否允许评论
    private String isComment;
    // 访问量
    private Long viewCount;
    // 文章内容
    private String content;
    private Date createTime;

}
