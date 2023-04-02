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
public class UserStatusDto {
    private String userId;
    private String status;
}
