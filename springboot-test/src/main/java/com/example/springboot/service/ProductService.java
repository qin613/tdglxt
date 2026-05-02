package com.example.springboot.service;

import com.example.springboot.entity.PageResult;
import com.example.springboot.entity.Product;
import com.example.springboot.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 商品服务类
 * 处理商品相关的业务逻辑
 */
@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    /**
     * 获取所有商品
     * 
     * @return 商品列表，按创建时间倒序排列
     */
    public List<Product> findAll() {
        return productMapper.findAll();
    }

    /**
     * 根据ID获取商品（包含分类名称）
     * 
     * @param id 商品ID
     * @return 商品信息，如果不存在则返回null
     */
    public Map<String, Object> findById(Long id) {
        return productMapper.findById(id);
    }

    /**
     * 添加商品
     * 
     * @param product 商品信息
     * @return 添加后的商品信息（包含自动生成的ID）
     */
    public Map<String, Object> insert(Map<String, Object> product) {
        Product p = new Product();
        if (product.get("name") != null) {
            p.setName(product.get("name").toString());
        }
        if (product.get("price") != null) {
            p.setPrice(new java.math.BigDecimal(product.get("price").toString()));
        }
        if (product.get("categoryId") != null) {
            p.setCategoryId(Long.valueOf(product.get("categoryId").toString()));
        }
        if (product.get("stock") != null) {
            p.setStock(Integer.valueOf(product.get("stock").toString()));
        }
        if (product.get("description") != null) {
            p.setDescription(product.get("description").toString());
        }
        if (product.get("image") != null) {
            p.setImage(product.get("image").toString());
        }
        productMapper.insert(p);
        Map<String, Object> result = new java.util.HashMap<>();
        result.put("id", p.getId());
        return result;
    }

    /**
     * 修改商品
     * 
     * @param product 商品信息
     * @throws RuntimeException 当商品ID为空时抛出异常
     */
    public void update(Map<String, Object> product) {
        if (product.get("id") == null) {
            throw new RuntimeException("商品ID不能为空");
        }
        Product p = new Product();
        p.setId(Long.valueOf(product.get("id").toString()));
        if (product.get("name") != null) {
            p.setName(product.get("name").toString());
        }
        if (product.get("price") != null) {
            p.setPrice(new java.math.BigDecimal(product.get("price").toString()));
        }
        if (product.get("categoryId") != null) {
            p.setCategoryId(Long.valueOf(product.get("categoryId").toString()));
        }
        if (product.get("stock") != null) {
            p.setStock(Integer.valueOf(product.get("stock").toString()));
        }
        if (product.get("description") != null) {
            p.setDescription(product.get("description").toString());
        }
        if (product.get("image") != null) {
            p.setImage(product.get("image").toString());
        }
        productMapper.update(p);
    }

    /**
     * 根据ID删除商品
     * 
     * @param id 商品ID
     * @throws RuntimeException 当商品ID为空时抛出异常
     */
    public void deleteById(Long id) {
        if (id == null) {
            throw new RuntimeException("商品ID不能为空");
        }
        productMapper.deleteById(id);
    }

    /**
     * 分页查询商品列表
     * 
     * @param categoryId 分类ID（可选）
     * @param name 商品名称（可选）
     * @param page 当前页码
     * @param pageSize 每页大小
     * @return 分页结果，包含商品列表和总数
     */
    public PageResult<Map<String, Object>> findPage(Long categoryId, String name, Integer page, Integer pageSize) {
        if (page == null || page < 1) {
            page = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        
        Map<String, Object> params = new java.util.HashMap<>();
        if (categoryId != null) {
            params.put("categoryId", categoryId);
        }
        if (name != null && !name.trim().isEmpty()) {
            params.put("name", name.trim());
        }
        
        int offset = (page - 1) * pageSize;
        params.put("offset", offset);
        params.put("limit", pageSize);
        
        List<Map<String, Object>> list = productMapper.findPageList(params);
        Long total = productMapper.countByCondition(params);
        
        PageResult<Map<String, Object>> result = new PageResult<>();
        result.setRecords(list);
        result.setTotal(total);
        return result;
    }
}
