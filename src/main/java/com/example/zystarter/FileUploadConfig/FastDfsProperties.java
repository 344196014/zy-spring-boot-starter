package com.example.zystarter.FileUploadConfig;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "zy.upload.fastdfs")
public class FastDfsProperties {
    private int connectTimeout = 5;
    private int networkTimeout = 30;
    private String charset = "UTF-8";
    private boolean httpAntiStealToken = false;
    private String httpSecretKey = "";
    private int trackerHttpPort = 80;
    private String trackerServer = "192.168.136.135:22122";
    private String nginxPrefix = "";
    private boolean showFile = false;

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getNetworkTimeout() {
        return networkTimeout;
    }

    public void setNetworkTimeout(int networkTimeout) {
        this.networkTimeout = networkTimeout;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public boolean isHttpAntiStealToken() {
        return httpAntiStealToken;
    }

    public void setHttpAntiStealToken(boolean httpAntiStealToken) {
        this.httpAntiStealToken = httpAntiStealToken;
    }

    public String getHttpSecretKey() {
        return httpSecretKey;
    }

    public void setHttpSecretKey(String httpSecretKey) {
        this.httpSecretKey = httpSecretKey;
    }

    public int getTrackerHttpPort() {
        return trackerHttpPort;
    }

    public void setTrackerHttpPort(int trackerHttpPort) {
        this.trackerHttpPort = trackerHttpPort;
    }

    public String getTrackerServer() {
        return trackerServer;
    }

    public void setTrackerServer(String trackerServer) {
        this.trackerServer = trackerServer;
    }
    public String getNginxPrefix() {
        return nginxPrefix;
    }

    public void setNginxPrefix(String nginxPrefix) {
        this.nginxPrefix = nginxPrefix;
    }

    public boolean isShowFile() {
        return showFile;
    }

    public void setShowFile(boolean showFile) {
        this.showFile = showFile;
    }
}
