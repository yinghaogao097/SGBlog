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
public class ListArticleDto {
    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章摘要
     */
    private String summary;
}
