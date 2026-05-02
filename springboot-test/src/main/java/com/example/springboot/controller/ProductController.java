package com.example.springboot.controller;

import com.example.springboot.entity.PageResult;
import com.example.springboot.service.ProductService;
import com.example.springboot.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 甜点控制器
 * 处理甜点管理相关请求
 */
@RestController
@RequestMapping("/dessert")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 分页查询甜点列表
     * 
     * @param categoryId 分类ID（可选）
     * @param name 甜点名称（可选）
     * @param page 当前页码，默认为1
     * @param pageSize 每页大小，默认为10
     * @return 分页结果，包含甜点列表和总数
     */
    @GetMapping("/page")
    public Result<PageResult<Map<String, Object>>> getPage(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageResult<Map<String, Object>> result = productService.findPage(categoryId, name, page, pageSize);
        return Result.success("获取甜点列表成功", result);
    }

    /**
     * 根据ID获取甜点详情
     * 
     * @param id 甜点ID
     * @return 甜点详情
     */
    @GetMapping("/{id}")
    public Result<Map<String, Object>> getById(@PathVariable Long id) {
        Map<String, Object> product = productService.findById(id);
        if (product == null) {
            return Result.error("甜点不存在");
        }
        return Result.success("获取甜点成功", product);
    }

    /**
     * 添加甜点
     * 
     * @param product 甜点信息
     * @return 添加后的甜点信息
     */
    @PostMapping
    public Result<Map<String, Object>> add(@RequestBody Map<String, Object> product) {
        if (product.get("name") == null || product.get("name").toString().trim().isEmpty()) {
            return Result.error("甜点名称不能为空");
        }
        if (product.get("price") == null) {
            return Result.error("甜点价格不能为空");
        }
        if (product.get("stock") == null) {
            return Result.error("甜点库存不能为空");
        }
        Map<String, Object> saved = productService.insert((Map) product);
        return Result.success("添加甜点成功", saved);
    }

    /**
     * 修改甜点
     * 
     * @param product 甜点信息
     * @return 修改结果
     */
    @PutMapping
    public Result<Void> update(@RequestBody Map<String, Object> product) {
        if (product.get("id") == null) {
            return Result.error("甜点ID不能为空");
        }
        if (product.get("name") == null || product.get("name").toString().trim().isEmpty()) {
            return Result.error("甜点名称不能为空");
        }
        if (product.get("price") == null) {
            return Result.error("甜点价格不能为空");
        }
        if (product.get("stock") == null) {
            return Result.error("甜点库存不能为空");
        }
        productService.update((Map) product);
        return Result.success("修改甜点成功");
    }

    /**
     * 删除甜点
     * 
     * @param id 甜点ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        try {
            productService.deleteById(id);
            return Result.success("删除甜点成功");
        } catch (Exception e) {
            return Result.error("删除甜点失败：" + e.getMessage());
        }
    }
}
