package io.duskmare.artio.services;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import io.duskmare.artio.dtos.requests.CreatePostRequest;
import io.duskmare.artio.dtos.requests.DescribePostRequest;
import io.duskmare.artio.dtos.requests.DescribePostsRequest;
import io.duskmare.artio.dtos.responses.CreatePostResponse;
import io.duskmare.artio.dtos.responses.DescribePostResponse;
import io.duskmare.artio.dtos.responses.DescribePostsResponse;
import io.duskmare.artio.exceptions.PostNotFoundException;
import io.duskmare.artio.mappers.ArtioPostMapper;
import io.duskmare.artio.models.ArtioPost;
import io.duskmare.artio.models.ArtioUser;
import io.duskmare.artio.repositories.ArtioPostRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArtioPostService {
    private final ArtioPostRepository postRepository;    
    private final ArtioAuthService authService;
    private final ArtioPostMapper postMapper;

    public CreatePostResponse createPost(CreatePostRequest request) {
        CreatePostResponse response = new CreatePostResponse();
        
        try {
            ArtioPost post = postRepository.save(postMapper.createPost(request, authService.getCurrentUser()));
            response.setPostId(post.getPostId());
            response.setSuccess(true);
            response.setErrors(List.of());
        } catch (Exception e) {
            response.setSuccess(false);
            response.setErrors(List.of(e.getMessage()));
        }        

        return response;
    }

    public DescribePostResponse describePost(DescribePostRequest request) {
        DescribePostResponse response = new DescribePostResponse();
        
        try {
            ArtioPost post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post not found"));
            response = postMapper.describePostResponse(post);
            response.setErrors(List.of());
            response.setSuccess(true);
        } catch (Exception e) {
            response.setErrors(List.of(e.getMessage()));
            response.setSuccess(false);
        }
        
        return response;
    }

    public DescribePostsResponse describePosts(@Valid DescribePostsRequest request) {
        DescribePostsResponse response = new DescribePostsResponse();
        try {
            ArtioUser user = authService.loadUserByUsername(request.getUsername());
            List<ArtioPost> posts = postRepository.findArtioPostsByAuthor(user);
            List<DescribePostResponse> postResponses = new ArrayList<>();

            for (ArtioPost post: posts) {
                postResponses.add(postMapper.describePostResponse(post));
            }

            response.setPosts(postResponses);
            response.setErrors(List.of());
            response.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            response.setErrors(List.of(e.getMessage()));
            response.setSuccess(false);
        }
        
        return response;

    }
}
