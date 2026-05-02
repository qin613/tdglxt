package com.example.springboot.util;

import java.io.Serializable;
/**
 * 统一响应结果类
 * 用于封装API响应结果，包含状态码、消息和数据
 * @param <T> 数据类型
 */
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer code;

    private String message;

    private T data;

    public Result() {
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功响应（无数据）
     * 
     * @param <T> 数据类型
     * @return 成功的响应对象
     */
    public static <T> Result<T> success() {
        return new Result<>(0, "操作成功", null);
    }

    /**
     * 成功响应（带数据）
     * 
     * @param <T> 数据类型
     * @param data 响应数据
     * @return 成功的响应对象
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(0, "操作成功", data);
    }

    /**
     * 成功响应（带消息和数据）
     * 
     * @param <T> 数据类型
     * @param message 响应消息
     * @param data 响应数据
     * @return 成功的响应对象
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(0, message, data);
    }

    /**
     * 成功响应（仅带消息）
     * 
     * @param <T> 数据类型
     * @param message 响应消息
     * @return 成功的响应对象
     */
    public static <T> Result<T> success(String message) {
        return new Result<>(0, message, null);
    }

    /**
     * 失败响应（默认消息）
     * 
     * @param <T> 数据类型
     * @return 失败的响应对象
     */
    public static <T> Result<T> error() {
        return new Result<>(1, "操作失败", null);
    }

    /**
     * 失败响应（带消息）
     * 
     * @param <T> 数据类型
     * @param message 错误消息
     * @return 失败的响应对象
     */
    public static <T> Result<T> error(String message) {
        return new Result<>(1, message, null);
    }

    /**
     * 失败响应（带消息和数据）
     * 
     * @param <T> 数据类型
     * @param message 错误消息
     * @param data 响应数据
     * @return 失败的响应对象
     */
    public static <T> Result<T> error(String message, T data) {
        return new Result<>(1, message, data);
    }

    /**
     * 判断响应是否成功
     * 
     * @return true表示成功，false表示失败
     */
    public boolean isSuccess() {
        return this.code != null && this.code == 0;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
