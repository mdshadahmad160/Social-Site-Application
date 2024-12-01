package com.social.api.dto.response;

import com.social.api.entity.Post;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponse {

    private Post post;
    private Boolean likedByAuthUser;
}
