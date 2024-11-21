package com.example.zystarter.FileUploadFactory;

import org.springframework.web.multipart.MultipartFile;

public abstract class AbstractUpload {
    public abstract String upload(MultipartFile file, String fileName) throws FileUploadException;

    public abstract String preview(String fileId) throws FileUploadException;
}
