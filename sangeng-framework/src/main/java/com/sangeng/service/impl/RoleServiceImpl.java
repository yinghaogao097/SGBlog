package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddRoleDto;
import com.sangeng.domain.dto.ChangeStatusDto;
import com.sangeng.domain.dto.GetRoleDto;
import com.sangeng.domain.entity.Menu;
import com.sangeng.domain.entity.Role;
import com.sangeng.domain.entity.RoleMenu;
import com.sangeng.domain.vo.MenuTreeVo;
import com.sangeng.domain.vo.PageVo;
import com.sangeng.domain.vo.RoleVo;
import com.sangeng.mapper.MenuMapper;
import com.sangeng.mapper.RoleMapper;
import com.sangeng.service.RoleMenuService;
import com.sangeng.service.RoleService;
import com.sangeng.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author Achens
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuService roleMenuService;

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
    public ResponseResult gerTreesSelect() {
        // 先找出所有菜单
        List<Menu> menus = menuMapper.selectAllMenu();
        // bean拷贝
        List<MenuTreeVo> menuTreeVos = new ArrayList<>();
        for (Menu menu : menus) {
            menuTreeVos.add(new MenuTreeVo(menu.getId(), menu.getMenuName(), menu.getParentId(), null));
        }
        menuTreeVos = buildTreesSelect(menuTreeVos, 0L);
        return ResponseResult.okResult(menuTreeVos);
    }

    private List<MenuTreeVo> buildTreesSelect(List<MenuTreeVo> menuTreeVos, long parentId) {
        return menuTreeVos.stream()
                // 只保留父id为0的集合
                .filter(menuTreeVo -> menuTreeVo.getParentId().equals(parentId))
                .map(menuTreeVo -> menuTreeVo.setChildren(getChildren(menuTreeVo, menuTreeVos)))
                .collect(Collectors.toList());
    }

    private List<MenuTreeVo> getChildren(MenuTreeVo menuTreeVo, List<MenuTreeVo> menuTreeVos) {
        return menuTreeVos.stream()
                // 只保留当前menu的父id等于上一级父id
                .filter(m -> m.getParentId().equals(menuTreeVo.getId()))
                .map(m -> m.setChildren(getChildren(m, menuTreeVos)))
                .collect(Collectors.toList());

    }

    @Override
    public ResponseResult addRole(AddRoleDto addRoleDto) {
        // bean拷贝
        Role role = BeanCopyUtils.copyBean(addRoleDto, Role.class);
        // 添加角色
        save(role);
        // 将菜单存储到菜单角色id表
        List<RoleMenu> roleMenus = addRoleDto.getMenuIds()
                .stream()
                .map(menuIds -> new RoleMenu(role.getId(), menuIds))
                .collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenus);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getRole(Integer id) {
        return null;
    }


}

