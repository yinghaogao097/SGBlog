package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.TagListDto;
import com.sangeng.domain.entity.Tag;
import com.sangeng.domain.vo.PageVo;
import com.sangeng.domain.vo.TagVo;
import com.sangeng.mapper.TagMapper;
import com.sangeng.service.TagService;
import com.sangeng.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author Achen
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        LambdaQueryWrapper<Tag> queryWrapper =
                Wrappers.lambdaQuery(Tag.class)
                        .like(StringUtils.hasText(tagListDto.getName()), Tag::getName, tagListDto.getName())
                        .eq(StringUtils.hasText(tagListDto.getRemark()), Tag::getRemark, tagListDto.getName());
        // 分页
        Page<Tag> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);
        // 封装数据返回
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult insertTag(Tag tag) {
        tagMapper.insert(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteTags(List<Integer> id) {
        LambdaUpdateWrapper<Tag> updateWrapper = new LambdaUpdateWrapper<Tag>()
                .set(Tag::getDelFlag, 1)
                .in(Tag::getId, id);
        update(updateWrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getTagById(Integer id) {
        Tag tag = tagMapper.selectById(id);
        return ResponseResult.okResult(tag);
    }

    @Override
    public ResponseResult updateTagById(Tag tag) {
        LambdaUpdateWrapper<Tag> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .set(Tag::getName, tag.getName())
                .set(Tag::getRemark, tag.getRemark())
                .eq(Tag::getId, tag.getId());
        update(new Tag(), updateWrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllTag() {
        List<Tag> tags = tagMapper.selectList(null);
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(tags, TagVo.class);
        return ResponseResult.okResult(tagVos);
    }
}

