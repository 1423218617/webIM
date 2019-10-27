package com.hx.webim.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


@Component
public class RedisUtil {

    private static StringRedisTemplate stringRedisTemplate;

    public static void set(String key,String value){
        stringRedisTemplate.opsForValue().set(key,value);
    }

    public static String get(String key){
        if (key!=null)
        return stringRedisTemplate.opsForValue().get(key);
        return null;
    }
    public static void set(String key,String value,long time){
        if (key!=null)
        stringRedisTemplate.opsForValue().set(key,value,time, TimeUnit.SECONDS);
    }
    public static boolean hasKey(String key){
        if (key!=null)
        return stringRedisTemplate.hasKey(key);
        return false;
    }
    public static boolean delete(String key){
        return stringRedisTemplate.delete(key);
    }


    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        RedisUtil.stringRedisTemplate = stringRedisTemplate;
    }
}
