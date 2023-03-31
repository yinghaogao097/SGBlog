package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddArticleDto;
import com.sangeng.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Achen
 */
@RestController
@RequestMapping("/content")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 写博文
     *
     * @param addArticleDto
     * @return
     */
    @PostMapping("/article")
    public ResponseResult add(@RequestBody AddArticleDto addArticleDto) {
        return articleService.add(addArticleDto);
    }
}
