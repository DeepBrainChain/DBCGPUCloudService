package com.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.concurrent.TimeUnit;

public class RedisUtil {

                @Autowired
                private RedisTemplate<String, String> redisTemplate;

                /**
                 * 读取缓存
                 *
                 */
                public String get(final String key) {
                    return redisTemplate.opsForValue().get(key);
                }

                /**
                 * 写入缓存
                 */
                public boolean ins(final String key, String value,int time,TimeUnit unit) {
                    boolean result = false;
                    try {
                        redisTemplate.opsForValue().set(key, value,time, unit);
                        result = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return result;
                }
                public boolean expire(String key, long time) {
                    try {
                        if (time > 0L) {
                            this.redisTemplate.expire(key, time, TimeUnit.SECONDS);
                        }

                        return true;
                    } catch (Exception var5) {
                        var5.printStackTrace();
                        return false;
                    }
                }

                /**
                 * 删除缓存
                 */
                public boolean delete(final String key) {
                    boolean result = false;
                    try {
                        redisTemplate.delete(key);
                        result = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return result;
                }


                public void del(String... key) {
                    if (key != null && key.length > 0) {
                        if (key.length == 1) {
                            redisTemplate.delete(key[0]);
                        } else {
                            redisTemplate.delete(String.valueOf(CollectionUtils.arrayToList(key)));
                        }
                    }
                }
    }
