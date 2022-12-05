package com.backend.tinyurl.config;

import org.springframework.context.annotation.*;
import org.springframework.security.crypto.bcrypt.*;

@Configuration
public class PasswordEncoderConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
