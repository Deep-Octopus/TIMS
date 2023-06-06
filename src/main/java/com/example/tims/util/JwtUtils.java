//package com.example.tims.util;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.baomidou.mybatisplus.core.toolkit.StringUtils;
//import com.example.ktp.bean.entity.User;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Date;
//
///**
// * jwt工具类
// */
//@Slf4j
//@Data
//@Component
//@ConfigurationProperties(prefix = "markerhub.jwt")
//public class JwtUtils {
//
//    private String secret;
//    private long expire;
//    private String header;
//
//    /**
//     * 生成jwt token
//     */
//        public  String  generateToken(User user){
//            String token = "";
//            token = JWT.create().withAudience(user.getUsername())
//                    .sign(Algorithm.HMAC256(user.getPassword()));
//            return token;
//        }
//
//    public Claims getClaimByToken(String token) {
//        try {
//            return Jwts.parser()
//                    .setSigningKey(secret)
//                    .parseClaimsJws(token)
//                    .getBody();
//        }catch (Exception e){
//            log.debug("validate is token error ", e);
//            return null;
//        }
//    }
//
//
//    public static String getRequestToken(HttpServletRequest httpRequest) {
//
//        //从header中获取token
//        String token = httpRequest.getHeader("token");
//        //如果header中不存在token，则从参数中获取token
//        if (StringUtils.isBlank(token)) {
//            token = httpRequest.getParameter("token");
//        }
//        return token;
//    }
//
//    /**
//     * token是否过期
//     * @return  true：过期
//     */
//    public static boolean isTokenExpired(Date expiration) {
//        return expiration.before(new Date());
//    }
//}
