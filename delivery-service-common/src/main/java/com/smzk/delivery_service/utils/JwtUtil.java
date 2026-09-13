package com.smzk.delivery_service.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

public class JwtUtil {

    private static final String SECRET_STRING = "c2RmamtkZmpsa2RmamFsa2ZkamZsa2RmamFsa2ZkamZsa2RmamFsa2ZkamZsa2Zk";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_STRING.getBytes(StandardCharsets.UTF_8));
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000L;

    /**
     * 生成 Token（支持自定义过期时间）
     *
     * @param subject     主题
     * @param extraClaims 自定义业务荷载
     * @param expireMillis 过期时间毫秒数
     */
    public static String generateToken(String subject, Map<String, Object> extraClaims, long expireMillis) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date expiration = new Date(nowMillis + expireMillis);

        return Jwts.builder()
                .claims(extraClaims)           // 存入自定义参数
                .subject(subject)               // 存入主体
                .issuedAt(now)                  // 签发时间
                .expiration(expiration)        // 过期时间
                .signWith(SECRET_KEY)          // 使用秘钥签名（自动推断 HS256）
                .compact();
    }

    /**
     * 解析 Token 获取完整 Payload（Claims）
     * 校验失败或过期时会抛出异常
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)        // 设置验证密钥
                .build()
                .parseSignedClaims(token)      // 解析并验证签名
                .getPayload();
    }

}
