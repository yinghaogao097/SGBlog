package com.sangeng.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Achen
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetRoleDto {
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色状态
     */
    private String status;
}
