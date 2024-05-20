package com.mangomelody.mangomelodybackend.auth;

import com.mangomelody.mangomelodybackend.model.entities.UsersEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(UsersEntity user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null) {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token.replace("Bearer", ""))
                    .getBody();

            String username = claims.getSubject();
            List<SimpleGrantedAuthority> authorities = ((List<?>) claims.get("roles")).stream()
                    .map(authority -> new SimpleGrantedAuthority((String) authority))
                    .collect(Collectors.toList());

            if (username != null) {
                return new UsernamePasswordAuthenticationToken(username, null, authorities);
            }
        }
        return null;
    }
}
