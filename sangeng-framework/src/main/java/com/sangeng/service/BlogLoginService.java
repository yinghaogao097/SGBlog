package com.sangeng.service;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.User;

/**
 * 登陆 业务层
 *
 * @author Achen
 */
public interface BlogLoginService {
    ResponseResult login(User user);
}
