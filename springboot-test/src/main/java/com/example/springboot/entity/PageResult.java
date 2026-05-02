package com.example.springboot.entity;

import lombok.Data;
import java.util.List;

/**
 * 分页结果类
 * 用于封装分页查询结果
 */
@Data
public class PageResult<T> {
    private List<T> records;  // 数据列表
    private Long total;        // 总记录数
}
