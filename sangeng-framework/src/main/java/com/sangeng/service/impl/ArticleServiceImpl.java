package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.constants.SystemConstants;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddArticleDto;
import com.sangeng.domain.dto.AdminArticleDetailDto;
import com.sangeng.domain.dto.ListArticleDto;
import com.sangeng.domain.entity.Article;
import com.sangeng.domain.entity.ArticleTag;
import com.sangeng.domain.entity.Category;
import com.sangeng.domain.vo.*;
import com.sangeng.mapper.ArticleMapper;
import com.sangeng.service.ArticleService;
import com.sangeng.service.ArticleTagService;
import com.sangeng.service.CategoryService;
import com.sangeng.utils.BeanCopyUtils;
import com.sangeng.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 文章 业务层处理
 *
 * @author Achen
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleTagService articleTagService;

    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper
                // 必须是正式文章
                .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL)
                // 按照浏览量降序排序
                .orderByDesc(Article::getViewCount);
        List<Article> articleList = articleMapper.selectList(queryWrapper);
//        // 创建分页
//        Page<Article> page = new Page<>(1, 10);
//        // 获取分页中的数据
//        List<Article> articles = page(page, queryWrapper).getRecords();
        // vo拷贝
        List<HotArticleVo> articleVos = BeanCopyUtils.copyBeanList(articleList, HotArticleVo.class);
        return ResponseResult.okResult(articleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        // 查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 如果categoryId存在就查询
        queryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
        // 状态是正式发布的
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // 对是否置顶进行降序排序
        queryWrapper.orderByDesc(Article::getIsTop);
        // 分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        // 获取文章集合 从集合中根据id查找名字并赋值到名字
        List<Article> articles = page.getRecords()
                .stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());
        // 从redis中获取浏览量放入到集合中
//        for (Article article : articles) {
////            Integer viewCount = redisCache.getCacheMapValue("article:viewCount", article.getId().toString());
//            redisCache.getCacheMapValue("article:viewCount", article.getId().toString())
//            article.setViewCount(viewCount.longValue());
//        }
        articles = articles
                .stream()
                .map(article -> article.setViewCount(redisCache.getCacheMapValue("article:viewCount", article.getId().toString())))
                .collect(Collectors.toList());
        // 封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);
        return ResponseResult.okResult(new PageVo(articleListVos, page.getTotal()));
    }

    @Override
    public ResponseResult getArticleDetail(Integer id) {
        // 根据id查询文章
        Article article = getById(id);
        // 从redis中查询浏览量
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        article.setViewCount(viewCount);
        // 转换VO
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        // 根据分类id查询分类名
        Category category = categoryService.getById(articleDetailVo.getCategoryId());
        if (category != null) {
            articleDetailVo.setCategoryName(category.getName());
        }
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        // 更新redis中对应 id的浏览量
        redisCache.incrementCacheMapValue("article:viewCount", id.toString(), 1);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult add(AddArticleDto addArticleDto) {
        // 添加博客
        Article article = BeanCopyUtils.copyBean(addArticleDto, Article.class);
        save(article);

        // 将标签存储到文章标签关联表
        List<ArticleTag> articleTags = addArticleDto.getTags()
                .stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listArticle(Integer pageNum, Integer pageSize, ListArticleDto listArticleDto) {
        // 查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .like(StringUtils.hasText(listArticleDto.getTitle()), Article::getTitle, listArticleDto.getTitle())
                .or()
                .like(StringUtils.hasText(listArticleDto.getSummary()), Article::getSummary, listArticleDto.getSummary());
        // 分页
        Page<Article> page = new Page<>(pageNum, pageSize);
        Page<Article> articlePage = page(page, queryWrapper);
        // 获取数据
        List<Article> articleList = articlePage.getRecords();
        List<ArticleAllVo> articleAllVos = BeanCopyUtils.copyBeanList(articleList, ArticleAllVo.class);
        long total = page.getTotal();
        return ResponseResult.okResult(new PageVo(articleAllVos, total));
    }

    @Override
    public ResponseResult getAdminArticleDetail(Integer id) {
        Article article = articleMapper.selectById(id);
        AdminArticleDetailVo adminArticleDetailVo = BeanCopyUtils.copyBean(article, AdminArticleDetailVo.class);
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        // 根据条件查询当前文章的tag
        queryWrapper.eq(ArticleTag::getArticleId, article.getId());
        List<ArticleTag> list = articleTagService.list(queryWrapper);
        List<Long> tagList = list.stream().map(ArticleTag::getTagId).collect(Collectors.toList());
        adminArticleDetailVo.setTags(tagList);
        return ResponseResult.okResult(adminArticleDetailVo);
    }

    @Override
    @Transactional
    public ResponseResult updateAdminArticleDetail(AdminArticleDetailDto adminArticleDetailDto) {
        // 转换为文章后存储
        Article article = BeanCopyUtils.copyBean(adminArticleDetailDto, Article.class);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getId, article.getId());
        updateById(article);
        // 删除原有的文章标签关联记录
        articleTagService.remove(new LambdaQueryWrapper<ArticleTag>().eq(ArticleTag::getArticleId, article.getId()));
        // 添加新的文章标签关联记录
        List<ArticleTag> articleTags = adminArticleDetailDto.getTags()
                .stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());
        if (!articleTags.isEmpty()) {
            articleTagService.saveBatch(articleTags);
        }
        return ResponseResult.okResult();
    }


    @Override
    public ResponseResult deleteArticle(List<Integer> id) {
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<Article>()
                .set(Article::getDelFlag, SystemConstants.ARTICLE_DELETE_FLAG)
                .in(Article::getId, id);
        update(updateWrapper);
        return ResponseResult.okResult();
    }

}
