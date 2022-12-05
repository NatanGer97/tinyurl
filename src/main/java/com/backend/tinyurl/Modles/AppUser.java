package com.backend.tinyurl.Modles;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.*;


import java.util.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class AppUser {
    @Id
    private String id;
    private String name;
    private String username; // email
    private String password;
    private Boolean activeUser = false;

    public AppUser(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.activeUser = false;


    }



}
