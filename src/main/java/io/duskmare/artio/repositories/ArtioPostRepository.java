package io.duskmare.artio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.duskmare.artio.models.ArtioPost;
import io.duskmare.artio.models.ArtioUser;
import jakarta.transaction.Transactional;

@Transactional
public interface ArtioPostRepository extends JpaRepository<ArtioPost, Long> {
    public ArtioPost findArtioPostByPostId(Long id);
    public List<ArtioPost> findArtioPostsByAuthor(ArtioUser user);
}
