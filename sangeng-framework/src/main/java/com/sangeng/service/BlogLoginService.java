package com.sangeng.service;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.User;

/**
 * 登陆 业务层
 *
 * @author Achen
 */
public interface BlogLoginService {
    /**
     * 登录
     *
     * @param user 用户
     * @return
     */
    ResponseResult login(User user);

    /**
     * 登出
     *
     * @return
     */
    ResponseResult logout();
}
