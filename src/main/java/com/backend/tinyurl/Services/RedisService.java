package com.backend.tinyurl.Services;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.*;

import java.security.*;
import java.time.*;
import java.util.concurrent.*;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Boolean setIfAbsent(String key, Object value) {
       return  redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    public boolean setWithExpire(String key, Object value, long time){
        try {
            if(time>0){
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            }else{
                redisTemplate.opsForValue().set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * check if key exists in redis or not
     * @param key
     * @return
     */
    public boolean isKeyExist(String key){
        Boolean isExist = redisTemplate.hasKey(key);

        if (isExist != null && isExist) {
            return true;
        }
        return false;
    }


    public Object get(String key) {
        if (redisTemplate.hasKey(key)) {
            return redisTemplate.opsForValue().get(key);
        }

        return null;
    }
}
