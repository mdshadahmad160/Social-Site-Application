package com.social.api.dto;

import com.social.api.annotation.PasswordRepeatEqual;
import com.social.api.annotation.ValidPassword;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@PasswordRepeatEqual(
        passwordFieldFirst = "password",
        passwordFieldSecond = "passwordRepeat"
)
public class ResetPasswordDto {

    @ValidPassword
    private String password;
    private String passwordRepeat;
}
