package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddArticleDto;
import com.sangeng.domain.dto.AdminArticleDetailDto;
import com.sangeng.domain.dto.ListArticleDto;
import com.sangeng.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Achen
 */
@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 写博文
     *
     * @param addArticleDto
     * @return
     */
    @PostMapping()
    public ResponseResult add(@RequestBody AddArticleDto addArticleDto) {
        return articleService.add(addArticleDto);
    }

    /**
     * 文章列表查找
     *
     * @param pageNum
     * @param pageSize
     * @param listArticleDto
     * @return
     */
    @GetMapping("list")
    public ResponseResult listArticle(Integer pageNum, Integer pageSize, ListArticleDto listArticleDto) {
        return articleService.listArticle(pageNum, pageSize, listArticleDto);
    }

    /**
     * 根据id查询文章详情
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseResult getAdminArticleDetail(@PathVariable("id") Integer id) {
        return articleService.getAdminArticleDetail(id);
    }

    /**
     * 根据id查询文章详情
     *
     * @param id
     * @return
     */
    @PutMapping()
    public ResponseResult updateAdminArticleDetail(@RequestBody AdminArticleDetailDto adminArticleDetailDto) {
        return articleService.updateAdminArticleDetail(adminArticleDetailDto);
    }

    @DeleteMapping("{id}")
    public ResponseResult getAdminArticleDetail(@PathVariable("id") Integer id) {
        return articleService.getAdminArticleDetail(id);
    }
}
