package com.backend.tinyurl.Services;

import org.springframework.stereotype.*;

import java.util.*;

@Service
public class TinyUrlService {

    private static final int TINY_SIZE = 4;
    private final String CHARS = "ABCDEFHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private Random random = new Random();

    public String generateCode()
    {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < TINY_SIZE; i++) {

            code.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }

        return code.toString();
    }
}
