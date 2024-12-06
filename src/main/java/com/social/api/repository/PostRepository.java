package com.social.api.repository;

import com.social.api.entity.Post;
import com.social.api.entity.Tag;
import com.social.api.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findPostsByAuthor(User author, Pageable pageable);

    List<Post> findPostsByAuthorIdIn(List<Long> followingUserIds, Pageable pageable);

    List<Post> findPostsBySharedPost(Post post, Pageable pageable);

    List<Post> findPostsByPostTags(Tag tag, Pageable pageable);

}
