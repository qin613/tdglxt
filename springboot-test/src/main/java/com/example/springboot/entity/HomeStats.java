package com.example.springboot.entity;

import lombok.Data;

/**
 * 首页统计实体类
 * 用于封装首页统计数据
 */
@Data
public class HomeStats {
    private Integer categoryCount;
    private Integer dessertCount;
    private Double monthlyGrowth;
}
