package com.backend.tinyurl.Modles;

import lombok.*;

@Data
public class LoginRes {
    private String jwtToken;

    public LoginRes(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
