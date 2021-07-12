package com.bamdule.studycafe.common;

import com.bamdule.studycafe.entity.serverconfig.ServerConfig;
import com.bamdule.studycafe.entity.serverconfig.ServerConfigRepository;
import com.bamdule.studycafe.exception.CustomException;
import com.bamdule.studycafe.exception.ExceptionCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.Map;

@Component
public class JWTUtils {
    private JWTUtils() {
    }

    private String key;

    @Autowired
    private ServerConfigRepository serverConfigRepository;

    @PostConstruct
    private void setting() {
        ServerConfig jwtKey = serverConfigRepository.findServerConfigByName("jwt_key");

        if (jwtKey == null) {
            key = "bamdule";
//            throw new RuntimeException("jwt key is empty !!");
        } else {
            key = jwtKey.getValue();
        }
    }

    public String createToken(Map<String, Object> data) {

        //Header 부분 설정
        Map<String, Object> headers = new IdentityHashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        //payload 부분 설정
        Map<String, Object> payloads = data;


        Long expiredTime = 1000 * 60L * 5L; // 토큰 유효 시간 (2시간)
//        Long expiredTime = 1000 * 60L;

        Date ext = new Date(); // 토큰 만료 시간
        ext.setTime(ext.getTime() + expiredTime);

        // 토큰 Builder
        String jwt = Jwts.builder()
                .setHeader(headers) // Headers 설정
                .setClaims(payloads) // Claims 설정
                .setSubject("member") // 토큰 용도
                .setExpiration(ext) // 토큰 만료 시간 설정
                .signWith(SignatureAlgorithm.HS256, key.getBytes()) // HS256과 Key로 Sign
                .compact(); // 토큰 생성

        return jwt;
    }

    //토큰 검증
    public Map<String, Object> verifyJWT(String jwt) {
        Map<String, Object> claimMap = null;
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key.getBytes("UTF-8")) // Set Key
                    .parseClaimsJws(jwt) // 파싱 및 검증, 실패 시 에러
                    .getBody();

            claimMap = claims;

        } catch (ExpiredJwtException e) { // 토큰이 만료되었을 경우
            throw new CustomException(ExceptionCode.TOKEN_EXPIRATION, e);
        } catch (Exception e) {
            throw new CustomException(ExceptionCode.INVALID_TOKEN);
        }

        return claimMap;
    }
}
