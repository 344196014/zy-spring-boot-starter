package com.example.zystarter.CacheConfig;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.example.zystarter.CacheConstants.CacheConstants;
import com.example.zystarter.CacheConstants.CacheTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties({CacheProperties.class, CacheSentinelConfig.class,CachePoolConfig.class})
public class CacheAutoConfiguration implements BeanPostProcessor {
    private static final Logger log = LoggerFactory.getLogger(CacheAutoConfiguration.class);
    @Resource
    private CacheProperties cacheProperties;
    @Resource
    private CachePoolConfig poolConfig;
    @Resource
    private CacheSentinelConfig sentinelConfig;

    @PostConstruct
    public void init() {
        log.info("初始化缓存配置开始======================================");
        if(!poolConfig.isEnabled()){
            log.error("当前缓存连接池未启用，enabled:{}",poolConfig.isEnabled());
            System.exit(0);
        }
        //初始化连接池配置
        System.setProperty("spring.redis.lettuce.pool.enabled",String.valueOf(poolConfig.isEnabled()));
        System.setProperty("spring.redis.lettuce.pool.max-active",String.valueOf(poolConfig.getMaxActive()));
        System.setProperty("spring.redis.lettuce.pool.max-wait",String.valueOf(poolConfig.getMaxWait()));
        System.setProperty("spring.redis.lettuce.pool.max-idle",String.valueOf(poolConfig.getMaxIdle()));
        System.setProperty("spring.redis.lettuce.pool.min-idle",String.valueOf(poolConfig.getMinIdle()));

        if(cacheProperties.getType() == CacheTypeEnum.redis_single){
            if(StringUtils.isAnyBlank(cacheProperties.getIp(),cacheProperties.getPort())){
                log.error("当前缓存连接缺少必要配置项，ip:{},port:{},username:{}",cacheProperties.getIp(),cacheProperties.getPort());
                System.exit(0);
            }
            //单机版redis连接
            System.setProperty("spring.redis.host", cacheProperties.getIp());
            System.setProperty("spring.redis.port", cacheProperties.getPort());
            System.setProperty("spring.redis.password", cacheProperties.getPassword());

            log.info("缓存配置完成======================================");
        }else if(cacheProperties.getType() == CacheTypeEnum.redis_sentinel){
            //哨兵版redis连接
            if(null == sentinelConfig){
                log.error("当前缓存为哨兵版，未配置哨兵配置项");
                System.exit(0);
            }else if(StringUtils.isAnyBlank(sentinelConfig.getMaster(),sentinelConfig.getNodes())){
                log.error("当前缓存为哨兵版，未配置哨兵配置项，master:{},nodes:{}",sentinelConfig.getMaster(),sentinelConfig.getNodes());
                System.exit(0);
            }else{
                System.setProperty("spring.redis.password", cacheProperties.getPassword());
                System.setProperty("spring.redis.sentinel.master", sentinelConfig.getMaster());
                System.setProperty("spring.redis.sentinel.nodes", sentinelConfig.getNodes());
                System.setProperty("spring.redis.sentinel.password", sentinelConfig.getPassword());
                log.info("缓存配置完成======================================");
            }
        }else{
            log.error("当前缓存类型未定义，type:{}",cacheProperties.getType());
            System.exit(0);
        }
    }

    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        //序列化问题
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);

        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(fastJsonRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(fastJsonRedisSerializer);

        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedissonClient redissonClient(){
        RedissonClient redissonClient = null;
        if(cacheProperties.getType() == CacheTypeEnum.redis_single){
            Config config = new Config();
            config.useSingleServer()
                    .setAddress(CacheConstants.redisson_prefix+cacheProperties.getIp()+":"+cacheProperties.getPort())
                    .setPassword(cacheProperties.getPassword())
                    .setConnectionPoolSize(poolConfig.getMaxActive())
                    .setConnectionMinimumIdleSize(poolConfig.getMinIdle());
            redissonClient = Redisson.create(config);
        }else if(cacheProperties.getType() == CacheTypeEnum.redis_sentinel){
            String[]sentinelAddresses = sentinelConfig.getNodes().split(",");
            Config config = new Config();
            SentinelServersConfig sentinelServersConfig = config.useSentinelServers();
            for (String sentinelAddress : sentinelAddresses) {
                sentinelServersConfig.addSentinelAddress(CacheConstants.redisson_prefix+sentinelAddress);
            }
            sentinelServersConfig.setMasterName(sentinelConfig.getMaster())
                    .setPassword(sentinelConfig.getPassword())
                    .setMasterConnectionMinimumIdleSize(poolConfig.getMinIdle())
                    .setMasterConnectionPoolSize(poolConfig.getMaxActive());
            redissonClient = Redisson.create(config);
        }else{

        }
        return redissonClient;
    }

    @Bean
    public RedisOperate redisOperate(RedisTemplate redisTemplate){
        return new RedisOperate(redisTemplate);
    }
}
