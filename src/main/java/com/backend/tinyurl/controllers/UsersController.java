package com.backend.tinyurl.controllers;

import com.backend.tinyurl.Modles.Casandra.*;
import com.backend.tinyurl.Services.*;
import com.backend.tinyurl.repos.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.*;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UserClickRepository iUserClickRepository;

    @Autowired
    UserService userService;


    @GetMapping("/get-users")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("/{userName}/clicks")
    public ResponseEntity<?> getUserClicks(@PathVariable String userName) {
        // TODO: 13/12/2022  check if user exists
        List<UserClickOut> collect = StreamUtils.createStreamFromIterator(iUserClickRepository.findByUserName(userName).iterator())
                .map(userClick -> UserClickOut.of(userClick))
                .collect(Collectors.toList());

        return new ResponseEntity<>(collect, HttpStatus.OK);

    }







}
