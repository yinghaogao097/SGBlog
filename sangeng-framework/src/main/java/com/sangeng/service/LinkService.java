package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.Link;


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
}

