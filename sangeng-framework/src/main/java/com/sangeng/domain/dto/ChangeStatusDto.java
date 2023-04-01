package com.sangeng.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Achen
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeStatusDto {
    /**
     * 角色id
     */
    private String roleId;
    /**
     * 角色状态
     */
    private String status;
}
