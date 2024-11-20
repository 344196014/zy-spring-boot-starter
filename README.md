zy-spring-boot-starter是自定义的spring boot starter，其中集成了对数据库的封装， 当其他项目使用此模块时，只需在pom.xml中添加依赖，即可使用。

一、数据库集成 

1、目前内置了mysql、clickhouse、kingbase8这三种数据库，并且内置了mybatis持久层框架和druid连接池

2、使用方法：在pom.xml中添加依赖，在application.yml中配置数据库连接信息，配置如下
```properties
#默认值为mysql,可省略
zy.db.type=kingbase8
zy.db.database=test
zy.db.ip=
zy.db.port=
zy.db.username=
zy.db.password=
#关于druid连接池配置，使用默认配置，如果自定义配置，格式如下
zy.db.pool.xxx=xxx
```
mybatis默认开启驼峰转换，mapper.xml文件默认位置为resource/mapper/数据库类型名/，其中数据库类型名为zy.db.type的值

二、缓存集成

1、目前内置了单机版redis和哨兵redis，内置lettuce作为redis连接池，连接池采用默认配置初始化，同时内置了redis的key序列化方式，默认为StringRedisSerializer
如需自定义，可自行实现RedisKeySerializer接口，并实现序列化方法，内置key的操作类RedisOperate，可通过注入的方式使用，同时，配置了Redisson作为分布式锁，使用
时注入RedissonClient即可

2、使用方法：在pom.xml中添加依赖，在application.yml中配置数据库连接信息，配置如下
```properties
#默认值为redis_single,可省略
zy.cache.type=redis_single
zy.cache.ip=
zy.cache.port=
zy.cache.password=
#关于lettuce连接池配置，使用默认配置，如果自定义配置，格式如下
zy.cache.pool.xxxx=xxx
#哨兵模式配置
#zy.cache.type=redis_sentinel
#zy.cache.password=root
#zy.cache.sentinel.master=mymaster
#zy.cache.sentinel.nodes=192.168.136.128:26379,192.168.136.128:26380,192.168.136.128:26381
#zy.cache.sentinel.password=root
```
配置完成后，注入RedisOperate、RedissonClient即可使用