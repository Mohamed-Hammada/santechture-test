package com.santechture.api.utils;

import com.santechture.api.configuration.JwtProperties;
import com.santechture.api.constant.ApplicationConstants;
import com.santechture.api.entity.Admin;
import com.santechture.api.entity.IRoles;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtUtils {
    private final JwtProperties jwtProperties;
    public String generateJwtToken(Admin admin, List<IRoles> roles) {
        return Jwts.builder()
                .issuer(jwtProperties.getIssuer())
                .subject(admin.getUsername())
                .issuedAt(new Date())
                .signWith(Keys.hmacShaKeyFor(jwtProperties.getKey().getBytes(StandardCharsets.UTF_8)))
                .claim(ApplicationConstants.ROLES, roles.stream().map(Enum::name).toList())
                .expiration(new Date((new Date()).getTime() + jwtProperties.getExpirationMs()))
                .compact();
    }
    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getKey()));
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getSignInKey())
                .build()
                .parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) getSignInKey())
                    .build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
