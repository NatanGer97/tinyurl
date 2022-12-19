package com.backend.tinyurl.repos;


import com.backend.tinyurl.Modles.*;

import java.util.*;

public interface IUserService {
    AppUser saveUser(AppUser appUser);

    void activateUser(String userId);

    AppUser getUser(String userId);

    List<AppUser> getUsers();
    Optional<AppUser> findByUsername(String username);
}
