package com.example.springboot.controller;

import com.example.springboot.service.AccountService;
import com.example.springboot.util.JwtUtil;
import com.example.springboot.util.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 账户控制器
 * 处理用户登录相关请求
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * 用户登录
     * 
     * @param username 用户名
     * @param password 密码
     * @return 登录结果，成功时返回JWT令牌
     */
    @PostMapping("/login")
    public Result<String> login(@RequestParam String username, @RequestParam String password) {
        try {
            String token = accountService.login(username, password);
            return Result.success("登录成功", token);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 用户注册
     * 
     * @param body 包含username、nickname和password的请求体
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result<Void> register(@RequestBody Map<String, String> body) {
        try {
            String username = body.get("username");
            String nickname = body.get("nickname");
            String password = body.get("password");

            if (username == null || username.isEmpty() || username.length() < 3 || username.length() > 20) {
                return Result.error("账号长度在3到20个字符");
            }

            if (nickname == null || nickname.isEmpty() || nickname.length() < 2 || nickname.length() > 20) {
                return Result.error("昵称长度在2到20个字符");
            }

            if (password == null || password.isEmpty() || password.length() < 6 || password.length() > 20) {
                return Result.error("密码长度在6到20个字符");
            }

            accountService.register(username, nickname, password);
            return Result.success("注册成功", null);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 用户登出
     * 将当前token加入黑名单
     * 
     * @param request HTTP请求
     * @return 登出结果
     */
    @PostMapping("/logout")
    public Result<Void> logout(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                accountService.logout(token);
            }
            return Result.success("登出成功", null);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 修改密码
     * 修改密码后token版本号+1，所有旧token失效
     * 
     * @param request HTTP请求
     * @param body 包含oldPassword和newPassword的请求体
     * @return 修改结果
     */
    @PostMapping("/change-password")
    public Result<Void> changePassword(HttpServletRequest request, @RequestBody Map<String, String> body) {
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return Result.error("未登录");
            }

            String token = authHeader.substring(7);
            Long userId = JwtUtil.getUserIdFromToken(token);
            String oldPassword = body.get("oldPassword");
            String newPassword = body.get("newPassword");

            if (oldPassword == null || newPassword == null || oldPassword.isEmpty() || newPassword.isEmpty()) {
                return Result.error("密码不能为空");
            }

            if (oldPassword.equals(newPassword)) {
                return Result.error("新密码不能与原密码相同");
            }

            accountService.changePassword(userId, oldPassword, newPassword);
            return Result.success("密码修改成功", null);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
