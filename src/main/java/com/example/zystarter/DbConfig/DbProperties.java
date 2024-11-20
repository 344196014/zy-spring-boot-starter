package com.example.zystarter.DbConfig;

import com.example.zystarter.DbConstants.DbTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "zy.db")
public class DbProperties {
    private static final Logger log = LoggerFactory.getLogger(DbProperties.class);
    private DbTypeEnum type = DbTypeEnum.mysql;
    private String ip;
    private String port;
    private String driverClassName;
    private String username;
    private String password;
    private String database;
    private String urlParam;


    public DbTypeEnum getType() {
        return type;
    }

    public void setType(DbTypeEnum type) {
        this.type = type;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
    public String getUrlParam() {
        return urlParam;
    }

    public void setUrlParam(String urlParam) {
        this.urlParam = urlParam;
    }
}
