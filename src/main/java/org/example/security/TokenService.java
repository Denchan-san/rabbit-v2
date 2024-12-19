package org.example.security;

import io.smallrye.jwt.build.Jwt;
import org.example.model.Role;
import java.util.Set;

import jakarta.enterprise.context.ApplicationScoped;
import java.time.Instant;
import java.util.stream.Collectors;

@ApplicationScoped
public class TokenService {

    public String generateToken(String username, Set<Role> roles, Long userId) {
        String roleNames = roles.stream()
                .map(Role::getName)
                .collect(Collectors.joining(","));

        return Jwt.issuer("http://localhost:8080") // This could be your backend API or a URL
                .upn(username) // Unique Identifier (usually the username)
                .claim("userId", userId) // Add user ID as a claim
                .groups(roleNames) // Add roles as a claim
                .expiresAt(Instant.now().plusSeconds(3600)) // Token expiration (1 hour)
                .sign();
    }
}

