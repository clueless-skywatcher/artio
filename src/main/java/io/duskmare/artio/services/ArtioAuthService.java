package io.duskmare.artio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.duskmare.artio.dtos.requests.SignUpRequest;
import io.duskmare.artio.exceptions.UserAlreadyExistsException;
import io.duskmare.artio.models.ArtioUser;
import io.duskmare.artio.models.ArtioUserRole;
import io.duskmare.artio.repositories.ArtioUserRepository;

@Service
public class ArtioAuthService implements UserDetailsService {
    @Autowired
    private ArtioUserRepository repository;

    @Override
    public ArtioUser loadUserByUsername(String username) throws UsernameNotFoundException {
        ArtioUser user = repository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("No user found with username: " + username));
        return user;
    }

    public UserDetails signUp(SignUpRequest data) {
        if (repository.findByUsername(data.username()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }

        String password = new BCryptPasswordEncoder().encode(data.password());
        ArtioUser user = new ArtioUser(data.username(), password, ArtioUserRole.USER);
        return repository.save(user);
    }

    public ArtioUser getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return repository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("No user found with username: " + username));
    }
    
}
