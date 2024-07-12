package com.unravel.api.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtil {

    @Value("${jwt_encoded_key}")
    private String encodedKey;

    public SecretKey getSecretKey() {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);

        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "HMACSHA256");
    }

    public String getBusinessUserToken(Long id, String email) {
        SecretKey key = getSecretKey();
        Date expirationDate = new Date();
        expirationDate = DateUtils.addDays(expirationDate, 1);
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        return Jwts.builder().subject("BUSINESSUSER-" + id).claims(claims).expiration(expirationDate).signWith(key).compact();
    }

    public Claims verifyToken(String token) {
        return Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).getPayload();
    }

}
