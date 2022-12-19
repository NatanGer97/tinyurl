package com.backend.tinyurl.utils;

import com.backend.tinyurl.Modles.*;
import com.backend.tinyurl.Services.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

@Component
public class SeedDB implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(SeedDB.class);
    @Autowired
    private UserService userService;
    @Override
    public void run(String... args) throws Exception {
        if  (userService.findByUsername("test@gmail.com").isEmpty()) {
            AppUser newUser = AppUser.AppUserBuilder.anAppUser()
                    .withUsername("test@gmail.com")
                    .withPassword("test")
                    .withActiveUser(true)
                    .withCreatedAt(Dates.nowUTC())
                    .withName("test user").build();
            logger.info("Creating user: " + newUser);
            userService.saveUser(newUser);
        }

    }
}


