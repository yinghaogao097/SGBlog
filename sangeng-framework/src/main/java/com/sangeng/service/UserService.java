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
    /**
     * 查询用户信息
     *
     * @return
     */
    ResponseResult userInfo();

    /**
     * 更新用户信息
     *
     * @param user 用户
     * @return
     */
    ResponseResult updateUserInfo(User user);

    /**
     * 注册用户
     *
     * @param user 用户
     * @return
     */
    ResponseResult register(User user);
}

