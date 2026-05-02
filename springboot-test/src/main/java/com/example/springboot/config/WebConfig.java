package com.example.springboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * Web配置类
 * 配置Web应用的资源处理，包括文件上传路径
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 文件上传路径
     */
    @Value("${file.upload.path}")
    private String uploadPath;

    /**
     * 配置资源处理
     * 
     * @param registry 资源处理注册器
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        File uploadDir = new File(uploadPath);
        String absolutePath = uploadDir.getAbsolutePath();
        
        /**
         * 配置上传资源处理
         * 
         * 上传文件的路径为 /uploads/**，实际存储路径为 absolutePath + "/"
         */
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + absolutePath + "/");
    }
}