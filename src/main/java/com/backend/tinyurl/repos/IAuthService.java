package com.backend.tinyurl.repos;

public interface IAuthService {
    boolean setKeyWithExpiry(String key, String value, long expiry) ;

    void setKey(String key, String value) ;

    String getValue(String key);
}
