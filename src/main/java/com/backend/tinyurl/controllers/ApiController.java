package com.backend.tinyurl.controllers;

import com.backend.tinyurl.Services.*;
import io.swagger.annotations.*;
import org.apache.logging.log4j.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.*;

@RestController
@RequestMapping("/api/")
public class ApiController {

    @Autowired
    RedisService redisService;

    @GetMapping("")
    public String test(){
        return "Hello World";
    }

    @PostMapping("set-key")
    public ResponseEntity<String> setKey(@RequestParam String key, @RequestParam String value)
    {
        Boolean setStatus = redisService.setIfAbsent(key, value);

         if (setStatus) {
            return new ResponseEntity<>("Key set successfully", HttpStatus.OK);
        }
         else {
            return new ResponseEntity<>("Key already exists", HttpStatus.CONFLICT);
         }
    }

    @PostMapping("set-key-expire")
    public ResponseEntity<?> setKeyExpire(
            @RequestParam String key, @RequestParam String value, @RequestParam Long duration)
    {
        if (redisService.setWithExpire(key, value, duration))
        {

        return new ResponseEntity<>("Key set successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("get-value")
    public ResponseEntity<String> getValue(@RequestParam String key)
    {
        Object value = redisService.get(key);

        if (value != null) {
            return new ResponseEntity<>(value.toString(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Key not found", HttpStatus.NOT_FOUND);
        }
    }





}
