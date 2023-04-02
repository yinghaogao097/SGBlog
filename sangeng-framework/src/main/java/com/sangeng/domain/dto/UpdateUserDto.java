package com.sangeng.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Achen
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {
    /**
     * 主键
     */
    private Long id;
    /**
     * 用户名
     */
    @NotNull(message = "用户名不能为空")
    private String userName;
    /**
     * 昵称
     */
    @NotNull(message = "昵称不能为空")
    private String nickName;
    /**
     * 账号状态（0正常 1停用）
     */
    private String status;
    /**
     * 邮箱
     */
    @NotNull(message = "邮箱不能为空")
    /**
     * 用户性别（0男，1女，2未知）
     */
    private String sex;
    private String email;
    private List<Long> roleIds;
}
