package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.ChangeStatusDto;
import com.sangeng.domain.dto.GetRoleDto;
import com.sangeng.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Achen
 */
@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 获取角色列表
     *
     * @param pageNum
     * @param pageSize
     * @param getRoleDto
     * @return
     */
    @GetMapping("/list")
    public ResponseResult getRoleList(Integer pageNum, Integer pageSize, GetRoleDto getRoleDto) {
        return roleService.gerRoleList(pageNum, pageSize, getRoleDto);
    }

    /**
     * 改变角色状态
     *
     * @param changeStatusDto
     * @return
     */
    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody ChangeStatusDto changeStatusDto) {
        return roleService.changeStatus(changeStatusDto);
    }
}
