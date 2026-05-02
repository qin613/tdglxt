package com.example.springboot.controller;

import com.example.springboot.entity.HomeStats;
import com.example.springboot.service.StatisticsService;
import com.example.springboot.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 统计控制器
 * 处理统计数据相关请求
 */
@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    /**
     * 获取首页统计数据
     * 
     * @return 首页统计信息，包括分类数量、甜点数量和月增长率
     */
    @GetMapping("/home")
    public Result<HomeStats> getHomeStats() {
        HomeStats stats = statisticsService.getHomeStats();
        return Result.success("获取统计数据成功", stats);
    }
}
