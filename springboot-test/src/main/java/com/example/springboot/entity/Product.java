package com.example.springboot.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品实体类
 * 对应数据库中的 product 表
 */
@Data
public class Product {
    private Long id;
    private String name;
    private BigDecimal price;
    private Long categoryId;
    private Integer stock;
    private String description;
    private String image;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
