package com.example.springboot.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户实体类
 * 对应数据库中的 account 表
 */
@Data
public class Account {
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private Integer tokenVersion;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
