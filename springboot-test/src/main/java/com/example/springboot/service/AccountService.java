package com.example.springboot.service;

import com.example.springboot.entity.Account;
import com.example.springboot.mapper.AccountMapper;
import com.example.springboot.util.JwtUtil;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务类
 * 处理用户相关的业务逻辑
 */
@Service
public class AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    /**
     * 用户登录
     * 
     * @param username 用户名
     * @param password 密码
     * @return JWT令牌
     * @throws RuntimeException 当用户名不存在或密码错误时抛出异常
     */
    public String login(String username, String password) {
        Account account = accountMapper.findByUsername(username);
        
        if (account == null) {
            throw new RuntimeException("用户名不存在");
        }
        
        if (!account.getPassword().equals(password)) {
            throw new RuntimeException("用户密码错误");
        }
        
        return JwtUtil.generateToken(account);
    }

    /**
     * 用户注册
     * 
     * @param username 用户名
     * @param nickname 昵称
     * @param password 密码
     * @throws RuntimeException 当用户名已存在时抛出异常
     */
    public void register(String username, String nickname, String password) {
        Account existingAccount = accountMapper.findByUsername(username);
        if (existingAccount != null) {
            throw new RuntimeException("用户名已存在");
        }

        Account account = new Account();
        account.setUsername(username);
        account.setNickname(nickname);
        account.setPassword(password);
        account.setTokenVersion(0);
        account.setCreateTime(LocalDateTime.now());
        account.setUpdateTime(LocalDateTime.now());

        accountMapper.insert(account);
    }

    /**
     * 用户登出
     * 将当前token加入黑名单
     * 
     * @param token JWT令牌
     */
    public void logout(String token) {
        String jti = JwtUtil.getJtiFromToken(token);
        Date expiration = JwtUtil.getExpirationFromToken(token);
        tokenBlacklistService.addToBlacklist(jti, expiration);
    }

    /**
     * 修改密码
     * 修改密码后增加token版本号，使所有旧token失效
     * 
     * @param userId 用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @throws RuntimeException 当旧密码错误时抛出异常
     */
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        Account account = accountMapper.findById(userId);
        
        if (account == null) {
            throw new RuntimeException("用户不存在");
        }
        
        if (!account.getPassword().equals(oldPassword)) {
            throw new RuntimeException("原密码错误");
        }
        
        Integer currentTokenVersion = account.getTokenVersion() != null ? account.getTokenVersion() : 0;
        Integer newTokenVersion = currentTokenVersion + 1;
        
        accountMapper.updatePassword(userId, newPassword, newTokenVersion);
    }
}
