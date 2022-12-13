package com.backend.tinyurl.Modles;

import org.hibernate.validator.constraints.*;

import javax.validation.constraints.*;
import javax.validation.constraints.Email;

public class UserIn {
    public UserIn(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;

    }

    @Length(min = 3, max = 60)
    private String name;

    @Email
    private String username; // email

//    private String profilePicture;

    @Length(min = 4, max = 12)
    private String password;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
