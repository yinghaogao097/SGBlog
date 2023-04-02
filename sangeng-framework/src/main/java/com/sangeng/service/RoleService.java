package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddRoleDto;
import com.sangeng.domain.dto.ChangeStatusDto;
import com.sangeng.domain.dto.GetRoleDto;
import com.sangeng.domain.dto.UpdateRoleDto;
import com.sangeng.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author Achen
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    /**
     * 获取角色列表
     *
     * @param pageNum
     * @param pageSize
     * @param getRoleDto
     * @return
     */
    ResponseResult gerRoleList(Integer pageNum, Integer pageSize, GetRoleDto getRoleDto);

    /**
     * 改变角色状态
     *
     * @param changeStatusDto
     * @return
     */
    ResponseResult changeStatus(ChangeStatusDto changeStatusDto);

    /**
     * 获取菜单树
     *
     * @return
     */
    ResponseResult gerTreesSelect();

    /**
     * 新增角色
     *
     * @param addRoleDto
     * @return
     */
    ResponseResult addRole(AddRoleDto addRoleDto);

    /**
     * 角色信息回显
     *
     * @param id
     * @return
     */
    ResponseResult getRole(Integer id);

    /**
     * 加载对应角色菜单列表树
     *
     * @param id
     * @return
     */
    ResponseResult roleMenuTreeSelect(Integer id);

    /**
     * 修改角色
     *
     * @param updateRoleDto
     * @return
     */
    ResponseResult updateRole(UpdateRoleDto updateRoleDto);
}

