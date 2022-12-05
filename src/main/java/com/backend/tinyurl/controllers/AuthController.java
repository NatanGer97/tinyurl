package com.backend.tinyurl.controllers;

import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.*;
import com.backend.tinyurl.Modles.*;
import com.backend.tinyurl.Services.*;
import com.backend.tinyurl.repos.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;



    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/user/register")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user) {
        userService.saveUser(user);
        return ResponseEntity.ok().body(user);

    }

    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody LoginReq loginReq) {
        auth(loginReq.getUsername(), loginReq.getPassword());
        final UserDetails userDetails = userService.loadUserByUsername(loginReq.getUsername());


        // TODO:   move secret to properties file
        /**
         * secret is used to sign the token
         */
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

        String accessToken = JWT.create()
                .withSubject(userDetails.getUsername())
                // 10 min expiry
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .sign(algorithm);

        return new ResponseEntity<>(new LoginRes(accessToken), HttpStatus.OK);
    }

    private void auth(String username, String password) {
        try {
            authenticationManager.authenticate
                    (new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new RuntimeException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new RuntimeException("INVALID_CREDENTIALS", e);
        }
    }
}
