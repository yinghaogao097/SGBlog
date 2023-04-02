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
public class AdminUserDto {
    private String userName;

    private String phonenumber;

    private String status;
}
