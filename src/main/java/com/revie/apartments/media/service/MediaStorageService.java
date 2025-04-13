package com.revie.apartments.media.service;

import com.revie.apartments.exceptions.NotFoundException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaStorageService {
    @Value("${revie.media.storage-path}")
    private String storagePath;

    @Value("${revie.media.base-url}")
    private String baseUrl;

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(Paths.get(storagePath));
    }

    public String saveFile(MultipartFile file) throws IOException {
//        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
//        File destinationFile = new File(storagePath + File.separator + fileName);
//        file.transferTo(destinationFile);
//        return baseUrl + "/" + fileName;
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(storagePath).resolve(fileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }

    public UrlResource loadFile(String fileName) {
        try {
            Path path = Paths.get(storagePath).resolve(fileName);
            UrlResource resource = new UrlResource(path.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new NotFoundException("File not found: " + fileName);
            }
        } catch (MalformedURLException e) {
            throw new NotFoundException("File not found: " + fileName);
        }
    }

    public String getMediaUrl(String fileName) {
        return baseUrl + "/" + fileName;
    }
}
