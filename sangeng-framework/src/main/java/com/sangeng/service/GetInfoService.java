package com.sangeng.service;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.LoginUser;

/**
 * @author Achen
 */
public interface GetInfoService {
    ResponseResult getInfo(LoginUser loginUser);
}
