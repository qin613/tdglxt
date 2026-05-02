package com.example.springboot.controller;

import com.example.springboot.util.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 * 提供测试接口
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public Result<String> hello() {
        return Result.success("Hello, World!你好，世界~~~");
    }
}
