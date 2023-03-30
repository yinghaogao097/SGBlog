package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.constants.SystemConstants;
import com.sangeng.domain.entity.Menu;
import com.sangeng.mapper.MenuMapper;
import com.sangeng.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author Achen
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<String> selectPermsByUserId(Long id) {
        // 如果是管理员 返回所有权限
        if (id == 1L) {
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
        return getBaseMapper().selectPermsByUserId(id);
    }
}

