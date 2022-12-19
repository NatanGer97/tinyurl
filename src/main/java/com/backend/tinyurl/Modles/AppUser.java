package com.backend.tinyurl.Modles;

import com.backend.tinyurl.utils.*;
import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.hibernate.validator.constraints.*;
import org.joda.time.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.*;


import javax.validation.constraints.*;
import javax.validation.constraints.Email;
import java.util.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class AppUser {
    @Id
    private String id;
    @Length(min = 3, max = 60)

    private String name;

    @Email
    private String username; // email

    private String profilePicture;

    @Length(min = 4, max = 12)
    @JsonIgnore
    private String password;

    private Boolean activeUser = false;

    @NotNull
    private Date createdAt = Dates.nowUTC();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("createdAt")
    public LocalDateTime calcCreatedAt() {
        return Dates.atLocalTime(createdAt);
    }

    public AppUser(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.activeUser = false;
    }


    public static final class AppUserBuilder {
        private String id;
        private @Length(min = 3, max = 60) String name;
        private @Email String username;
        private String profilePicture;
        private @Length(min = 4, max = 12) String password;
        private Boolean activeUser;
        private @NotNull Date createdAt;

        private AppUserBuilder() {
        }

        public static AppUserBuilder anAppUser() {
            return new AppUserBuilder();
        }

        public AppUserBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public AppUserBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public AppUserBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public AppUserBuilder withProfilePicture(String profilePicture) {
            this.profilePicture = profilePicture;
            return this;
        }

        public AppUserBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public AppUserBuilder withActiveUser(Boolean activeUser) {
            this.activeUser = activeUser;
            return this;
        }

        public AppUserBuilder withCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public AppUser build() {
            AppUser appUser = new AppUser();
            appUser.setId(id);
            appUser.setName(name);
            appUser.setUsername(username);
            appUser.setProfilePicture(profilePicture);
            appUser.setPassword(password);
            appUser.setActiveUser(activeUser);
            appUser.setCreatedAt(createdAt);
            return appUser;
        }
    }
}
