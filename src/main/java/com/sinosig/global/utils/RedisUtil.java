package com.sinosig.global.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * Copyright © 2020 SIG.Co.Ltd. All rights reserved.
 *
 * @describe: redis工具类
 * @author: Aladdin.Cao
 */
public class RedisUtil {

    private RedisUtil() {
    }

    private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);
    // 超时间 秒/单位
    public static int SESSION_TIME_OUT = 1800;
    private static JedisSentinelPool jedisSentinelPool = null;

    /**
     * redis服务地址
     */
    public static String REDIS_MASTER_NAME = "";
    public static String REDIS_SENTINEL_ADDRESS = "";
    public static String REDIS_AUTH = "";
    public static int REDIS_DATABASE = 0;

    //读取环境变量
    static {
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("application.yml"));
        Properties properties = yaml.getObject();
        String profilesActive = REDIS_MASTER_NAME = (String) properties.get("spring.profiles.active");
        String ymlFileName = "application-" + profilesActive + ".yml";
        logger.info(ymlFileName);
        yaml.setResources(new ClassPathResource(ymlFileName));
        properties = yaml.getObject();
        REDIS_MASTER_NAME = (String) properties.get("redis.master.name");
        REDIS_SENTINEL_ADDRESS = (String) properties.get("redis.sentinel.address");
        REDIS_AUTH = (String) properties.get("redis.auth");
        REDIS_DATABASE = (int) properties.get("redis.database");
