package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.constants.SystemConstants;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.LinkDto;
import com.sangeng.domain.entity.Link;
import com.sangeng.domain.vo.LinkVo;
import com.sangeng.domain.vo.PageVo;
import com.sangeng.mapper.LinkMapper;
import com.sangeng.service.LinkService;
import com.sangeng.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 友链 业务层处理
 *
 * @author Achen
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();

        // 查询所有审核通过的
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(queryWrapper);
        // 转换VO
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult linkPage(Integer pageNum, Integer pageSize, LinkDto linkDto) {
        // 封装查询条件
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .like(StringUtils.hasText(linkDto.getName()), Link::getName, linkDto.getName())
                .eq(StringUtils.hasText(linkDto.getStatus()), Link::getStatus, linkDto.getStatus());
        // 分页
        Page<Link> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        return ResponseResult.okResult(new PageVo(page.getRecords(), page.getTotal()));
    }

    @Override
    public ResponseResult addLink(Link link) {
        save(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getLink(Long id) {
        Link link = getById(id);
        return ResponseResult.okResult(link);
    }

    @Override
    public ResponseResult updateLink(Link link) {
        updateById(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteLink(List<Integer> id) {
        LambdaUpdateWrapper<Link> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .set(Link::getDelFlag, SystemConstants.LINK_STATUS_DELETE)
                .in(Link::getId, id);
        update(updateWrapper);
        return ResponseResult.okResult();
    }
}

