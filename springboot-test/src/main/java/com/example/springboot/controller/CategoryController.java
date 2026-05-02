package com.example.springboot.controller;

import com.example.springboot.entity.Category;
import com.example.springboot.service.CategoryService;
import com.example.springboot.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类控制器
 * 处理分类管理相关请求
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取分类列表
     * 
     * @param name 分类名称（可选），用于搜索
     * @return 分类列表
     */
    @GetMapping("/list")
    public Result<List<Category>> getList(@RequestParam(required = false) String name) {
        List<Category> list;
        if (name != null && !name.trim().isEmpty()) {
            list = categoryService.searchByName(name);
        } else {
            list = categoryService.findAll();
        }
        return Result.success("获取分类列表成功", list);
    }

    /**
     * 根据ID获取分类详情
     * 
     * @param id 分类ID
     * @return 分类详情
     */
    @GetMapping("/{id}")
    public Result<Category> getById(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        if (category == null) {
            return Result.error("分类不存在");
        }
        return Result.success("获取分类成功", category);
    }

    /**
     * 添加分类
     * 
     * @param category 分类信息
     * @return 添加后的分类信息
     */
    @PostMapping
    public Result<Category> add(@RequestBody Category category) {
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            return Result.error("分类名称不能为空");
        }
        Category saved = categoryService.insert(category);
        return Result.success("添加分类成功", saved);
    }

    /**
     * 修改分类
     * 
     * @param category 分类信息
     * @return 修改结果
     */
    @PutMapping
    public Result<Void> update(@RequestBody Category category) {
        if (category.getId() == null) {
            return Result.error("分类ID不能为空");
        }
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            return Result.error("分类名称不能为空");
        }
        categoryService.update(category);
        return Result.success("修改分类成功");
    }

    /**
     * 删除分类
     * 
     * @param id 分类ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        try {
            categoryService.deleteById(id);
            return Result.success("删除分类成功");
        } catch (Exception e) {
            return Result.error("删除分类失败：" + e.getMessage());
        }
    }
}
