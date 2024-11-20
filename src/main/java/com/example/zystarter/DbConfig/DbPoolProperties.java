package com.example.zystarter.DbConfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
@ConfigurationProperties(prefix = "zy.db.pool")
public class DbPoolProperties {
    private String type = "com.alibaba.druid.pool.DruidDataSource";
    private Integer initialSize = 5;//初始化时建立物理连接个数
    private Integer minIdel = 5;//最小空闲连接数
    private Integer maxActive = 20;//最大连接数
    private Integer maxWait = 60000;//获取连接的最大等待时间，单位ms
    private Boolean testWhileIdel = true;//检查空闲连接的有效性
    private Integer timeBetweenEvictionRunsMillis = 60000;//定期检查空闲连接的线程休眠时间
    private Integer minEvictableIdleTimeMillis = 300000;//连接在池中保持空闲不被驱逐的最小时间
    private String validationQuery = "SELECT 1";//用于检查连接是否有效的sql
    private Boolean testOnBorrow = false;//从连接池获取连接时检查有效性
    private Boolean testOnReturn = false;//归还连接时检查有效性
    private Boolean poolPreparedStatements = false;//是否启用池化的PreparedStatement
    private String filters = "";//监控统计拦截的filter
    private Integer maxPoolPreparedStatementPerConnectionSize = 20;//每个数据库连接可以缓存的最大 PreparedStatement 数量
    private Boolean useGlobalDataSourceStat = false;//全局数据源统计功能
    private String connectProperties = "";//用于配置数据库连接的额外属性，通常作为 URL 的一部分传递给 JDBC 驱动程序
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(Integer initialSize) {
        this.initialSize = initialSize;
    }

    public Integer getMinIdel() {
        return minIdel;
    }

    public void setMinIdel(Integer minIdel) {
        this.minIdel = minIdel;
    }

    public Integer getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(Integer maxActive) {
        this.maxActive = maxActive;
    }

    public Integer getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(Integer maxWait) {
        this.maxWait = maxWait;
    }

    public Boolean getTestWhileIdel() {
        return testWhileIdel;
    }

    public void setTestWhileIdel(Boolean testWhileIdel) {
        this.testWhileIdel = testWhileIdel;
    }

    public Integer getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(Integer timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public Integer getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(Integer minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    public Boolean getTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(Boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public Boolean getTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(Boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public Boolean getPoolPreparedStatements() {
        return poolPreparedStatements;
    }

    public void setPoolPreparedStatements(Boolean poolPreparedStatements) {
        this.poolPreparedStatements = poolPreparedStatements;
    }

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public Integer getMaxPoolPreparedStatementPerConnectionSize() {
        return maxPoolPreparedStatementPerConnectionSize;
    }

    public void setMaxPoolPreparedStatementPerConnectionSize(Integer maxPoolPreparedStatementPerConnectionSize) {
        this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
    }

    public Boolean getUseGlobalDataSourceStat() {
        return useGlobalDataSourceStat;
    }

    public void setUseGlobalDataSourceStat(Boolean useGlobalDataSourceStat) {
        this.useGlobalDataSourceStat = useGlobalDataSourceStat;
    }

    public String getConnectProperties() {
        return connectProperties;
    }

    public void setConnectProperties(String connectProperties) {
        this.connectProperties = connectProperties;
    }
}
