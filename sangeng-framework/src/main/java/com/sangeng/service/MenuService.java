package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.GetMenuListDto;
import com.sangeng.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author Achen
 */
public interface MenuService extends IService<Menu> {
    /**
     * 根据用户id查询权限
     *
     * @param id
     * @return
     */
    List<String> selectPermsByUserId(Long id);

    /**
     * 根据用户id查询菜单数据
     *
     * @param userId
     * @return
     */
    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    /**
     * 获取菜单列表
     *
     * @param getMenuListDto
     * @return
     */
    ResponseResult getMenuList(GetMenuListDto getMenuListDto);

    /**
     * 新增菜单
     *
     * @param menu
     * @return
     */
    ResponseResult addMenu(Menu menu);
}

