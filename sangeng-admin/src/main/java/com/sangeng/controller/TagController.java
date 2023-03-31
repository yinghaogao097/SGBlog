package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.TagListDto;
import com.sangeng.domain.dto.UpdateTagDTO;
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
     * 查询所有标签
     *
     * @return
     */
    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        return tagService.pageTagList(pageNum, pageSize, tagListDto);
    }

    /**
     * 新增标签
     *
     * @param tagListDto
     * @return
     */
    @PostMapping
    public ResponseResult insertTag(@RequestBody TagListDto tagListDto) {
        Tag tag = BeanCopyUtils.copyBean(tagListDto, Tag.class);
        return tagService.insertTag(tag);
    }

    /**
     * 删除标签
     *
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public ResponseResult deleteTags(@PathVariable("id") List<Integer> id) {

        return tagService.deleteTags(id);
    }

    /**
     * 获取标签
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseResult getTag(@PathVariable("id") Integer id) {
        return tagService.getTagById(id);
    }

    /**
     * 修改标签
     *
     * @param updateTagDTO
     * @return
     */
    @PutMapping
    public ResponseResult updateTag(@RequestBody UpdateTagDTO updateTagDTO) {
        Tag tag = BeanCopyUtils.copyBean(updateTagDTO, Tag.class);
        return tagService.updateTagById(tag);
    }

    /**
     * 查询所有标签
     *
     * @return
     */
    @GetMapping("listAllTag")
    public ResponseResult listAllTag() {
        return tagService.listAllTag();
    }

}
