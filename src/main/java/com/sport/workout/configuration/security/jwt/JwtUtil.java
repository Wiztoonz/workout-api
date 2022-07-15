package com.sport.workout.configuration.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.key}")
    private String key;
    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    public String generateJwtToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        claims.put("roles", authorities);
        return doGenerateToken(claims, String.valueOf(userDetails.getUsername()));
    }

    public Boolean isValidateToken(String token, UserDetails userDetails) {
        final String username = getSubject(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public String getSubject(String token) {
        return getClaimsFromToken(token, Claims::getSubject);
    }

    private Key getSecretKey() {
        return Keys.hmacShaKeyFor(Base64.getUrlEncoder().encode(key.getBytes()));
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        Date issueAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(issueAt.getTime() + jwtExpirationMs);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(issueAt)
                .setExpiration(expiration)
                .signWith(getSecretKey())
                .compact();
    }

    private Claims getAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpirationDate(String token) {
        return getClaimsFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDate(token);
        return expiration.before(new Date());
    }

}
