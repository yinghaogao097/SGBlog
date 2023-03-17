package com.sangeng.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author：Achen
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotArticleVo {
    @TableId
    private Long id;
    //标题
    private String title;
    //访问量
    private Long viewCount;
}
