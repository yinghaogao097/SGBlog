package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddRoleDto;
import com.sangeng.domain.dto.ChangeStatusDto;
import com.sangeng.domain.dto.GetRoleDto;
import com.sangeng.domain.dto.UpdateRoleDto;
import com.sangeng.domain.entity.Menu;
import com.sangeng.domain.entity.Role;
import com.sangeng.domain.entity.RoleMenu;
import com.sangeng.domain.vo.*;
import com.sangeng.mapper.MenuMapper;
import com.sangeng.mapper.RoleMapper;
import com.sangeng.service.RoleMenuService;
import com.sangeng.service.RoleService;
import com.sangeng.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        Role role = getById(id);
        RoleBackVo roleBackVo = BeanCopyUtils.copyBean(role, RoleBackVo.class);
        return ResponseResult.okResult(roleBackVo);
    }

    @Override
    public ResponseResult roleMenuTreeSelect(Integer id) {
        // 先查菜单树
        // 先找出所有菜单
        List<Menu> menus = menuMapper.selectAllMenu();
        // bean拷贝
        List<MenuTreeVo> menuTreeVos = new ArrayList<>();
        for (Menu menu : menus) {
            menuTreeVos.add(new MenuTreeVo(menu.getId(), menu.getMenuName(), menu.getParentId(), null));
        }
        menuTreeVos = buildTreesSelect(menuTreeVos, 0L);
        // 再查角色所关联的菜单权限id列表
        List<Menu> roleMenu = menuMapper.selectAllMenuById(id.longValue());
        List<String> checkedKeys = new ArrayList<>();
        for (Menu menu : roleMenu) {
            checkedKeys.add(menu.getId().toString());
        }
        return ResponseResult.okResult(new RoleMenuTreeSelectVo(menuTreeVos, checkedKeys));
    }

    @Override
    @Transactional
    public ResponseResult updateRole(UpdateRoleDto updateRoleDto) {
        // bean拷贝
        Role role = BeanCopyUtils.copyBean(updateRoleDto, Role.class);
        // 更新角色
        updateById(role);
        // 删除原有的角色菜单关联记录
        roleMenuService.remove(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, role.getId()));
        // 添加新的角色菜单关联记录
        List<RoleMenu> roleMenus = updateRoleDto.getMenuIds()
                .stream()
                .map(menuId -> new RoleMenu(role.getId(), menuId))
                .collect(Collectors.toList());
        if (!roleMenus.isEmpty()) {
            roleMenuService.saveBatch(roleMenus);
        }
        return ResponseResult.okResult();
    }


}

