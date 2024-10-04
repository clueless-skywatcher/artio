package io.duskmare.artio.dtos;

import io.duskmare.artio.models.ArtioUserRole;

public record SignUpDTO(String username, String password, ArtioUserRole role){}