//       logger.info("參數：{}",REDIS_MASTER_NAME+REDIS_SENTINEL_ADDRESS+REDIS_AUTH);
    }


    /**
     * 初始化Redis连接池
     */
    static {
        Set<String> sentinels = new HashSet<String>();
        try {
            logger.info("初始化Redis连接池>>>>>>>>>>>>>>>>>>>start");
            String sentinelAddress = REDIS_SENTINEL_ADDRESS;
            for (String address : sentinelAddress.split("/")) {
                sentinels.add(address);
            }
            JedisPoolConfig config = new JedisPoolConfig();
            /** 最大连接数 */
            config.setMaxTotal(1024);
            /** 最大空闲连接数 */
            config.setMaxIdle(16);
            /** 获取连接时的最大等待毫秒数 */
            config.setMaxWaitMillis(1000);
            /** 在获取连接的时候检查有效性 */
            config.setTestOnBorrow(true);
            jedisSentinelPool = new JedisSentinelPool(REDIS_MASTER_NAME, sentinels, config,0, REDIS_AUTH,REDIS_DATABASE);
            logger.info("初始化Redis连接池>>>>>>>>>>>>>>>>>>>end");
        } catch (Exception e) {
            logger.info("初始化Redis连接池, errmsg: >>>>>>>>>>>>>>>>>>{}", e);
        }
    }

    /**
     * 获取Jedis实例
     *
     * @return
     */
    public synchronized static Jedis getJedis() {
        try {
            if (jedisSentinelPool != null) {
                Jedis resource = jedisSentinelPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.info("获取Jedis实例, errmsg: >>>>>>>>>>>>>>>>>>{}", e);
            return null;
        }
    }

    /**
     * 释放jedis资源
     *
     * @param jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    /**
     * 获取缓存数据
     *
     * @param jedis 缓存对象
     * @param key   主键
     */
    public static String get(Jedis jedis, String key) {
        if (jedis != null) {
            return jedis.get(key);
        }
        return null;
    }

    /**
     * 获取缓存数据列表
     *
     * @param jedis 缓存对象
     * @param key   主键数组
     */
    public static List<String> mget(Jedis jedis, String[] key) {
        try {
            if (jedis != null) {
                List<String> list = jedis.mget(key);
                list.removeAll(Collections.singleton(null));
                return list;
            }
        } catch (Exception e) {
            logger.info("redis获取缓存数据列表:{}", e);
        } finally {
            returnResource(jedis);
        }
        return null;
    }

    /**
     * 批量删除
     *
     * @param jedis
     * @param key
     */
    public static Long del(Jedis jedis, String[] key) {
        try {
            if (key != null && key.length > 0) {
                return jedis.del(key);
            }
        } catch (Exception e) {
            logger.info("redis批量删除:{}", e);
        } finally {
            returnResource(jedis);
        }
        return null;
    }

    /**
     * 删除缓存
     *
     * @param jedis
     * @param key   键值
     */
    public static Long del(Jedis jedis, String key) {
        try {
            if (key != null) {
                return jedis.del(key);
            }
        } catch (Exception e) {
            logger.info("redis删除缓存:{}", e);
        } finally {
            returnResource(jedis);
        }
        return null;
    }

    /**
     * 查询key剩余生命周期
     *
     * @param jedis
     * @param key
     */
    public static Long pttl(Jedis jedis, String key) {
        try {
            if (jedis != null) {
                if (key != null && !"".equals(key)) {
                    return jedis.pttl(key);
                }
            }
        } catch (Exception e) {
            logger.info("redis查询key剩余生命周期：{}", e);
        } finally {
            returnResource(jedis);
        }
        return null;
    }

    /**
     * 存储字符串
     *
     * @param jedis  缓存对象
     * @param key    主键
     * @param value  字符串
     * @param expire 有效时间
     */
    public static void setString(Jedis jedis, String key, String value, int expire) {
        try {
            if (jedis != null) {
                jedis.set(key, value);
                jedis.expire(key, expire);
            }
        } catch (Exception e) {
            logger.info("redis存储字符串：{}", e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 存储字符串
     *
     * @param key    主键
     * @param value  字符串
     * @param expire 有效时间
     */
    public static void setString(String key, String value, int expire) {
        Jedis jedis = getJedis();
        try {
            setString(jedis, key, value, expire);
        } catch (Exception e) {
            logger.info("redis存储字符串报错:{}", e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取缓存数据
     *
     * @param key 主键
     */
    public static String get(String key) {
        Jedis jedis = getJedis();
        String string = null;
        try {
            string = get(jedis, key);
        } catch (Exception e) {
            logger.info("redis获取缓存数据报错:{}", e);
        } finally {
            returnResource(jedis);
        }
        return string;
    }


    /**
     * 存集合
     *
     * @param key
     * @param members
     * @return
     */
    public static @NotNull Long sadd(final String key, final String[] members) {
        Jedis jedis = getJedis();
        Long num = 0L;
        try {
            num = jedis.sadd(key, members);
        } catch (Exception e) {
            logger.info("redis获取缓存数据报错:{}", e);
        } finally {
            returnResource(jedis);
        }
        return num;
    }

    /**
     * 存字符
     *
     * @param key
     * @param members
     * @return
     */
    public static @NotNull Long sadd(final String key, final String members) {
        logger.info("当前服务器的ip地址为:" + key);
        Jedis jedis = getJedis();
        Long num = 0L;
        try {
            num = jedis.sadd(key, members);
        } catch (Exception e) {
            logger.info("redis获取缓存数据报错:{}", e);
        } finally {
            returnResource(jedis);
        }
        return num;
    }

    /**
     * 取集合
     *
     * @param key
     * @return
     */
    public static @NotNull Set<String> smembers(final String key) {
        logger.info("当前服务器的ip地址为:" + key);
        Jedis jedis = getJedis();
        Set<String> members = Collections.EMPTY_SET;
        try {
            members = jedis.smembers(key);
        } catch (Exception e) {
            logger.info("redis获取缓存数据报错:{}", e);
        } finally {
            returnResource(jedis);
        }
        return members;
    }

    /**
     * 删除集合中对应的成员
     *
     * @param key
     * @param members 被删除的成员
     * @return 成功删除的个数
     */
    public static @NotNull Long srem(final String key, final String[] members) {
        Jedis jedis = getJedis();
        Long num = 0L;
        try {
            num = jedis.srem(key, members);
        } catch (Exception e) {
            logger.info("redis获取缓存数据报错:{}", e);
        } finally {
            returnResource(jedis);
        }
        return num;
    }

    /**
     * 删除单个对应的成员
     *
     * @param key
     * @param members 被删除的成员
     * @return 成功删除的个数
     */
    public static @NotNull Long srem(final String key, final String members) {
        Jedis jedis = getJedis();
        Long num = 0L;
        try {
            num = jedis.srem(key, members);
        } catch (Exception e) {
            logger.info("redis获取缓存数据报错:{}", e);
        } finally {
            returnResource(jedis);
        }
        return num;
    }


    /**
     * Redis Lpush 命令将一个或多个值插入到列表头部。 如果 key 不存在，一
     * 个空列表会被创建并执行 LPUSH 操作。
     *
     * @param key
     * @param string
     */
    public static void lpush(final String key, final String... string) {
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            Long lpush = jedis.lpush(key, string);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    /**
     * 自增计数器
     *
     * @param key
     */
    public static void incr(final String key) {
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            jedis.incr(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }


    /**
     * Redis Lrem 根据参数 COUNT 的值，移除列表中与参数 VALUE 相等的元素。
     * <p>
     * COUNT 的值可以是以下几种：
     * <p>
     * count > 0 : 从表头开始向表尾搜索，移除与 VALUE 相等的元素，数量为 COUNT 。
     * count < 0 : 从表尾开始向表头搜索，移除与 VALUE 相等的元素，数量为 COUNT 的绝对值。
     * count = 0 : 移除表中所有与 VALUE 相等的值
     *
     * @param key
     * @param count
     * @param value
     * @return
     */
    public static long lrem(final String key, long count, final String value) {
        Jedis jedis = null;
        Long lrem = 0L;
        try {
            jedis = RedisUtil.getJedis();
            lrem = jedis.lrem(key, 0, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return lrem;
    }


    /**
     * Redis Lrange 返回列表中指定区间内的元素，区间以偏移量 START 和 END 指定。
     * 其中 0 表示列表的第一个元素， 1 表示列表的第二个元素，以此类推。
     * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static List<String> lrange(final String key, final long start, final long end) {
        Jedis jedis = null;
        List<String> list = null;
        try {
            jedis = RedisUtil.getJedis();
            list = jedis.lrange(key, 0, -1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return list;
    }
}
