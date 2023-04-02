package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.LinkDto;
import com.sangeng.domain.entity.Link;

import java.util.List;


/**
 * 友链 业务层
 *
 * @author Achen
 */
public interface LinkService extends IService<Link> {
    /**
     * 友链查询
     *
     * @return
     */
    ResponseResult getAllLink();

    /**
     * 分页查询友链列表
     *
     * @param pageNum
     * @param pageSize
     * @param linkDto
     * @return
     */
    ResponseResult linkPage(Integer pageNum, Integer pageSize, LinkDto linkDto);

    /**
     * 新增友链
     *
     * @param link
     * @return
     */
    ResponseResult addLink(Link link);

    /**
     * 获取友链
     *
     * @param id
     * @return
     */
    ResponseResult getLink(Long id);

    /**
     * 更新友链
     *
     * @param link
     * @return
     */
    ResponseResult updateLink(Link link);

    /**
     * 删除友链
     *
     * @param id
     * @return
     */
    ResponseResult deleteLink(List<Integer> id);
}

