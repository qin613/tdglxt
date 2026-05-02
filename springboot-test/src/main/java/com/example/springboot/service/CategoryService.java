package com.example.springboot.service;

import com.example.springboot.entity.Category;
import com.example.springboot.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分类服务类
 * 处理分类相关的业务逻辑
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 获取所有分类
     * 
     * @return 分类列表，按创建时间倒序排列
     */
    public List<Category> findAll() {
        return categoryMapper.findAll();
    }

    /**
     * 根据ID获取分类
     * 
     * @param id 分类ID
     * @return 分类信息，如果不存在则返回null
     */
    public Category findById(Long id) {
        return categoryMapper.findById(id);
    }

    /**
     * 根据名称搜索分类
     * 
     * @param name 分类名称
     * @return 匹配的分类列表，如果名称为空则返回所有分类
     */
    public List<Category> searchByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return findAll();
        }
        return categoryMapper.searchByName(name.trim());
    }

    /**
     * 添加分类
     * 
     * @param category 分类信息
     * @return 添加后的分类信息（包含自动生成的ID）
     */
    public Category insert(Category category) {
        categoryMapper.insert(category);
        return category;
    }

    /**
     * 修改分类
     * 
     * @param category 分类信息
     * @throws RuntimeException 当分类ID为空时抛出异常
     */
    public void update(Category category) {
        if (category.getId() == null) {
            throw new RuntimeException("分类ID不能为空");
        }
        categoryMapper.update(category);
    }

    /**
     * 根据ID删除分类
     * 
     * @param id 分类ID
     * @throws RuntimeException 当分类ID为空时抛出异常
     */
    public void deleteById(Long id) {
        if (id == null) {
            throw new RuntimeException("分类ID不能为空");
        }
        categoryMapper.deleteById(id);
    }
}
