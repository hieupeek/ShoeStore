package com.shoestore.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.shoestore.constant.Constant;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // Lấy giá trị từ application.yaml
    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration}")
    private long jwtExpiration;

    // 1. Tạo access Token từ Username
    public String generateAccessToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 2. Lấy Username từ access Token (Giải mã)
    public String getUsernameFromAccessToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // 3. Validate Token
    public boolean validateAccessToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            System.err.println(Constant.ERROR_INVALID_JWT_TOKEN);
        } catch (ExpiredJwtException ex) {
            System.err.println(Constant.ERROR_EXPIRED_JWT_TOKEN);
        } catch (UnsupportedJwtException ex) {
            System.err.println(Constant.ERROR_UNSUPPORTED_JWT_TOKEN);
        } catch (IllegalArgumentException ex) {
            System.err.println(Constant.ERROR_ILLEGAL_JWT_TOKEN);
        }
        return false;
    }

    // Helper: Decode key
    private Key getSignInKey() {
        // Vì key trong yaml chúng ta để dạng text thường, nên dùng getBytes()
        // Nếu bạn dùng chuỗi Base64 thì dùng Decoders.BASE64.decode()
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }
}