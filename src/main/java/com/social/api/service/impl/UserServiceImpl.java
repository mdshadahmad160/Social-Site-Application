package com.social.api.service.impl;

import com.social.api.dto.SignupDto;
import com.social.api.dto.response.UserResponse;
import com.social.api.entity.User;
import com.social.api.enumeration.Role;
import com.social.api.exception.EmailExistsException;
import com.social.api.exception.UserNotFoundException;
import com.social.api.repository.UserRepository;
import com.social.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<UserResponse> getFollowerUsersPaginate(Long userId, Integer page, Integer size) {
        return null;
    }

    @Override
    public List<UserResponse> getFollowingUsersPaginate(Long userId, Integer page, Integer size) {
        return null;
    }

    @Override
    public User createNewUser(SignupDto signupDto) {
        try {
            User user = getUserByEmail(signupDto.getEmail());
            if (user != null) {

                throw new EmailExistsException();

            }
        } catch (UserNotFoundException userNotFoundException) {
            User newUser = new User();
            newUser.setEmail(signupDto.getEmail());
            newUser.setPassword(passwordEncoder.encode(signupDto.getEmail()));
            newUser.setFirstName(signupDto.getEmail());
            newUser.setLastName(signupDto.getEmail());
            newUser.setFollowingCount(0);
            newUser.setFollowingCount(0);
            newUser.setEnabled(true);
            newUser.setAccountVerified(false);
            newUser.setEmailVerified(false);
            newUser.setJoinDate(new Date());
            newUser.setDateLastModified(new Date());
            newUser.setRole(Role.ROLE_USER.name());
            User savedUser = userRepository.save(newUser);
            return savedUser;


        }

        return null;
    }
}
