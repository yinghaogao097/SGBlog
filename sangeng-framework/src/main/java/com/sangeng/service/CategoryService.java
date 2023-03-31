package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.Category;


/**
 * 类别 业务层
 *
 * @author Achen
 */
public interface CategoryService extends IService<Category> {
    /**
     * 查询分类列表
     *
     * @return
     */
    ResponseResult getCategoryList();

    /**
     * 查询所有分类
     *
     * @return
     */
    ResponseResult listAllCategory();
}

