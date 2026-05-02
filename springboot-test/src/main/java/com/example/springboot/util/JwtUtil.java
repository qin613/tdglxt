package com.example.springboot.util;

import com.example.springboot.entity.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

/**
 * JWT令牌类，用于生成、验证和解析JWT令牌
 * 
 * 包含用户id、username、nickname、tokenVersion和jti声明，有效期7天
 */
public class JwtUtil {
    /**
     * JWT令牌的密钥，用于签名和验证
     */
    private static final String SECRET = "mySecretKeyForJWTTokenGenerationThatShouldBeLongEnough";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    /**
     * JWT令牌的过期时间，7天
     */
    private static final long EXPIRATION_TIME = 7 * 24 * 60 * 60 * 1000;

    /**
     * 生成JWT令牌，包含用户id、username、nickname、tokenVersion和jti
     * 
     * @param account 用户账户信息
     * @return JWT令牌字符串，有效期7天
     */
    public static String generateToken(Account account) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);
        Integer tokenVersion = account.getTokenVersion() != null ? account.getTokenVersion() : 0;
        String jti = UUID.randomUUID().toString();

        return Jwts.builder()
                .id(jti)
                .subject(account.getUsername())
                .claim("userId", account.getId())
                .claim("username", account.getUsername())
                .claim("nickname", account.getNickname())
                .claim("tokenVersion", tokenVersion)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(SECRET_KEY)
                .compact();
    }

    /**
     * 验证JWT令牌
     * 
     * @param token JWT令牌
     * @return 令牌的Claims对象，包含所有声明信息
     * @throws io.jsonwebtoken.security.SecurityException 当令牌无效或已过期时抛出异常
     */
    public static Claims validateToken(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 从令牌中获取用户名
     * 
     * @param token JWT令牌
     * @return 用户名
     */
    public static String getUsernameFromToken(String token) {
        Claims claims = validateToken(token);
        return claims.get("username", String.class);
    }

    /**
     * 从令牌中获取用户ID
     * 
     * @param token JWT令牌
     * @return 用户ID
     */
    public static Long getUserIdFromToken(String token) {
        Claims claims = validateToken(token);
        return claims.get("userId", Long.class);
    }

    /**
     * 从令牌中获取token版本号
     * 
     * @param token JWT令牌
     * @return token版本号
     */
    public static Integer getTokenVersionFromToken(String token) {
        Claims claims = validateToken(token);
        return claims.get("tokenVersion", Integer.class);
    }

    /**
     * 从令牌中获取jti（JWT ID）
     * 
     * @param token JWT令牌
     * @return jti
     */
    public static String getJtiFromToken(String token) {
        Claims claims = validateToken(token);
        return claims.getId();
    }

    /**
     * 检查令牌是否过期
     * 
     * @param token JWT令牌
     * @return true表示已过期，false表示未过期
     */
    public static boolean isTokenExpired(String token) {
        Claims claims = validateToken(token);
        return claims.getExpiration().before(new Date());
    }

    /**
     * 获取令牌的过期时间
     * 
     * @param token JWT令牌
     * @return 过期时间
     */
    public static Date getExpirationFromToken(String token) {
        Claims claims = validateToken(token);
        return claims.getExpiration();
    }
}
