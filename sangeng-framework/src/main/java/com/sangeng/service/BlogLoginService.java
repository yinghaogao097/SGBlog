package com.sangeng.service;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.User;

/**
 * 登陆接口
 *
 * @Author：Achen
 */
public interface BlogLoginService {
    ResponseResult login(User user);
}
