package com.backend.tinyurl.Modles.Casandra;

import org.springframework.context.annotation.*;
import org.springframework.data.cassandra.core.cql.*;
import org.springframework.data.cassandra.core.mapping.*;

import java.lang.annotation.*;
import java.util.*;

@Table
public class UserClick {
    @PrimaryKey
    private UserClickKey userClickKey;

    private  String tinyUrl;
    private String originalUrl;


    public UserClickKey getUserClickKey() {
        return userClickKey;
    }

    public void setUserClickKey(UserClickKey userClickKey) {
        this.userClickKey = userClickKey;
    }

    public String getTinyUrl() {
        return tinyUrl;
    }

    public void setTinyUrl(String tinyUrl) {
        this.tinyUrl = tinyUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public static final class UserClickBuilder {
        private UserClickKey userClickKey;
        private String tinyUrl;
        private String originalUrl;

        private UserClickBuilder() {
        }

        public static UserClickBuilder anUserClick() {
            return new UserClickBuilder();
        }

        public UserClickBuilder withUserClickKey(UserClickKey userClickKey) {
            this.userClickKey = userClickKey;
            return this;
        }

        public UserClickBuilder withTinyUrl(String tinyUrl) {
            this.tinyUrl = tinyUrl;
            return this;
        }

        public UserClickBuilder withOriginalUrl(String originalUrl) {
            this.originalUrl = originalUrl;
            return this;
        }

        public UserClick build() {
            UserClick userClick = new UserClick();
            userClick.setUserClickKey(userClickKey);
            userClick.setTinyUrl(tinyUrl);
            userClick.setOriginalUrl(originalUrl);
            return userClick;
        }
    }
}
