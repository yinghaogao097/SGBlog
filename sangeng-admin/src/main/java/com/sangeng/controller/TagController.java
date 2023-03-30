package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.TagListDto;
import com.sangeng.domain.entity.Tag;
import com.sangeng.service.TagService;
import com.sangeng.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Achen
 */
@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 查询标签
     *
     * @return
     */
    @GetMapping("/list")
    public ResponseResult<Tag> list(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        return tagService.pageTagList(pageNum, pageSize, tagListDto);
    }

    /**
     * 新增标签
     *
     * @param tagListDto
     * @return
     */
    @PostMapping
    public ResponseResult<Tag> insertTag(@RequestBody TagListDto tagListDto) {
        Tag tag = BeanCopyUtils.copyBean(tagListDto, Tag.class);
        return tagService.insertTag(tag);
    }

    /**
     * 删除标签
     *
     * @param tagListDto
     * @return
     */
    @DeleteMapping("{id}")
    public ResponseResult<Tag> deleteTags(@PathVariable("id") List<Integer> id) {

        return tagService.deleteTags(id);
    }

}
