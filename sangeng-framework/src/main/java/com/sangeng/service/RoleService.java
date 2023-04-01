package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.ChangeStatusDto;
import com.sangeng.domain.dto.GetRoleDto;
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
     * 新增角色
     *
     * @return
     */
    ResponseResult addRole();
}

