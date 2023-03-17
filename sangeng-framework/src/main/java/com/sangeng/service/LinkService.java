package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-03-17 17:26:16
 */
public interface LinkService extends IService<Link> {
    /**
     * 友链查询
     *
     * @return
     */
    ResponseResult getAllLink();
}

