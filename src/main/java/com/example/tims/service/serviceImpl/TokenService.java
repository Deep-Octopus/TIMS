package com.example.tims.service.serviceImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {

    @Value("${jwt.secret}") // 应用程序配置中的JWT密钥
    private String secret;

    @Value("${jwt.expiration}") // 应用程序配置中的JWT有效期（秒）
    private long expiration;

    // 生成JWT令牌
    public String generateToken(String username,String userType) {
        Date issuedAt = new Date();
        Date expirationTime = new Date(issuedAt.getTime() + expiration * 1000); // 毫秒转为秒
        return Jwts.builder()
                .claim("username",username)
                .claim("user_type",userType)
                .setSubject(username)
                .setIssuedAt(issuedAt)
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    // 解析JWT令牌，获取用户名和用户类型
    public Map<String, String> getUsernameAndUserTypeFromToken(String token) {
//        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
//        return claims.getSubject();
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        String username = claims.get("username", String.class);
        String userType = claims.get("user_type", String.class);
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("user_type", userType);
        return map;
    }

    // 验证JWT令牌的有效性
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
