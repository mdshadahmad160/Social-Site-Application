package com.social.api.service.impl;

import com.social.api.dto.TagDto;
import com.social.api.dto.response.PostResponse;
import com.social.api.entity.Comment;
import com.social.api.entity.Post;
import com.social.api.entity.Tag;
import com.social.api.entity.User;
import com.social.api.exception.PostNotFoundException;
import com.social.api.exception.TagNotFoundException;
import com.social.api.repository.PostRepository;
import com.social.api.service.PostService;
import com.social.api.service.TagService;
import com.social.api.service.UserService;
import com.social.api.util.FileNamingUtil;
import com.social.api.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final UserService userService;

    private final Environment environment;

    private final FileNamingUtil fileNamingUtil;

    private final FileUploadUtil fileUploadUtil;

    private final TagService tagService;

    @Override
    public Post getPostById(Long postId) {

        return postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
    }

    @Override
    public PostResponse getPostResponseById(Long postId) {
        User authUser = userService.getAuthenticatedUser();
        Post foundPost = getPostById(postId);
        return PostResponse.builder()
                .post(foundPost)
                .likedByAuthUser(foundPost.getLikeList().contains(authUser))
                .build();
    }

    @Override
    public List<PostResponse> getPostsByUserPaginate(User authUser, Integer page, Integer size) {
        return postRepository.findPostsByAuthor(authUser, PageRequest.of(page, size, Sort.by(
                        Sort.Direction.DESC, "dateCreated"
                )))
                .stream().map(this::postToPostResponse).collect(Collectors.toList());

    }

    @Override
    public List<PostResponse> getTimelinePostsPaginate(Integer page, Integer size) {
        User authUser = userService.getAuthenticatedUser();
        List<User> followingList = authUser.getFollowingUsers();
        followingList.add(authUser);
        List<Long> followingListIds = followingList.stream().map(User::getId).collect(Collectors.toList());
        return postRepository.findPostsByAuthorIdIn(
                        followingListIds,
                        PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "dateCreated")))
                .stream().map(this::postToPostResponse).collect(Collectors.toList());

    }

    @Override
    public List<PostResponse> getPostSharesPaginate(Post sharedPost, Integer page, Integer size) {
        return postRepository.findPostsBySharedPost(
                sharedPost,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "dateCreated"))
        ).stream().map(this::postToPostResponse).collect(Collectors.toList());
    }

    @Override
    public List<PostResponse> getPostByTagPaginate(Tag tag, Integer page, Integer size) {
        return postRepository.findPostsByPostTags(
                tag,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "dateCreated"))
        ).stream().map(this::postToPostResponse).collect(Collectors.toList());
    }

    @Override
    public Post createNewPost(String content, MultipartFile postPhoto, List<TagDto> postTags) {
        User authUser = userService.getAuthenticatedUser();
        Post newPost = new Post();
        newPost.setContent(content);
        newPost.setAuthor(authUser);
        newPost.setLikeCount(0);
        newPost.setShareCount(0);
        newPost.setCommentCount(0);
        newPost.setIsTypeShare(false);
        newPost.setSharedPost(null);
        newPost.setDateCreated(new Date());
        newPost.setDateLastModified(new Date());

        if (postPhoto != null && postPhoto.getSize() > 0) {
            String uploadDir = environment.getProperty("upload.post.images");
            String newPhotoName = fileNamingUtil.nameFile(postPhoto);

            String newPhotoUrl = environment.getProperty("app.root.backend") + File.separator
                    + environment.getProperty("upload.post.images") + File.separator + newPhotoName;
            newPost.setPostPhoto(newPhotoUrl);
            try {
                fileUploadUtil.saveNewFile(uploadDir, newPhotoName, postPhoto);
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
        if (postTags != null && postTags.size() > 0) {

            postTags.forEach(tagDto -> {

                        Tag tagToAdd = null;
                        try {
                            Tag existingTag = tagService.getTagByName(tagDto.getTagName());
                            if (existingTag != null) {
                                tagToAdd = tagService.increaseTagUseCounter(tagDto.getTagName());
                            }
                        } catch (TagNotFoundException exception) {
                            tagToAdd = tagService.createNewTag(tagDto.getTagName());
                        }
                        newPost.getPostTags().add(tagToAdd);
                    }

            );
        }
        return postRepository.save(newPost);
    }

    @Override
    public Post updatePost(Long postId, String content, MultipartFile postPhoto, List<TagDto> postTags) {
        return null;
    }

    @Override
    public void deletePost(Long postId) {

    }

    @Override
    public void deletePostPhoto(Long postId) {

    }

    @Override
    public void likePost(Long postId) {

    }

    @Override
    public void unlikePost(Long postId) {

    }

    @Override
    public Comment createPostComment(Long postId, String content) {
        return null;
    }

    @Override
    public Comment updatePostComment(Long commentId, Long postId, String content) {
        return null;
    }

    @Override
    public void deletePostComment(Long commentId, Long postId) {

    }

    @Override
    public Post createPostShare(String content, Long postShareId) {
        return null;
    }

    @Override
    public Post updatePostShare(String content, Long postShareId) {
        return null;
    }

    @Override
    public void deletePostShare(Long postShareId) {

    }

    private PostResponse postToPostResponse(Post post) {
        User authUser = userService.getAuthenticatedUser();
        return PostResponse.builder()
                .post(post)
                .likedByAuthUser(post.getLikeList().contains(authUser))
                .build();
    }
}
