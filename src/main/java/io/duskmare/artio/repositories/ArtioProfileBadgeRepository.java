package io.duskmare.artio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import io.duskmare.artio.models.ArtioProfileBadge;

public interface ArtioProfileBadgeRepository extends JpaRepository<ArtioProfileBadge, Long> {
    
}
