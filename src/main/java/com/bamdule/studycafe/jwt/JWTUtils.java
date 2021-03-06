package com.bamdule.studycafe.jwt;

import com.bamdule.studycafe.entity.serverconfig.ServerConfig;
import com.bamdule.studycafe.entity.serverconfig.ServerConfigRepository;
import com.bamdule.studycafe.exception.CustomException;
import com.bamdule.studycafe.exception.ExceptionCode;
import com.bamdule.studycafe.jwt.EmailPayload;
import com.bamdule.studycafe.jwt.MemberPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private String memberTokenKey;
    private String emailTokenKey;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ServerConfigRepository serverConfigRepository;

    @PostConstruct
    private void setting() {
        ServerConfig jwtMemberKey = serverConfigRepository.findServerConfigByName("jwt_member_key");
        ServerConfig jwtEmailKey = serverConfigRepository.findServerConfigByName("jwt_email_key");

        if (jwtMemberKey != null) {
            this.memberTokenKey = jwtMemberKey.getValue();
        } else {
            this.memberTokenKey = "testMemberKey";
        }
        if (jwtEmailKey != null) {
            this.emailTokenKey = jwtEmailKey.getValue();
        } else {
            this.emailTokenKey = "testEmailKey";
        }

    }

    public String createMemberToken(MemberPayload memberPayload) {
        Map data = objectMapper.convertValue(memberPayload, Map.class);
        return createToken(data, memberTokenKey);
    }

    public String createEmailToken(EmailPayload emailPayload) {
        Map data = objectMapper.convertValue(emailPayload, Map.class);
        return createToken(data, emailTokenKey);
    }


    public MemberPayload verifyMemberJWT(String jwt) {
        Map<String, Object> tokenData = verifyJWT(jwt, memberTokenKey);
        return objectMapper.convertValue(tokenData, MemberPayload.class);
    }

    public EmailPayload verifyEmailJWT(String jwt) {
        Map<String, Object> tokenData = verifyJWT(jwt, emailTokenKey);
        return objectMapper.convertValue(tokenData, EmailPayload.class);
    }


    //?????? ??????
    private String createToken(Map<String, Object> data, String key) {

        //Header ?????? ??????
        Map<String, Object> headers = new IdentityHashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        //payload ?????? ??????
        Map<String, Object> payloads = data;


        Long expiredTime = 1000 * 60L * 5L; //
//        Long expiredTime = 1000 * 60L;

        Date ext = new Date(); // ?????? ?????? ??????
        ext.setTime(ext.getTime() + expiredTime);

        // ?????? Builder
        String jwt = Jwts.builder()
                .setHeader(headers) // Headers ??????
                .setSubject("token")
                .setClaims(payloads) // Claims ??????
                .setExpiration(ext) // ?????? ?????? ?????? ??????
                .signWith(SignatureAlgorithm.HS256, key.getBytes()) // HS256??? Key??? Sign
                .compact(); // ?????? ??????

        return jwt;
    }

    //?????? ??????
    private Map<String, Object> verifyJWT(String jwt, String key) {

        Map<String, Object> claimMap = null;
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key.getBytes("UTF-8")) // Set Key
                    .parseClaimsJws(jwt) // ?????? ??? ??????, ?????? ??? ??????
                    .getBody();

            claimMap = claims;

        } catch (ExpiredJwtException e) { // ????????? ??????????????? ??????
            throw new CustomException(ExceptionCode.TOKEN_EXPIRATION, e);
        } catch (Exception e) {
            throw new CustomException(ExceptionCode.INVALID_TOKEN, e);
        }

        return claimMap;
    }
}
