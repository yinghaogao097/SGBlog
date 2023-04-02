package com.sangeng.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Achen
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenuTreeSelectVo {
    private List<MenuTreeVo> menus;
    private List<String> checkedKeys;
}
