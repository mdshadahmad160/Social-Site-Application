package com.social.api.dto.response;

import com.social.api.entity.User;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private User user;

    private Boolean followedByAuthUser;
}
