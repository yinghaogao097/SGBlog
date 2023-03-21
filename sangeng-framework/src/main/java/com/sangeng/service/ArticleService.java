package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.Article;

/**
 * 文章 业务层
 *
 * @Author：Achen
 */
public interface ArticleService extends IService<Article> {
    /**
     * 查询热门文章列表
     *
     * @return
     */
    ResponseResult hotArticleList();

    /**
     * 分页查询文章列表
     *
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @return
     */
    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    /**
     * 查询文章内容
     *
     * @param id
     * @return
     */
    ResponseResult getArticleDetail(Integer id);
}
