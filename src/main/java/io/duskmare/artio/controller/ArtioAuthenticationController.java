package io.duskmare.artio.controller;

import java.util.List;

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

import com.auth0.jwt.exceptions.JWTCreationException;

import io.duskmare.artio.dtos.requests.SignInRequest;
import io.duskmare.artio.dtos.requests.SignUpRequest;
import io.duskmare.artio.dtos.responses.SignInResponse;
import io.duskmare.artio.dtos.responses.SignUpResponse;
import io.duskmare.artio.exceptions.UserAlreadyExistsException;
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
    public ResponseEntity<SignUpResponse> signUp(@RequestBody @Valid SignUpRequest signUpDTO) {
        SignUpResponse response = new SignUpResponse();
        try {
            authService.signUp(signUpDTO);
            
            response.setRole(signUpDTO.role().getValue());
            response.setSuccess(true);
            response.setErrors(List.of());
            response.setUsername(signUpDTO.username());
            
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
        } catch (UserAlreadyExistsException e) {
            response.setSuccess(false);
            response.setUsername(signUpDTO.username());
            response.setRole(signUpDTO.role().getValue());
            response.setErrors(List.of(e.getMessage()));

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);          
        } 
                
        catch (Exception e) {
            e.printStackTrace();

            response.setSuccess(false);
            response.setErrors(List.of(e.getMessage()));

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);                
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<SignInResponse> signIn(@RequestBody @Valid SignInRequest data) {
        SignInResponse response = new SignInResponse();    
        try {
            Authentication usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
            Authentication authUser = authManager.authenticate(usernamePassword);
            String accessToken = tokenProvider.generateAccessToken((ArtioUser) authUser.getPrincipal());
            
            response.setToken(accessToken);
            response.setErrors(List.of());
            response.setSuccess(true);
            
            return ResponseEntity.ok(new SignInResponse(accessToken));
        } catch (JWTCreationException e) {
            response.setErrors(List.of(e.getMessage()));
            response.setSuccess(false);

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } catch (Exception e) {
            response.setErrors(List.of(e.getMessage()));
            response.setSuccess(false);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
