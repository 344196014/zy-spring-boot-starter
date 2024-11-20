package com.example.zystarter.CacheConfig;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "zy.cache.pool")
public class CachePoolConfig {
    private boolean enabled = true;
    private int maxActive = 8;//最大连接数（从池中可分配的连接总数）
    private int maxWait = -1;//最大等待时间ms
    private int maxIdle = 8;//最大空闲连接数
    private int minIdle = 0;//最小空闲连接数
    private boolean testOnBorrow = true;//从池中借用连接时是否进行有效性检查
    private boolean testOnReturn = true;//返回连接到池时是否进行有效性检查
    private boolean testWhileIdle = true;//在空闲时是否进行有效性检查
    private int timeBetweenEvictionRuns = 30000;//空闲连接检查的时间间隔ms
    private int minEvictableIdleTime = 60000;//最小可驱逐空闲时间ms

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }
    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public int getTimeBetweenEvictionRuns() {
        return timeBetweenEvictionRuns;
    }

    public void setTimeBetweenEvictionRuns(int timeBetweenEvictionRuns) {
        this.timeBetweenEvictionRuns = timeBetweenEvictionRuns;
    }

    public int getMinEvictableIdleTime() {
        return minEvictableIdleTime;
    }

    public void setMinEvictableIdleTime(int minEvictableIdleTime) {
        this.minEvictableIdleTime = minEvictableIdleTime;
    }
}
