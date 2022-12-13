package com.backend.tinyurl.Services;

import com.backend.tinyurl.Modles.*;
import com.backend.tinyurl.repos.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Service @Transactional
public class UserService implements IUserService, UserDetailsService {
    Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser saveUser(AppUser appUser) {
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return userRepository.save(appUser);

    }

    @Override
    public void activateUser(String userId) {

    }

    @Override
    public AppUser getUser(String userId) {
        return userRepository.findById(userId).get();
    }

    @Override
    public List<AppUser> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        User user = new User(appUser.getUsername(), appUser.getPassword(), new ArrayList<>());
        logger.info(user.toString());

        return user;

    }
}
