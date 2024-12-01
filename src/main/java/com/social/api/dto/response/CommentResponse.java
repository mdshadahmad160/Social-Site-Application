package com.social.api.dto.response;

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
