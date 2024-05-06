package com.santechture.api.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

@Slf4j
public class GenerateJwtRandomKeys {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        // Generate a random key
        Key key = generateKey();

        // Convert key to Base64
        String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());

        log.info("Secure JWT Secret: " + base64Key);

        // Generate JWT token
        String jwt = Jwts.builder()
                .subject("Admin")
                .claim("roles", new String[]{"ROLE_ADMIN", "ROLE_USER"})
                .issuedAt(new Date(System.currentTimeMillis()))
                .signWith(Keys.hmacShaKeyFor(key.getEncoded()))
                .compact();

        log.info("Generated JWT: " + jwt);

        // Generate a sample password using BCryptPasswordEncoder
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        log.info("Generated Admin Password: " + bCryptPasswordEncoder.encode("p@ssw0rd"));
    }

    private static SecretKey generateKey() throws NoSuchAlgorithmException {
        byte[] keyBytes = new byte[32]; // 256 bits
        SecureRandom secureRandom = SecureRandom.getInstanceStrong();
        secureRandom.nextBytes(keyBytes);
        return new SecretKeySpec(keyBytes, "HmacSHA256");
    }
}
