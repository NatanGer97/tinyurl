package com.backend.tinyurl.controllers;

import com.backend.tinyurl.Modles.Casandra.*;
import com.backend.tinyurl.Modles.TinyUrl.*;
import com.backend.tinyurl.Services.*;
import com.backend.tinyurl.repos.*;
import com.backend.tinyurl.utils.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import java.util.*;

@RestController
@RequestMapping("/api/tinyurl/")
public class TinyUrlController {
    private final int numberOfRetries = 4;
    Logger logger = LoggerFactory.getLogger(TinyUrlController.class);

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    TinyUrlService tinyUrlService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MongodbService mongodbService;

    @Autowired
    private UserClickRepository iUserClickRepository;

    @Value("${base.url}")
    String baseUrl;


    /**
     * method to generate tiny url
     *
     * @param tinyUrlRequest
     * @return
     */
    @PostMapping("/create")
    public String createTinyUrl(@RequestBody TinyUrlRequest tinyUrlRequest) throws JsonProcessingException {
        String tinyCode = tinyUrlService.generateCode();
        int currentRetries = 0;

        if (!tinyUrlRequest.getOriginalUrl().startsWith("https://") || !tinyUrlRequest.getOriginalUrl().startsWith("http://")) {
            tinyUrlRequest.setOriginalUrl("http://" + tinyUrlRequest.getOriginalUrl());
        }

        while (!redisService.setIfAbsent(tinyCode, objectMapper.writeValueAsString(tinyUrlRequest))
                && currentRetries++ < numberOfRetries) {
            tinyCode = tinyUrlService.generateCode();
        }

        if (currentRetries == numberOfRetries) {
            throw new RuntimeException("Can't generate tiny url, all options are taken");
        }

        return baseUrl + tinyCode + "/";

    }

    @GetMapping(value = "/{tiny}/")
    public ModelAndView getOriginalUrl(@PathVariable String tiny) throws JsonProcessingException {
        logger.info("get req for url: " + tiny);

        Object tinyValueFromRedis = redisService.get(tiny);
        TinyUrlRequest tinyUrlRequest = objectMapper.readValue(tinyValueFromRedis.toString(), TinyUrlRequest.class);
        if (tinyUrlRequest.getOriginalUrl() != null) {
            String username = tinyUrlRequest.getUserName();
            if (username != null) {
                mongodbService.incrementMongoField(username, "allUrlsClicks");
                mongodbService.incrementMongoField(username, "shorts_" + tiny + "_clicks_" + Dates.getCurMonth());

                iUserClickRepository.save(UserClick.UserClickBuilder.anUserClick()
                        .withUserClickKey(UserClickKey.UserClickKeyBuilder.anUserClickKey()
                                .withUserName(username).withClickTime(new Date()).build())
                        .withTinyUrl(tiny).withOriginalUrl(tinyUrlRequest.getOriginalUrl()).build());
            }
            return new ModelAndView("redirect:" + tinyUrlRequest.getOriginalUrl());
        } else {
            throw new RuntimeException("Can't find original url for this tiny url");
        }
    }

    @GetMapping("/")
    public String getRoot() {
        return "Welcome to tiny url";
    }
}
