package com.example.zystarter.CacheConfig;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("zy.cache.sentinel")
public class CacheSentinelConfig {
    private String master;
    private String nodes;
    private String password;

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getNodes() {
        return nodes;
    }

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
