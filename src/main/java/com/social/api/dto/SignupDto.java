package com.social.api.dto;

import com.social.api.annotation.PasswordRepeatEqual;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@PasswordRepeatEqual( passwordFieldFirst = "password",
        passwordFieldSecond = "passwordRepeat")
public class SignupDto {
    private String email;
}
