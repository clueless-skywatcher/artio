package io.duskmare.artio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.duskmare.artio.models.ArtioComment;
import io.duskmare.artio.models.ArtioPost;

public interface ArtioCommentRepository extends JpaRepository<ArtioComment, Long> {

    List<ArtioComment> findByPost(ArtioPost post);
    
}
