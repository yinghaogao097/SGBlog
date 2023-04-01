package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.GetRoleDto;
import com.sangeng.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseResult gerRoleList(Integer pageNum, Integer pageSize, GetRoleDto getRoleDto) {
        return roleService.gerRoleList(pageNum, pageSize, getRoleDto);
    }
}
