package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.CategoryDto;
import com.sangeng.domain.entity.Category;

import java.util.List;


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

    /**
     * 分页查询分类列表
     *
     * @param pageNum
     * @param pageSize
     * @param categoryDto
     * @return
     */
    ResponseResult listPage(Integer pageNum, Integer pageSize, CategoryDto categoryDto);

    /**
     * 新增分类
     *
     * @param category
     * @return
     */
    ResponseResult addCategory(Category category);

    /**
     * 查询分类
     *
     * @param id
     * @return
     */
    ResponseResult getCategory(Long id);

    /**
     * 修改分类
     *
     * @param category
     * @return
     */
    ResponseResult updateCategory(Category category);

    /**
     * 删除分类
     *
     * @param id
     * @return
     */
    ResponseResult deleteCategory(List<Integer> id);
}

