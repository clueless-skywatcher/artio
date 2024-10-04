package io.duskmare.artio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.duskmare.artio.dtos.JWTDTO;
import io.duskmare.artio.dtos.SignInDTO;
import io.duskmare.artio.dtos.SignUpDTO;
import io.duskmare.artio.models.ArtioUser;
import io.duskmare.artio.services.ArtioAuthService;
import io.duskmare.artio.services.JWTTokenProviderService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class ArtioAuthenticationController {
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private ArtioAuthService authService;

    @Autowired
    private JWTTokenProviderService tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpDTO signUpDTO) {
        authService.signUp(signUpDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/signin")
    public ResponseEntity<JWTDTO> signIn(@RequestBody @Valid SignInDTO data) {
        Authentication usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        Authentication authUser = authManager.authenticate(usernamePassword);
        String accessToken = tokenProvider.generateAccessToken((ArtioUser) authUser.getPrincipal());
        return ResponseEntity.ok(new JWTDTO(accessToken));
    }
}
