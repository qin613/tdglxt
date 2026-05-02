package com.example.springboot.util;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TokenCache {
    private static final Cache<String, Boolean> cache = Caffeine.newBuilder()
            .maximumSize(10000)
            .expireAfterWrite(7, TimeUnit.DAYS)
            .build();

    private TokenCache() {
    }

    public static Cache<String, Boolean> getInstance() {
        return cache;
    }

    public static void addToBlacklist(String jti, Date expiration) {
        cache.put(jti, true);
    }

    public static boolean isBlacklisted(String jti) {
        return Boolean.TRUE.equals(cache.getIfPresent(jti));
    }

    public static void removeFromBlacklist(String jti) {
        cache.invalidate(jti);
    }

    public static void clearAll() {
        cache.invalidateAll();
    }
}
