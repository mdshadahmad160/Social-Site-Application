package com.social.api.service;

import com.social.api.dto.SignupDto;
import com.social.api.entity.User;

public interface UserService {
    User createNewUser(SignupDto signupDto);
}
