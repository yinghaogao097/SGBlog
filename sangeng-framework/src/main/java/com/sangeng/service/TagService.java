package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.TagListDto;
import com.sangeng.domain.entity.Tag;

import java.util.List;


/**
 * 标签(Tag)表服务接口
 *
 * @author Achen
 */
public interface TagService extends IService<Tag> {
    /**
     * 查询标签
     *
     * @param pageNum
     * @param pageSize
     * @param tagListDto
     * @return
     */
    ResponseResult<Tag> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    /**
     * 新增标签
     *
     * @param tagListDto
     * @return
     */
    ResponseResult<Tag> insertTag(Tag tag);

    /**
     * 删除标签
     *
     * @param id
     * @return
     */
    ResponseResult<Tag> deleteTags(List<Integer> id);
}

