package com.example.toolexchangeservice.config.auth;

import com.example.toolexchangeservice.model.entity.UserDetail;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration}")
    private int jwtExpiration;

    public String generateToken(Authentication auth) {
        UserDetail user = (UserDetail) auth.getPrincipal();

        return Jwts.builder().setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + this.jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, this.jwtSecret).compact();
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(this.jwtSecret)
                .build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(this.jwtSecret).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            log.error("Invalid Jwt token {}", token);
            e.printStackTrace();
            return false;
        }
    }
}
