package com.social.api.dto.response;

import com.social.api.entity.Comment;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponse {

    private Comment comment;

    private Boolean likedByAuthUser;

}
