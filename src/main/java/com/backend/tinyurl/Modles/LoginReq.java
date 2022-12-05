package com.backend.tinyurl.Modles;

import lombok.*;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class LoginReq {
    private String username;
    private String password;

}
