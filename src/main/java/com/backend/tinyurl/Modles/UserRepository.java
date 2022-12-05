package com.backend.tinyurl.Modles;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.*;

public interface UserRepository extends MongoRepository<AppUser, String> {
    AppUser findFirstByName(String name);
    Optional<AppUser> findByUsername(String username);

}
