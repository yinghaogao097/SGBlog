package com.sangeng.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Achen
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllCategoryVo {
    private Long id;
    private String description;
    private String name;
}
