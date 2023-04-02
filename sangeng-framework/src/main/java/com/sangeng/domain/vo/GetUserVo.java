package com.sangeng.domain.vo;

import com.sangeng.domain.entity.Role;
import com.sangeng.domain.entity.User;
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
public class GetUserVo {
    /**
     * 用户所关联的角色id列表
     */
    private List<Long> roleIds;
    /**
     * 所有角色的列表
     */
    private List<Role> roles;
    private User user;
}
