package io.duskmare.artio.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.duskmare.artio.models.ArtioUser;

public interface ArtioUserRepository extends JpaRepository<ArtioUser, Long> {
    public Optional<ArtioUser> findByUsername(String username);
}
