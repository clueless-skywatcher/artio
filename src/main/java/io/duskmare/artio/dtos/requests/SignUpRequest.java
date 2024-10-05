package io.duskmare.artio.dtos.requests;

import io.duskmare.artio.models.ArtioUserRole;

public record SignUpRequest(String username, String password, ArtioUserRole role){}
