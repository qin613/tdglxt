package com.example.springboot.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.springboot.util.TokenCache;

import java.util.Date;

/**
 * Token黑名单服务
 * 用于管理被注销或失效的JWT令牌
 */
@Service
public class TokenBlacklistService {

    /**
     * 将令牌加入黑名单
     * 使用Caffeine Cache，自动在token过期时间后清理
     * 
     * @param jti JWT ID
     * @param expiration 令牌过期时间
     */
    public void addToBlacklist(String jti, Date expiration) {
        TokenCache.addToBlacklist(jti, expiration);
    }

    /**
     * 检查令牌是否在黑名单中
     * 
     * @param jti JWT ID
     * @return true表示在黑名单中，false表示不在
     */
    public boolean isBlacklisted(String jti) {
        return TokenCache.isBlacklisted(jti);
    }

    /**
     * 从黑名单中移除令牌
     * 
     * @param jti JWT ID
     */
    public void removeFromBlacklist(String jti) {
        TokenCache.removeFromBlacklist(jti);
    }
}
