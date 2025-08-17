package com.sysc.workshop.core.security.jwt;

import com.sysc.workshop.core.security.user.SecurityUserDetails;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

//Token Generation & Validation
/*
The “token factory” and “token inspector.”
It doesn’t care how you authenticated —
it takes an Authentication object
(already containing your SecurityUserDetails)
and produces a signed JWT for stateless sessions.
It also verifies and decodes incoming JWTs.

*/
@RequiredArgsConstructor
@Component
public class JwtUtils {

    @Value("${auth.token.jwtSecret}")
    private String jwtSecret;

    @Value("${auth.token.expirationInMils}")
    private long expirationTime;

    private SecretKey key;

    @PostConstruct
    void init() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret.trim());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    //Extracts the authenticated SecurityUserDetails.
    //
    //Embeds the user’s email as subject, plus id and roles as custom claims.
    //
    //Sets issue and expiration timestamps.
    //
    //Signs the token with an HS256 key derived from your jwtSecret.

    /** Generate a signed JWT for the authenticated user */
    public String generateTokenForUser(Authentication authentication) {
        String username = authentication.getName();

        List<String> roles = authentication
            .getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .toList();

        Date now = new Date();
        Date expiry = new Date(System.currentTimeMillis() + expirationTime);

        return Jwts.builder()
            .subject(username) // non-deprecated
            .claim("roles", roles)
            .issuedAt(now)
            .expiration(expiry)
            .signWith(key, Jwts.SIG.HS256) // non-deprecated
            .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            parser().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtException(e.getMessage(), e);
        }
    }

    private JwtParser parser() {
        return Jwts.parser()
            .verifyWith(getSigningKey())
            .clockSkewSeconds(30)
            .build();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret.trim());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
