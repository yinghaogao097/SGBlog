package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.GetMenuListDto;
import com.sangeng.domain.entity.Menu;
import com.sangeng.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Achen
 */
@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 获取菜单列表
     *
     * @param getMenuListDto
     * @return
     */
    @GetMapping("/list")
    public ResponseResult getMenuList(GetMenuListDto getMenuListDto) {
        return menuService.getMenuList(getMenuListDto);
    }

    /**
     * 新增菜单
     *
     * @param menu
     * @return
     */
    @PostMapping()
    public ResponseResult addMenu(@RequestBody Menu menu) {
        return menuService.addMenu(menu);
    }

    /**
     * 根据id获取菜单
     *
     * @param menu
     * @return
     */
    @GetMapping("{id}")
    public ResponseResult getMenuById(@PathVariable Integer id) {
        return menuService.getMenuById(id);
    }

    /**
     * 修改菜单
     *
     * @param menu
     * @return
     */
    @PutMapping()
    public ResponseResult updateMenu(@RequestBody Menu menu) {
        return menuService.updateMenu(menu);
    }

    /**
     * 删除菜单
     *
     * @param menuId
     * @return
     */
    @DeleteMapping("{menuId}")
    public ResponseResult deleteMenu(@PathVariable("menuId") Integer menuId) {
        return menuService.deleteMenu(menuId);
    }
}
