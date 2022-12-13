package com.backend.tinyurl.Modles.Casandra;

import com.backend.tinyurl.utils.*;
import com.fasterxml.jackson.annotation.*;
import org.joda.time.*;

import java.util.*;

public class UserClickOut {
    private String userName;
    private Date clickTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("clickTime")
    public LocalDateTime calcClickTime() {
     return Dates.atLocalTime(clickTime);
    }

    private String tinyUrl;
    private String originalUrl;

    public String getUserName() {
        return userName;
    }

    public Date getClickTime() {
        return clickTime;
    }

    public String getTinyUrl() {
        return tinyUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public static UserClickOut of(UserClick userClick) {
        UserClickOut response = new UserClickOut();
        response.userName = userClick.getUserClickKey().getUserName();
        response.clickTime = userClick.getUserClickKey().getClickTime();
        response.tinyUrl = userClick.getTinyUrl();
        response.originalUrl = userClick.getOriginalUrl();

        return response;
    }
}
