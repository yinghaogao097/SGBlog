package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.LinkDto;
import com.sangeng.domain.entity.Link;
import com.sangeng.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Achen
 */
@RestController
@RequestMapping("/content/link")
public class AdminLinkController {

    @Autowired
    private LinkService linkService;

    /**
     * 分页查询友链列表
     *
     * @param pageNum
     * @param pageSize
     * @param linkDto
     * @return
     */
    @GetMapping("/list")
    public ResponseResult linkPage(Integer pageNum, Integer pageSize, LinkDto linkDto) {
        return linkService.linkPage(pageNum, pageSize, linkDto);
    }

    /**
     * 新增友链
     *
     * @param link
     * @return
     */
    @PostMapping
    public ResponseResult addLink(@RequestBody Link link) {
        return linkService.addLink(link);
    }

    /**
     * 获取友链
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult getLink(@PathVariable("id") Long id) {
        return linkService.getLink(id);
    }

    /**
     * 修改友链
     *
     * @param link
     * @return
     */
    @PutMapping
    public ResponseResult updateLink(@RequestBody Link link) {
        return linkService.updateLink(link);
    }

    /**
     * 删除友链
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseResult deleteLink(@PathVariable("id") List<Integer> id) {
        return linkService.deleteLink(id);
    }
}
