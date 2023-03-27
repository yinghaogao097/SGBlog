package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Achen
 */
@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 查询标题
     *
     * @return
     */
    @GetMapping("/list")
    public ResponseResult list() {
        return ResponseResult.okResult(tagService.list());
    }

}
