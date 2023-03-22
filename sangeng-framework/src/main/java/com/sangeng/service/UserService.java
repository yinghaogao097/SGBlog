package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.User;


/**
 * 用户 业务层
 *
 * @author Achen
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();
}

