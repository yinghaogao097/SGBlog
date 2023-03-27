package com.sangeng.service;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.User;

/**
 * 后台登陆 业务层
 *
 * @author Achen
 */
public interface SystemLoginService {
    /**
     * 登录
     *
     * @param user 用户
     * @return
     */
    ResponseResult login(User user);

}
