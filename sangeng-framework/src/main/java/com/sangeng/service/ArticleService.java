package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddArticleDto;
import com.sangeng.domain.dto.AdminArticleDetailDto;
import com.sangeng.domain.dto.ListArticleDto;
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

    /**
     * 更新浏览量时去更新redis中的数据
     *
     * @param id 文章id
     * @return
     */
    ResponseResult updateViewCount(Long id);

    /**
     * 写博文
     *
     * @param article
     * @return
     */
    ResponseResult add(AddArticleDto addArticleDto);

    /**
     * 文章列表查找
     *
     * @param pageNum
     * @param pageSize
     * @param listArticleDto
     * @return
     */
    ResponseResult listArticle(Integer pageNum, Integer pageSize, ListArticleDto listArticleDto);

    /**
     * 根据id查询文章详情
     *
     * @param id
     * @return
     */
    ResponseResult getAdminArticleDetail(Integer id);

    /**
     * 修改文章
     *
     * @param adminArticleDetailDto
     * @return
     */
    ResponseResult updateAdminArticleDetail(AdminArticleDetailDto adminArticleDetailDto);
}
