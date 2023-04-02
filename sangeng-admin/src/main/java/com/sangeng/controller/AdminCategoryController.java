package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.CategoryDto;
import com.sangeng.domain.entity.Category;
import com.sangeng.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Achen
 */
@RestController
@RequestMapping("/content/category")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 分页查询分类列表
     *
     * @param pageNum
     * @param pageSize
     * @param categoryDto
     * @return
     */
    @GetMapping("/list")
    public ResponseResult listPage(Integer pageNum, Integer pageSize, CategoryDto categoryDto) {
        return categoryService.listPage(pageNum, pageSize, categoryDto);
    }

    /**
     * 新增分类
     *
     * @param category
     * @return
     */
    @PostMapping
    public ResponseResult addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }

    /**
     * 查询分类
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult getCategory(@PathVariable("id") Long id) {
        return categoryService.getCategory(id);
    }

    /**
     * 修改分类
     *
     * @param category
     * @return
     */
    @PutMapping
    public ResponseResult updateCategory(@RequestBody Category category) {
        return categoryService.updateCategory(category);
    }

    /**
     * 删除分类
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseResult deleteCategory(@PathVariable("id") List<Integer> id) {
        return categoryService.deleteCategory(id);
    }
}
