package com.backend.tinyurl.controllers;

import com.backend.tinyurl.Exception.*;
import com.backend.tinyurl.Modles.*;
import com.backend.tinyurl.Modles.Casandra.*;
import com.backend.tinyurl.Services.*;
import com.backend.tinyurl.repos.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.util.*;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.*;
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

    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        AppUser appUser = userService.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        return ResponseEntity.ok().body(appUser);
    }

    @GetMapping("/{userName}/clicks")
    public ResponseEntity<?> getUserClicks(@PathVariable String userName) {
        // user validation
        userService.findByUsername(userName)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + userName));

        List<UserClickOut> collect = StreamUtils.createStreamFromIterator(iUserClickRepository.findByUserName(userName).iterator())
                .map(userClick -> UserClickOut.of(userClick))
                .collect(Collectors.toList());

        return new ResponseEntity<>(collect, HttpStatus.OK);

    }







}
