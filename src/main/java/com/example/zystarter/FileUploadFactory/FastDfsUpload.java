package com.example.zystarter.FileUploadFactory;

import com.example.zystarter.FileUploadConfig.FastDfsProperties;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ProtoCommon;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;

public class FastDfsUpload extends AbstractUpload{
    private TrackerServer trackerServer;
    private StorageClient1 storageClient;
    private FastDfsProperties fastDfsProperties;

    public FastDfsUpload(TrackerServer trackerServer, StorageClient1 storageClient, FastDfsProperties fastDfsProperties) {
        this.trackerServer = trackerServer;
        this.storageClient = storageClient;
        this.fastDfsProperties = fastDfsProperties;
    }

    @Override
    public String upload(MultipartFile file, String fileName) throws FileUploadException {
        if(null == file || StringUtils.isEmpty(fileName)){
            return "文件或文件名为空";
        }
        byte[] bytes;
        try{
            bytes = file.getBytes();
        }catch (IOException e){
            throw new FileUploadException("文件解析失败：",e);
        }
        return upload(bytes,fileName);
    }

    public String upload(byte[] fileContent,String fileName) throws FileUploadException {
        try{
            //文件元信息
            NameValuePair[] nameValuePairs = new NameValuePair[1];
            nameValuePairs[0] = new NameValuePair("fileName",fileName);
            //执行上传
            String fileId = storageClient.upload_file1(fileContent,fileName.substring(fileName.lastIndexOf(".")+1),nameValuePairs);
            return fileId;
        }catch (Exception e){
            throw new FileUploadException("上传文件失败",e);
        }
    }

    @Override
    public String preview(String fileId) throws FileUploadException {
        if(fastDfsProperties.isShowFile()){
            if(fastDfsProperties.isHttpAntiStealToken()){
                //开启了安全访问，需要token
                String secretKey = fastDfsProperties.getHttpSecretKey();
                String fileName = getFileName(fileId);
                int ts = (int) Instant.now().getEpochSecond();
                String token = "";
                try{
                    token = ProtoCommon.getToken(fileName,ts,secretKey);
                }catch (Exception e){
                    throw new FileUploadException("获取token失败",e);
                }
                return fastDfsProperties.getNginxPrefix()+"/"+fileId+"?token="+token+"&ts="+ts;
            }else{
                return fastDfsProperties.getNginxPrefix()+"/"+fileId;
            }
        }else {
            return "当前设置文件不可预览";
        }
    }

    private String getFileName(String fileId) {
        String[] results = new String[2];
        StorageClient1.split_file_id(fileId, results);
        return results[1];
    }


}
