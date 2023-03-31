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
public class UpdateTagDTO {
    private Long id;
    private String name;
    private String remark;
}
