package com.backend.tinyurl.controllers;

import io.swagger.annotations.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("")
    public ResponseEntity<?> hello(){
        return ResponseEntity.ok("hello");
    }
}
