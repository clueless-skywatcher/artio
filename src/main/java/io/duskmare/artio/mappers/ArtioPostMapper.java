package io.duskmare.artio.mappers;

import java.time.Duration;
import java.time.Instant;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import io.duskmare.artio.dtos.requests.CreatePostRequest;
import io.duskmare.artio.dtos.responses.DescribePostResponse;
import io.duskmare.artio.models.ArtioPost;
import io.duskmare.artio.models.ArtioUser;
import io.duskmare.artio.repositories.ArtioCommentRepository;
import io.duskmare.artio.utils.TimeFormatHelper;

@SuppressWarnings("all")
@Mapper(componentModel = "spring")
public abstract class ArtioPostMapper {
    @Autowired
    private ArtioCommentRepository commentRepository;

    @Mapping(target = "author", source = "user")
    @Mapping(target = "likes", constant = "0")
    @Mapping(target = "text", source = "request.text")
    @Mapping(target = "title", source = "request.title")
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    public abstract ArtioPost createPost(CreatePostRequest request, ArtioUser user);

    @Mapping(target = "postId", source = "post.postId")
    @Mapping(target = "title", source = "post.title")
    @Mapping(target = "text", source = "post.text")
    @Mapping(target = "likes", source = "post.likes")
    @Mapping(target = "author", source = "post.author.username")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getElapsedTime(post))")
    @Mapping(target = "success", expression = "java(true)")
    @Mapping(target = "errors", expression = "java(java.util.List.of())")
    public abstract DescribePostResponse describePostResponse(ArtioPost post);

    String getElapsedTime(ArtioPost post) {
        Instant createdAt = post.getCreatedAt();
        Duration elapsed = Duration.between(createdAt, Instant.now());
        return TimeFormatHelper.formatDuration(elapsed);
    }

    Integer commentCount(ArtioPost post) {
        return commentRepository.findByPost(post).size();
    }
}
