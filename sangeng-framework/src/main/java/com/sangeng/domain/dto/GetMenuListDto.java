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
public class GetMenuListDto {
    private String status;
    private String menuName;
}
