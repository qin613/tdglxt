package com.example.springboot.service;

import com.example.springboot.entity.HomeStats;
import com.example.springboot.mapper.CategoryMapper;
import com.example.springboot.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 统计服务类
 * 处理首页统计数据相关的业务逻辑
 */
@Service
public class StatisticsService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductMapper productMapper;

    /**
     * 获取首页统计数据
     * 
     * @return 首页统计信息，包含：
     *         - categoryCount: 分类总数
     *         - dessertCount: 甜点（商品）总数
     *         - monthlyGrowth: 月增长率（百分比，保留一位小数）
     */
    public HomeStats getHomeStats() {
        HomeStats stats = new HomeStats();
        stats.setCategoryCount(categoryMapper.findAll().size());
        stats.setDessertCount(productMapper.findAll().size());
        
        int currentMonth = productMapper.countCurrentMonth();
        int lastMonth = productMapper.countLastMonth();
        
        if (lastMonth == 0) {
            if (currentMonth > 0) {
                stats.setMonthlyGrowth(100.0);
            } else {
                stats.setMonthlyGrowth(0.0);
            }
        } else {
            double growth = ((double) (currentMonth - lastMonth) / lastMonth) * 100;
            stats.setMonthlyGrowth(Math.round(growth * 10) / 10.0);
        }
        
        return stats;
    }
}
