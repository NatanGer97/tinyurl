package com.backend.tinyurl.controllers;

import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.*;
import com.backend.tinyurl.Exception.*;
import com.backend.tinyurl.Modles.*;
import com.backend.tinyurl.Services.*;
import com.backend.tinyurl.repos.*;
import com.backend.tinyurl.utils.*;
import lombok.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TinyUrlService tinyUrlService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private AuthService authService;



    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/register")
    public ResponseEntity<AppUser> saveUser(@RequestBody UserIn user) {
        String username = user.getUsername();
        userService.findByUsername(username).ifPresent(u -> {
            throw new UserConflictException("User already exists with username: " + username);
        });
        AppUser newUser = AppUser.AppUserBuilder.anAppUser()
                .withUsername(user.getUsername())
                .withPassword(user.getPassword())
                .withActiveUser(false)
                .withCreatedAt(Dates.nowUTC())
                .withName(user.getName()).build();
        String registerConfirmationCode = tinyUrlService.generateCode();


        authService.setKeyWithExpiry(newUser.getUsername(), registerConfirmationCode, 60 * 2);

//        new Thread(() -> emailService.sendSimpleMessage(user.getUsername(), registerConfirmationCode)).start();


        return ResponseEntity.ok().body(userService.saveUser(newUser));
    }

    @PostMapping("/activate/{userId}")
    public ResponseEntity<?> activateUser(@RequestBody ActivationRequest activationRequest, @PathVariable String userId) {
        String code = authService.getValue(activationRequest.getUserName());
        if (code == null) {
            throw new RuntimeException("User not found");
        }
        if (code.equals(activationRequest.getActivationCode())) {
            AppUser appUser = userService.getUser(userId);
            appUser.setActiveUser(true);
            userService.saveUser(appUser);
            return ResponseEntity.ok().body("User activated");
        }
        return ResponseEntity.badRequest().body("Invalid code");
    }



    @PostMapping("/login")
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
            logger.info("Bad Credentials");
            throw new InvalidCredentialsException();
        }
    }


}
