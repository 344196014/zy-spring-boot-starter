package com.example.zystarter.FileUploadFactory;

import com.example.zystarter.FileUploadConfig.FileContentType;
import com.example.zystarter.FileUploadConfig.MinioProperties;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

public class MinioUpload  extends AbstractUpload{
    private MinioClient minioClient;
    private MinioProperties minioProperties;
    public MinioUpload(MinioClient minioClient, MinioProperties minioProperties) {
        this.minioClient = minioClient;
        this.minioProperties = minioProperties;
    }

    @Override
    public String upload(MultipartFile file, String fileName) throws FileUploadException {
        if(null == file || StringUtils.isEmpty(fileName)){
            throw new FileUploadException("文件或文件名不存在");
        }
        //从文件获取输入流，objectSize已知，partSize设置为-1意为自动设置
        InputStream inputStream = null;
        long objectSize = file.getSize();
        int partSize = -1;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String contentType = FileContentType.getType(fileName);
        return upload(inputStream,fileName,minioProperties.getBucketName(),objectSize,partSize,contentType);
    }

    private String upload(InputStream inputStream, String fileName, String bucketName, long objectSize, int partSize, String contentType) throws FileUploadException {
        PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                .bucket(bucketName)
                .object(fileName)
                .stream(inputStream,objectSize,partSize)
                .contentType(contentType)
                .build();
        ObjectWriteResponse response = null;
        try {
            response = minioClient.putObject(putObjectArgs);
        } catch (Exception e) {
            throw new FileUploadException("文件上传失败",e);
        }
        return response.etag();
    }

    @Override
    public String preview(String fileId) throws FileUploadException {
        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                .bucket(minioProperties.getBucketName())
                .object(fileId)
                .expiry(1, TimeUnit.MINUTES)
                .method(Method.GET)
                .build();
        String presignedObjectUrl = "";
        try {
            presignedObjectUrl = minioClient.getPresignedObjectUrl(args);
        } catch (Exception e){
            throw new FileUploadException("获取预览地址失败",e);
        }
        return presignedObjectUrl;
    }

    /**
     * 是否需要创建bucket
     */
    public void createBucket() throws FileUploadException {
        BucketExistsArgs bucketExistsArgs = BucketExistsArgs.builder().bucket(minioProperties.getBucketName()).build();
        try{
            if(!minioClient.bucketExists(bucketExistsArgs)){
                MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder().bucket(minioProperties.getBucketName()).build();
                minioClient.makeBucket(makeBucketArgs);
            }
        }catch (Exception e){
            throw new FileUploadException("创建bucket失败",e);
        }
    }
}
