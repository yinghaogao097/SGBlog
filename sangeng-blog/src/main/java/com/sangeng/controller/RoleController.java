package com.sangeng.controller;

import com.sangeng.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Achen
 */
@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
}
