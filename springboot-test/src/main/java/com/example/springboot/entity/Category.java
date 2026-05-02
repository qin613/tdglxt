package com.example.springboot.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 分类实体类
 * 对应数据库中的 category 表
 */
@Data
public class Category {
    private Long id;
    private String name;
    private String description;
    private String icon;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
