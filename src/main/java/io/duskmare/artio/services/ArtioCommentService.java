package io.duskmare.artio.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.duskmare.artio.models.ArtioComment;
import io.duskmare.artio.models.ArtioPost;
import io.duskmare.artio.repositories.ArtioCommentRepository;

@Service
public class ArtioCommentService {
    @Autowired
    private ArtioCommentRepository commentRepository;

    public List<ArtioComment> findByPost(ArtioPost post) {
        return commentRepository.findByPost(post);
    }
}
