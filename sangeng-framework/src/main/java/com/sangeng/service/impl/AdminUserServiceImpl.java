package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.constants.SystemConstants;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddUserDto;
import com.sangeng.domain.dto.AdminUserDto;
import com.sangeng.domain.dto.UpdateUserDto;
import com.sangeng.domain.dto.UserStatusDto;
import com.sangeng.domain.entity.Role;
import com.sangeng.domain.entity.User;
import com.sangeng.domain.entity.UserRole;
import com.sangeng.domain.vo.GetUserVo;
import com.sangeng.domain.vo.PageVo;
import com.sangeng.enums.AppHttpCodeEnum;
import com.sangeng.exception.SystemException;
import com.sangeng.mapper.RoleMapper;
import com.sangeng.mapper.UserMapper;
import com.sangeng.service.AdminUserService;
import com.sangeng.service.UserRoleService;
import com.sangeng.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Achen
 */
@Service
public class AdminUserServiceImpl extends ServiceImpl<UserMapper, User> implements AdminUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public ResponseResult getUserList(Integer pageNum, Integer pageSize, AdminUserDto adminUserDto) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .like(StringUtils.hasText(adminUserDto.getUserName()), User::getUserName, adminUserDto.getUserName())
                .eq(StringUtils.hasText(adminUserDto.getPhonenumber()), User::getPhonenumber, adminUserDto.getPhonenumber())
                .eq(StringUtils.hasText(adminUserDto.getStatus()), User::getStatus, adminUserDto.getStatus());
        // 创建分页
        Page<User> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        return ResponseResult.okResult(new PageVo(page.getRecords(), page.getTotal()));
    }

    @Override
    public ResponseResult listAllRole() {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getStatus, SystemConstants.ROLE_NORMAL_FLAG);
        List<Role> roleList = roleMapper.selectList(queryWrapper);
        return ResponseResult.okResult(roleList);
    }

    @Override
    public ResponseResult addUser(AddUserDto addUserDto) {
        // bean拷贝
        User user = BeanCopyUtils.copyBean(addUserDto, User.class);
        // 判断手机号码是否存在数据库
        if (hasNumber(user.getPhonenumber())) {
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }
        // 判断邮箱是否存在数据库
        if (hasEmail(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        // 判断用户名是否存在数据
        if (haeUserName(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        // 密码加密
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        userMapper.insert(user);
        // 再向用户角色标签添加添加
        List<UserRole> userRoles = addUserDto.getRoleIds()
                .stream()
                .map(roleId -> new UserRole(user.getId(), roleId))
                .collect(Collectors.toList());
        if (!userRoles.isEmpty()) {
            userRoleService.saveBatch(userRoles);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult changeStatus(UserStatusDto userStatusDto) {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .set(User::getStatus, userStatusDto.getStatus())
                .eq(User::getId, userStatusDto.getUserId());
        update(updateWrapper);
        return null;
    }

    @Override
    public ResponseResult deleteUser(Long id) {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .set(User::getDelFlag, SystemConstants.USER_DELETE_FLAG)
                .eq(User::getId, id);
        update(updateWrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getUser(Long id) {
        // 获取当前角色
        User user = getById(id);
        // 获取当前用户所关联角色列表
        List<UserRole> userRoles = userRoleService.list(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, id));
        List<Long> roles = userRoles
                .stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());
        // 获取所有角色
        List<Role> roleList = roleMapper.selectList(new LambdaQueryWrapper<Role>().eq(Role::getStatus, SystemConstants.ROLE_NORMAL_FLAG));
        return ResponseResult.okResult(new GetUserVo(roles, roleList, user));
    }

    @Override
    public ResponseResult updateUser(UpdateUserDto updateUserDto) {
        // bean拷贝
        User user = BeanCopyUtils.copyBean(updateUserDto, User.class);
        // 更新用户
        updateById(user);
        // 删除原有的用户角色关联表
        userRoleService.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, user.getId()));
        // 添加新的用户角色关联表
        List<UserRole> userRoles = updateUserDto.getRoleIds()
                .stream()
                .map(roleId -> new UserRole(user.getId(), roleId))
                .collect(Collectors.toList());
        if (!userRoles.isEmpty()) {
            userRoleService.saveBatch(userRoles);
        }
        return null;
    }

    private boolean haeUserName(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, userName);
        return count(queryWrapper) > 0;
    }

    private boolean hasEmail(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        return count(queryWrapper) > 0;
    }

    private boolean hasNumber(String phonenumber) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhonenumber, phonenumber);
        return count(queryWrapper) > 0;
    }
}
