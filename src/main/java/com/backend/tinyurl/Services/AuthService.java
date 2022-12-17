package com.backend.tinyurl.Services;

import com.backend.tinyurl.repos.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;

@Service
public class AuthService implements IAuthService {
    @Autowired
    private RedisService redisService;

    @Override
    public  boolean setKeyWithExpiry(String key, String value, long expiry) {
        return redisService.setWithExpire(key, value, expiry);

    }

    @Override
    public void setKey(String key, String value) {

    }

    @Override
    public String getValue(String key) {
        if (redisService.get(key) != null) {
            return redisService.get(key).toString();
        }

        // TODO: 14/12/2022 throw exception
        return "Key not found";
    }
}
