package io.duskmare.artio.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.duskmare.artio.dtos.requests.CreatePostRequest;
import io.duskmare.artio.dtos.requests.DescribePostRequest;
import io.duskmare.artio.dtos.requests.DescribePostsRequest;
import io.duskmare.artio.dtos.responses.CreatePostResponse;
import io.duskmare.artio.dtos.responses.DescribePostResponse;
import io.duskmare.artio.dtos.responses.DescribePostsResponse;
import io.duskmare.artio.services.ArtioPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class ArtioPostsController {
    private final ArtioPostService postService;

    @PostMapping
    public ResponseEntity<CreatePostResponse> createPost(@RequestBody @Valid CreatePostRequest request) {
        return ResponseEntity.ok(postService.createPost(request));
    }

    @GetMapping
    public ResponseEntity<DescribePostResponse> describePost(@RequestBody @Valid DescribePostRequest request) {
        return ResponseEntity.ok(postService.describePost(request));
    }

    @GetMapping("/all")
    public ResponseEntity<DescribePostsResponse> describePosts(@RequestBody @Valid DescribePostsRequest request) {
        return ResponseEntity.ok(postService.describePosts(request));
    }
}
