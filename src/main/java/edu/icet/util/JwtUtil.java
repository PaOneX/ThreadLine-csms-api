package edu.icet.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    private final SecretKey key;
    private final long accessTokenExpiryMinutes;
    private final long refreshTokenExpiryDays;

    public JwtUtil(
            @Value("${security.jwt.secret-base64}") String base64Secret,
            @Value("${security.jwt.access-token-expiry-minutes}") long accessTokenExpiryMinutes,
            @Value("${security.jwt.refresh-token-expiry-days}") long refreshTokenExpiryDays
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(base64Secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpiryMinutes = accessTokenExpiryMinutes;
        this.refreshTokenExpiryDays = refreshTokenExpiryDays;
    }

    public String generateAccessToken(String username, List<String> roles) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(accessTokenExpiryMinutes * 60);
        return Jwts.builder()
                .subject(username)
                .claim("roles", roles)
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .signWith(key)
                .compact();
    }

    public Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValid(String token, String username) {
        try {
            Claims claims = parseClaims(token);
            String sub = claims.getSubject();
            return sub != null && sub.equals(username) && claims.getExpiration().after(new Date());
        } catch (Exception ex) {
            return false;
        }
    }

    public long getRefreshTokenExpirySeconds() {
        return refreshTokenExpiryDays * 24L * 3600L;
    }

    public String createRefreshTokenString() {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(getRefreshTokenExpirySeconds());
        return Jwts.builder()
                .subject("refresh")
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .signWith(key)
                .compact();
    }

    public long getAccessTokenExpirySeconds() {
        return accessTokenExpiryMinutes * 60L;
    }
}
