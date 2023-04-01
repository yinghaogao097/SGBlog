package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.ChangeStatusDto;
import com.sangeng.domain.dto.GetRoleDto;
import com.sangeng.domain.entity.Role;
import com.sangeng.domain.vo.PageVo;
import com.sangeng.domain.vo.RoleVo;
import com.sangeng.mapper.RoleMapper;
import com.sangeng.service.RoleService;
import com.sangeng.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author Achen
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        // 判断是否是管理员 如果是返回集合中只需要有admin
        if (id == 1L) {
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        // 否则查询用户所具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    public ResponseResult gerRoleList(Integer pageNum, Integer pageSize, GetRoleDto getRoleDto) {
        // 构建查询条件
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .like(StringUtils.hasText(getRoleDto.getRoleName()), Role::getRoleName, getRoleDto.getRoleName())
                .eq(StringUtils.hasText(getRoleDto.getStatus()), Role::getStatus, getRoleDto.getStatus())
                .orderByAsc(Role::getRoleSort);
        // 创建分页
        Page<Role> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        // bean拷贝
        List<Role> records = page.getRecords();
        List<RoleVo> roleVos = BeanCopyUtils.copyBeanList(records, RoleVo.class);
        return ResponseResult.okResult(new PageVo(roleVos, page.getTotal()));
    }

    @Override
    public ResponseResult changeStatus(ChangeStatusDto changeStatusDto) {
        LambdaUpdateWrapper<Role> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .set(Role::getStatus, changeStatusDto.getStatus())
                .eq(Role::getId, changeStatusDto.getRoleId());
        update(updateWrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult addRole() {

        return null;
    }
}

