package com.sangeng.service;

import com.sangeng.utils.SecurityUtils;
import org.springframework.stereotype.Component;

/**
 * 自定义权限判断
 *
 * @author Achen
 */
@Component("ps")
public class PermissionService {

    public boolean hasPermission(String permission) {
        // 如果是超级管理员 返回true
        if (SecurityUtils.isAdmin()) {
            return true;
        }
        // 否则判断是否在当前用户权限里
        return SecurityUtils.getLoginUser().getPermissions().contains(permission);
    }
}
