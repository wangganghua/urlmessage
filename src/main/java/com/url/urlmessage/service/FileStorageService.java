package com.url.urlmessage.service;


import com.url.urlmessage.property.FileStoragePropertys;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


public interface FileStorageService {

    public void fileStorageService(FileStoragePropertys rv);

    public String storeFile(MultipartFile file);

    public Resource loadFileAsResource(String fileName);
}
