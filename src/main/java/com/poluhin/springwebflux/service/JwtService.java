package com.poluhin.springwebflux.service;

import com.poluhin.springwebflux.config.JwtProperties;
import com.poluhin.springwebflux.domain.model.AuthTokens;
import com.poluhin.springwebflux.domain.model.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class JwtService {

    private final JwtProperties jwtProperties;

    public AuthTokens generateTokens(User user) {
        String access = generateAccessToken(user);
        String refresh = generateRefreshToken(user);

        return new AuthTokens(access, refresh);
    }

    public String generateAccessToken(User user) {
        Token access = this.jwtProperties.tokens().access();

        return Jwts.builder()
                .setClaims(Map.of("role", user.getAuthorities().stream().map(Object::toString)
                        .collect(Collectors.joining(", "))))
                .setSubject(user.getUsername())
                .setIssuer(this.jwtProperties.issuer())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + access.ttlInSeconds() * 1000))
                .signWith(Keys.hmacShaKeyFor(access.key().getBytes()), SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateRefreshToken(User user) {
        Token refresh = this.jwtProperties.tokens().refresh();

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuer(this.jwtProperties.issuer())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refresh.ttlInSeconds() * 1000))
                .signWith(Keys.hmacShaKeyFor(refresh.key().getBytes()), SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateAccessToken(String token) {
        Token access = this.jwtProperties.tokens().access();

        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(access.key().getBytes()))
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (Exception ex) {
            log.error("Signature validation failed");
        }

        return false;
    }

    public Claims parseAccessClaims(String token) {
        Token access = this.jwtProperties.tokens().access();

        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(access.key().getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
