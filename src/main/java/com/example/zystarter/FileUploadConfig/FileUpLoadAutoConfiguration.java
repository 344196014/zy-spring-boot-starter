package com.example.zystarter.FileUploadConfig;

import com.example.zystarter.FileUploadFactory.AbstractUpload;
import com.example.zystarter.FileUploadFactory.FastDfsUpload;
import com.example.zystarter.FileUploadFactory.FileUploadException;
import com.example.zystarter.FileUploadFactory.MinioUpload;
import io.minio.MinioClient;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@EnableConfigurationProperties({FastDfsProperties.class, MinioProperties.class})
public class FileUpLoadAutoConfiguration {
    @Resource
    private FastDfsProperties fastDfsProperties;
    @Resource
    private MinioProperties minioProperties;

    @Bean
    @ConditionalOnProperty(prefix = "zy.upload", name = "type",havingValue = "fastdfs")
    public AbstractUpload fastDfs() throws MyException, IOException {
        initFastDfs(fastDfsProperties);
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        StorageClient1 storageClient = new StorageClient1(trackerServer, null);
        FastDfsUpload fastDfsUpload = new FastDfsUpload(trackerServer,storageClient,fastDfsProperties);
        return fastDfsUpload;
    }

    private void initFastDfs(FastDfsProperties fastDfsProperties) throws MyException, IOException {
        Properties prop = new Properties();
        prop.setProperty("fastdfs.tracker_servers", fastDfsProperties.getTrackerServer());
        prop.setProperty("fastdfs.connect_timeout_in_seconds", String.valueOf(fastDfsProperties.getConnectTimeout()));
        prop.setProperty("fastdfs.network_timeout_in_seconds",String.valueOf(fastDfsProperties.getNetworkTimeout()));
        prop.setProperty("fastdfs.charset", fastDfsProperties.getCharset());
        prop.setProperty("fastdfs.http_anti_steal_token", String.valueOf(fastDfsProperties.isHttpAntiStealToken()));
        prop.setProperty("fastdfs.http_secret_key",fastDfsProperties.getHttpSecretKey());
        prop.setProperty("fastdfs.http_tracker_http_port", String.valueOf(fastDfsProperties.getTrackerHttpPort()));
        ClientGlobal.initByProperties(prop);
    }

    @Bean
    @ConditionalOnProperty(prefix = "zy.upload",name = "type",havingValue = "minio")
    public AbstractUpload minio() throws FileUploadException {
        MinioClient minioClient = MinioClient.builder()
                .endpoint(minioProperties.getEndPoint())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
        MinioUpload minioUpload = new MinioUpload(minioClient,minioProperties);
        minioUpload.createBucket();
        return minioUpload;
    }
}
