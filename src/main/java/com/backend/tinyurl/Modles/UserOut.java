package com.backend.tinyurl.Modles;

import com.backend.tinyurl.utils.*;
import com.fasterxml.jackson.annotation.*;
import org.joda.time.*;


import java.util.*;


/**
 * @Value -> make all fields are made private and final by default, and setters are not generated.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserOut {

    private String id;
    private String name;
    private String username;
    private String profilePicture;
    private Boolean activeUser;
    private Date createdat;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("createdat")
    public LocalDateTime calcCreatedAt() {
        return Dates.atLocalTime(createdat);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserOut(String id, String name, String username, String profilePicture, Boolean activeUser, Date createdat) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.profilePicture = profilePicture;
        this.activeUser = activeUser;
        this.createdat = createdat;
    }

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

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Boolean getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(Boolean activeUser) {
        this.activeUser = activeUser;
    }

    public Date getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Date createdat) {
        this.createdat = createdat;
    }
}
