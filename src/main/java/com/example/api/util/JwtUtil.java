package com.example.api.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;

/**
 * JWT token işlemleri için yardımcı sınıf. Token üretme ve doğrulama işlemlerini gerçekleştirir.
 */
@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret; // JWT token'ları imzalamak için kullanılan gizli anahtar.

    @Value("${jwt.expiration}")
    private long expiration; // JWT token'larının son kullanma süresi.

    /**
     * Belirtilen gizli anahtar kullanılarak bir imza anahtarı üretir.
     *
     * @return JWT token'larını imzalamak için kullanılan Key nesnesi.
     */
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Belirtilen kullanıcı adı için bir JWT token üretir.
     *
     * @param username Token üretilecek kullanıcı adı.
     * @return String olarak JWT token.
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Bir JWT token'dan kullanıcı adını çıkarır.
     *
     * @param token Kullanıcı adının çıkarılacağı JWT token.
     * @return String olarak kullanıcı adı.
     */
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Bir JWT token'ı UserDetails nesnesine karşı doğrular.
     *
     * @param token       Doğrulanacak JWT token.
     * @param userDetails Token'ın doğrulanacağı UserDetails nesnesi.
     * @return Token geçerliyse true, aksi takdirde false döner.
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Bir JWT token'ın süresinin dolup dolmadığını kontrol eder.
     *
     * @param token Süresi kontrol edilecek JWT token.
     * @return Token'ın süresi dolmuşsa true, aksi takdirde false döner.
     */
    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}