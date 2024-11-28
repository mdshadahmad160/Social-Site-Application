package com.social.api.service.impl;

import com.social.api.dto.SignupDto;
import com.social.api.entity.User;
import com.social.api.repository.UserRepository;
import com.social.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public User createNewUser(SignupDto signupDto) {
        
        return null;
    }
}
