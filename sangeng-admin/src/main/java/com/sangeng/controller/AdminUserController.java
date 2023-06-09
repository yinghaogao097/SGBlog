package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddUserDto;
import com.sangeng.domain.dto.AdminUserDto;
import com.sangeng.domain.dto.UpdateUserDto;
import com.sangeng.domain.dto.UserStatusDto;
import com.sangeng.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Achen
 */
@RestController
@RequestMapping("/system")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    /**
     * 用户列表
     *
     * @param pageNum
     * @param pageSize
     * @param adminUserDto
     * @return
     */
    @GetMapping("/user/list")
    public ResponseResult getUserList(Integer pageNum, Integer pageSize, AdminUserDto adminUserDto) {
        return adminUserService.getUserList(pageNum, pageSize, adminUserDto);
    }

    /**
     * 查询角色列表
     *
     * @return
     */
    @GetMapping("/role/listAllRole")
    public ResponseResult listAllRole() {
        return adminUserService.listAllRole();
    }

    /**
     * 新增用户
     *
     * @param addUserDto
     * @return
     */
    @PostMapping("/user")
    public ResponseResult addUser(@RequestBody @Validated AddUserDto addUserDto) {
        return adminUserService.addUser(addUserDto);
    }

    /**
     * 改变用户状态
     *
     * @param userStatusDto
     * @return
     */
    @PutMapping("/user/changeStatus")
    public ResponseResult changeStatus(@RequestBody UserStatusDto userStatusDto) {
        return adminUserService.changeStatus(userStatusDto);
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @DeleteMapping("/user/{id}")
    public ResponseResult deleteUser(@PathVariable("id") Long id) {
        return adminUserService.deleteUser(id);
    }

    /**
     * 查询用户
     *
     * @param id
     * @return
     */
    @GetMapping("/user/{id}")
    public ResponseResult getUser(@PathVariable("id") Long id) {
        return adminUserService.getUser(id);
    }

    /**
     * 修改用户
     *
     * @param updateUserDto
     * @return
     */
    @PutMapping("/user")
    public ResponseResult updateUser(@RequestBody UpdateUserDto updateUserDto) {
        return adminUserService.updateUser(updateUserDto);
    }
}
