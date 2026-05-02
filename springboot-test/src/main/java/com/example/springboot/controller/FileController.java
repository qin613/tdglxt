package com.example.springboot.controller;

import com.example.springboot.util.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * 文件控制器
 * 处理文件上传相关请求
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${file.upload.path}")
    private String uploadPath;

    /**
     * 文件上传
     * 
     * @param file 上传的文件
     * @return 上传结果，成功时返回文件访问路径
     */
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("上传文件为空");
        }

        try {
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = UUID.randomUUID().toString() + fileExtension;
            
            Path uploadDir = Paths.get(uploadPath);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
            
            Path filePath = uploadDir.resolve(newFilename);
            Files.copy(file.getInputStream(), filePath);
            
            return Result.success("上传成功", "/uploads/" + newFilename);
        } catch (IOException e) {
            return Result.error("上传失败：" + e.getMessage());
        }
    }
}
