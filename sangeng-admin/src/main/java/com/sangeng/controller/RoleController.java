package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddRoleDto;
import com.sangeng.domain.dto.ChangeStatusDto;
import com.sangeng.domain.dto.GetRoleDto;
import com.sangeng.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Achen
 */
@RestController
@RequestMapping("/system")
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
    @GetMapping("/role/list")
    public ResponseResult getRoleList(Integer pageNum, Integer pageSize, GetRoleDto getRoleDto) {
        return roleService.gerRoleList(pageNum, pageSize, getRoleDto);
    }

    /**
     * 改变角色状态
     *
     * @param changeStatusDto
     * @return
     */
    @PutMapping("/role/changeStatus")
    public ResponseResult changeStatus(@RequestBody ChangeStatusDto changeStatusDto) {
        return roleService.changeStatus(changeStatusDto);
    }

    /**
     * 获取菜单树
     *
     * @return
     */
    @GetMapping("/menu/treeselect")
    public ResponseResult gerTreesSelect() {
        return roleService.gerTreesSelect();
    }

    /**
     * 新增角色 能够直接设置角色所关联的菜单权限
     *
     * @return
     */
    @PostMapping("/role")
    public ResponseResult addRole(@RequestBody AddRoleDto addRoleDto) {
        return roleService.addRole(addRoleDto);
    }

    /**
     * 角色信息回显
     *
     * @param id
     * @return
     */
    @GetMapping("/role/{id}")
    public ResponseResult getRole(@PathVariable("id") Integer id) {
        return roleService.getRole(id);
    }
}
