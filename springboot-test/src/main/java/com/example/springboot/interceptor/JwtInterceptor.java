package com.example.springboot.interceptor;

import com.example.springboot.entity.Account;
import com.example.springboot.mapper.AccountMapper;
import com.example.springboot.service.TokenBlacklistService;
import com.example.springboot.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * JWT拦截器
 * 拦截所有请求，验证JWT令牌
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            sendErrorResponse(response, "未登录或登录已过期");
            return false;
        }

        token = token.substring(7);
        try {
            if (JwtUtil.isTokenExpired(token)) {
                sendErrorResponse(response, "登录已过期");
                return false;
            }

            String jti = JwtUtil.getJtiFromToken(token);
            if (tokenBlacklistService.isBlacklisted(jti)) {
                sendErrorResponse(response, "令牌已失效，请重新登录");
                return false;
            }

            Long userId = JwtUtil.getUserIdFromToken(token);
            Integer tokenVersion = JwtUtil.getTokenVersionFromToken(token);

            Account account = accountMapper.findById(userId);
            if (account == null) {
                sendErrorResponse(response, "用户不存在");
                return false;
            }

            Integer currentTokenVersion = account.getTokenVersion() != null ? account.getTokenVersion() : 0;
            if (!currentTokenVersion.equals(tokenVersion)) {
                sendErrorResponse(response, "密码已修改，请重新登录");
                return false;
            }

            request.setAttribute("userId", userId);
            request.setAttribute("username", account.getUsername());

            return true;
        } catch (Exception e) {
            sendErrorResponse(response, "令牌验证失败");
            return false;
        }
    }

    private void sendErrorResponse(HttpServletResponse response, String message) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        try {
            response.getWriter().write("{\"code\":1,\"message\":\"" + message + "\"}");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
