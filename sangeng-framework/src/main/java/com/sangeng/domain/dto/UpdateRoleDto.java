package com.sangeng.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Achen
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoleDto {

    private Long id;
    private String remark;
    private String roleKey;
    private String roleSort;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色状态
     */
    private String status;
    /**
     * 菜单id
     */
    private List<Long> menuIds;
}
