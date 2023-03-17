package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.Article;

/**
 * @Author：Achen
 */
public interface ArticleService extends IService<Article> {
    /**
     * 查询热门文章列表
     *
     * @return
     */
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);
}
