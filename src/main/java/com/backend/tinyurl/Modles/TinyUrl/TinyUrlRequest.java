package com.backend.tinyurl.Modles.TinyUrl;

public class TinyUrlRequest {

    private String originalUrl;
    private String userName;

    public TinyUrlRequest(String originalUrl, String userName) {
        this.originalUrl = originalUrl;
        this.userName = userName;
    }

    public TinyUrlRequest() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}
