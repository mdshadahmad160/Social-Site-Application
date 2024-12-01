package com.social.api.service.impl;

import com.social.api.dto.*;
import com.social.api.dto.response.UserResponse;
import com.social.api.entity.Comment;
import com.social.api.entity.Post;
import com.social.api.entity.User;
import com.social.api.enumeration.Role;
import com.social.api.exception.EmailExistsException;
import com.social.api.exception.UserNotFoundException;
import com.social.api.repository.UserRepository;
import com.social.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        User targetUser=getUserById(userId);
        return userRepository.findUsersByFollowingUsers(targetUser,
                         PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "firstName", "lastName")))
                .stream().map(this::userToUserResponse).collect(Collectors.toList());    }

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

    @Override
    public User updateUserInfo(UpdateUserInfoDto updateUserInfoDto) {
        User authUser = getAuthenticatedUser();
        if (updateUserInfoDto.getCountryName()!=null){

        }
        return null;
    }

    @Override
    public User updateEmail(UpdateEmailDto updateEmailDto) {
        return null;
    }

    @Override
    public User updatePassword(UpdatePasswordDto updatePasswordDto) {
        return null;
    }

    @Override
    public User updateProfilePhoto(MultipartFile photo) {
        return null;
    }

    @Override
    public User updateCoverPhoto(MultipartFile photo) {
        return null;
    }

    @Override
    public User verifyEmail(String token) {
        return null;
    }

    @Override
    public void forgotPassword(String email) {

    }

    @Override
    public User resetPassword(String token, ResetPasswordDto resetPasswordDto) {
        return null;
    }

    @Override
    public void deleteUserAccount() {

    }

    @Override
    public void followUser(Long userId) {

    }

    @Override
    public void unfollowUser(Long userId) {

    }

    @Override
    public User getAuthenticatedUser() {
        return null;
    }

    @Override
    public List<UserResponse> getUserSearchResult(String key, Integer page, Integer size) {
        return null;
    }

    @Override
    public List<User> getLikesByPostPaginate(Post post, Integer page, Integer size) {
        return null;
    }

    @Override
    public List<User> getLikesByCommentPaginate(Comment comment, Integer page, Integer size) {
        return null;
    }

    private UserResponse userToUserResponse(User user) {
        User authUser = getAuthenticatedUser();
        return UserResponse.builder()
                .user(user)
                .followedByAuthUser(user.getFollowerUsers().contains(authUser))
                .build();
    }
}
