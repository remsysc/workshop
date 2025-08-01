package com.sysc.workshop.core.security.jwt;

import com.sysc.workshop.core.security.user.ShopUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

//Token Generation & Validation
@Component
public class JwtUtils {

    @Value("${auth.token.jwtSecret}")
    private String jwtSecret;

    @Value("${auth.token.expirationInMils}")
    private int expirationTime;

    //Extracts the authenticated ShopUserDetails.
    //
    //Embeds the user’s email as subject, plus id and roles as custom claims.
    //
    //Sets issue and expiration timestamps.
    //
    //Signs the token with an HS256 key derived from your jwtSecret.

    /** Generate a signed JWT for the authenticated user */
    public String generateTokenForUser(Authentication authentication) {
        ShopUserDetails user = (ShopUserDetails) authentication.getPrincipal();
        Date now    = new Date();
        Date expiry = new Date(now.getTime() + expirationTime);

        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .subject(user.getEmail())                        // non-deprecated
                .claim("id", user.getId().toString())
                .claim("roles", roles)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(getSigningKey()) // non-deprecated
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return parser().parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().verifyWith((SecretKey) getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true; }
        catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | io.jsonwebtoken.security.SecurityException | IllegalArgumentException e) {
            throw new JwtException(e.getMessage());
    } }

    private JwtParser parser() {
        return Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())
                .build();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
