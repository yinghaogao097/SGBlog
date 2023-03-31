package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.constants.SystemConstants;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.GetMenuListDto;
import com.sangeng.domain.entity.Menu;
import com.sangeng.domain.vo.MenuByIdVo;
import com.sangeng.domain.vo.MenuVo;
import com.sangeng.mapper.MenuMapper;
import com.sangeng.service.MenuService;
import com.sangeng.utils.BeanCopyUtils;
import com.sangeng.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author Achen
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<String> selectPermsByUserId(Long id) {
        // 如果是管理员 返回所有权限
        if (SecurityUtils.isAdmin()) {
            LambdaQueryWrapper<Menu> queryWrapper = Wrappers
                    .lambdaQuery(Menu.class)
                    // 菜单类型为C和M
                    .in(Menu::getMenuType, SystemConstants.MENU, SystemConstants.BUTTON)
                    // 菜单状态为正常
                    .eq(Menu::getStatus, SystemConstants.LINK_STATUS_NORMAL);
            // stream转换为List<String> perms 返回
            List<Menu> menus = list(queryWrapper);
            return menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
        }
        // 否则根据id查权限
        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        List<Menu> menus;
        // 判断是否是管理员
        if (SecurityUtils.isAdmin()) {
            menus = menuMapper.selectAllRouterMenu();
        } else {
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }

        // 构建tree
        // 先找出第一层的菜单 然后去找它们的子菜单设置到children属性中
        return builderMenuTree(menus, 0L);
    }

    @Override
    public ResponseResult getMenuList(GetMenuListDto getMenuListDto) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(StringUtils.hasText(getMenuListDto.getStatus()), Menu::getStatus, getMenuListDto.getStatus())
                .eq(StringUtils.hasText(getMenuListDto.getMenuName()), Menu::getMenuName, getMenuListDto.getMenuName())
                .orderByAsc(Menu::getParentId)
                .orderByAsc(Menu::getOrderNum);
        List<Menu> menus = menuMapper.selectList(queryWrapper);
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menus, MenuVo.class);
        return ResponseResult.okResult(menuVos);
    }

    @Override
    public ResponseResult addMenu(Menu menu) {
        save(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getMenuById(Integer id) {
        Menu menu = getById(id);
        MenuByIdVo menuByIdVo = BeanCopyUtils.copyBean(menu, MenuByIdVo.class);
        return ResponseResult.okResult(menuByIdVo);
    }

    @Override
    public ResponseResult updateMenu(Menu menu) {
        Menu oldMenu = getById(menu.getParentId());
        // 比较
        if (menu.getMenuName().equals(oldMenu.getMenuName())) {
            return ResponseResult.errorResult(500, "修改菜单[" + menu.getMenuName() + "]失败，上级菜单不能选择自己");
        } else {
            updateById(menu);
            return ResponseResult.okResult();
        }
    }

    @Override
    public ResponseResult deleteMenu(Integer menuId) {
        // 查询是否存在子菜单
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getParentId, menuId);
        int childCount = menuMapper.selectCount(queryWrapper);
        if (childCount > 0) {
            return ResponseResult.errorResult(500, "存在子菜单不允许删除");
        }
        LambdaUpdateWrapper<Menu> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .set(Menu::getDelFlag, SystemConstants.MENU_DELETE_FLAG)
                .eq(Menu::getId, menuId);
        update(updateWrapper);
        return ResponseResult.okResult();
    }

    private List<Menu> builderMenuTree(List<Menu> menus, long parentId) {
        return menus.stream()
                // 只保留父id为0的集合
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
    }

    /**
     * 获取存入参数的  子menu集合
     *
     * @param menu
     * @param menus
     * @return
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        return menus.stream()
                // 只保留当前menu的父id等于上一级父id
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m -> m.setChildren(getChildren(m, menus)))
                .collect(Collectors.toList());
    }
}

