package com.social.api.dto;

import com.social.api.annotation.ValidEmail;
import com.social.api.annotation.ValidPassword;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    @ValidEmail
    private String email;

    @ValidPassword
    private String password;
}
