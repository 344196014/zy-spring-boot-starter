package com.example.zystarter.CacheConfig;

import com.example.zystarter.CacheConstants.CacheTypeEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "zy.cache")
public class CacheProperties {
    private CacheTypeEnum type = CacheTypeEnum.redis_single;
    private String ip;
    private String port;
    private String password;
    private int timeout = 3000;

    public CacheTypeEnum getType() {
        return type;
    }

    public void setType(CacheTypeEnum type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
