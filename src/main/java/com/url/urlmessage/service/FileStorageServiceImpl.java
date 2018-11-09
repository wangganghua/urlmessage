package com.url.urlmessage.service;

import com.url.urlmessage.exception.AppRuntimeException;
import com.url.urlmessage.property.FileStoragePropertys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    public Path fileStorageLocating;

    @Autowired
    FileStoragePropertys fileStoragePropertys;

    @Override
    public void fileStorageService(FileStoragePropertys fileStoragePropertys) {
        this.fileStorageLocating = Paths.get(fileStoragePropertys.getUploadDir())
                .toAbsolutePath()
                .normalize();
        try {
            Files.createDirectories(this.fileStorageLocating);
        } catch (IOException e) {
            e.printStackTrace();
            throw new AppRuntimeException("创建目录失败:" + e);
        }
    }

    /**
     * 保存文件
     *
     * @param file :文件
     * @return
     */
    @Override
    public String storeFile(MultipartFile file) {
        fileStorageService(fileStoragePropertys);
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw new AppRuntimeException("文件包含无效字符:" + fileName);
            }
            Path targetLocation = this.fileStorageLocating.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            throw new AppRuntimeException("创建失败," + fileName, e);
        }
    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocating.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new AppRuntimeException("未找到 " + fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppRuntimeException("未找到：" + fileName, e);
        }
    }
}
