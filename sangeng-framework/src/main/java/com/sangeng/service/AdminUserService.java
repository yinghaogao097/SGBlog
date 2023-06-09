package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddUserDto;
import com.sangeng.domain.dto.AdminUserDto;
import com.sangeng.domain.dto.UpdateUserDto;
import com.sangeng.domain.dto.UserStatusDto;
import com.sangeng.domain.entity.User;

/**
 * @author Achen
 */
public interface AdminUserService extends IService<User> {
    /**
     * 用户列表
     *
     * @param pageNum
     * @param pageSize
     * @param adminUserDto
     * @return
     */
    ResponseResult getUserList(Integer pageNum, Integer pageSize, AdminUserDto adminUserDto);

    /**
     * 查询角色列表
     *
     * @return
     */
    ResponseResult listAllRole();

    /**
     * 新增用户
     *
     * @param addUserDto
     * @return
     */
    ResponseResult addUser(AddUserDto addUserDto);

    /**
     * 改变用户状态
     *
     * @param userStatusDto
     * @return
     */
    ResponseResult changeStatus(UserStatusDto userStatusDto);

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    ResponseResult deleteUser(Long id);

    /**
     * 查询用户信息
     *
     * @param id
     * @return
     */
    ResponseResult getUser(Long id);

    /**
     * 修改用户
     *
     * @param updateUserDto
     * @return
     */
    ResponseResult updateUser(UpdateUserDto updateUserDto);
}
