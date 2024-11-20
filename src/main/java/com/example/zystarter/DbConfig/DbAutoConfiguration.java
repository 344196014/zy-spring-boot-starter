package com.example.zystarter.DbConfig;

import com.example.zystarter.DbConstants.DbConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties({DbProperties.class,DbPoolProperties.class})
public class DbAutoConfiguration implements BeanPostProcessor, EnvironmentAware {
    private static final Logger log = LoggerFactory.getLogger(DbAutoConfiguration.class);
    @Resource
    private DbProperties dbProperties;
    @Resource
    private DbPoolProperties poolProperties;

    public DbAutoConfiguration() {
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    private Environment environment;

    /**
     * 采用System.setProperty("属性"，“属性值”)的方式
     * 初始化连接和池配置
     */
    @PostConstruct
    public void init(){
        log.info("开始初始化数据库配置");
        if(StringUtils.isAnyBlank(dbProperties.getIp(),dbProperties.getPort(),dbProperties.getUsername(),dbProperties.getPassword(),dbProperties.getDatabase())){
            log.error("当前数据库连接缺少必要配置项，ip:{},port:{},username:{},password:{},database:{}",dbProperties.getIp(),dbProperties.getPort(),dbProperties.getUsername(),dbProperties.getPassword(),dbProperties.getDatabase());
            System.exit(0);
        }
        //数据库连接url
        String url = "";
        switch(dbProperties.getType()){
            case mysql:
                if(StringUtils.isEmpty(dbProperties.getDriverClassName())){
                    dbProperties.setDriverClassName(DbConstants.MYSQL_DRIVER_CLASS);
                }
                url = DbConstants.MYSQL_JDBC_PREFIX+dbProperties.getIp()+":"+dbProperties.getPort()+"/"+dbProperties.getDatabase();
                break;
            case clickhouse:
                if(StringUtils.isEmpty(dbProperties.getDriverClassName())){
                    dbProperties.setDriverClassName(DbConstants.CLICKHOUSE_DRIVER_CLASS);
                }
                url = DbConstants.CLICKHOUSE_JDBC_PREFIX+dbProperties.getIp()+":"+dbProperties.getPort()+"/"+dbProperties.getDatabase();
                break;
            case kingbase8:
                if(StringUtils.isEmpty(dbProperties.getDriverClassName())){
                    dbProperties.setDriverClassName(DbConstants.KINGBASE8_DRIVER_CLASS);
                }
                url = DbConstants.KINGBASE8_JDBC_PREFIX+dbProperties.getIp()+":"+dbProperties.getPort()+"/"+dbProperties.getDatabase();
                break;
            default:
                log.error("不支持的数据库类型：{}", dbProperties.getType());
                System.exit(0);

        }
        if(StringUtils.isNotEmpty(dbProperties.getUrlParam())){
            url = url+"?"+dbProperties.getUrlParam();
        }
        if(StringUtils.isEmpty(environment.getProperty("mybatis.mapper-locations"))){
            System.setProperty("mybatis.mapper-locations",String.format("classpath*:/mapper/%s/**/*.xml", dbProperties.getType().toString()));
        }
        if(StringUtils.isEmpty(environment.getProperty("mybatis.configuration.mapUnderscoreToCamelCase"))){
            System.setProperty("mybatis.configuration.mapUnderscoreToCamelCase","true");
        }
        //初始化数据库连接
        System.setProperty("spring.datasource.url",url);
        System.setProperty("spring.datasource.username",dbProperties.getUsername());
        System.setProperty("spring.datasource.password",dbProperties.getPassword());
        System.setProperty("spring.datasource.driverClassName",dbProperties.getDriverClassName());
        //初始化Druid连接池
        System.setProperty("spring.datasource.type",poolProperties.getType());
        System.setProperty("spring.datasource.druid.initial-size",poolProperties.getInitialSize().toString());
        System.setProperty("spring.datasource.druid.min-evictable-idle-time-millis",poolProperties.getMinEvictableIdleTimeMillis().toString());
        System.setProperty("spring.datasource.druid.time-between-eviction-runs-millis",poolProperties.getTimeBetweenEvictionRunsMillis().toString());
        System.setProperty("spring.datasource.druid.test-while-idle",poolProperties.getTestWhileIdel().toString());
        System.setProperty("spring.datasource.druid.max-wait",poolProperties.getMaxWait().toString());
        System.setProperty("spring.datasource.druid.max-active",poolProperties.getMaxActive().toString());
        System.setProperty("spring.datasource.druid.min-idle",poolProperties.getMinIdel().toString());
        System.setProperty("spring.datasource.druid.validation-query",poolProperties.getValidationQuery());
        System.setProperty("spring.datasource.druid.test-on-borrow",poolProperties.getTestOnBorrow().toString());
        System.setProperty("spring.datasource.druid.test-on-return",poolProperties.getTestOnReturn().toString());
        System.setProperty("spring.datasource.druid.pool-prepared-statements",poolProperties.getPoolPreparedStatements().toString());
        System.setProperty("spring.datasource.druid.filters",poolProperties.getFilters());
        System.setProperty("spring.datasource.druid.max-pool-prepared-statement-per-connection-size",poolProperties.getMaxPoolPreparedStatementPerConnectionSize().toString());
        System.setProperty("spring.datasource.druid.use-global-data-source-stat",poolProperties.getUseGlobalDataSourceStat().toString());
        System.setProperty("spring.datasource.druid.connect-properties",poolProperties.getConnectProperties());
        log.info("数据库配置初始化完成");
    }
}
