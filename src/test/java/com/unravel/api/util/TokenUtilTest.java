package com.unravel.api.util;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@ExtendWith(SpringExtension.class)
@Import(
        value = {
                TokenUtil.class
        }
)
@TestPropertySource(
        locations = "/application.yaml"
)
public class TokenUtilTest {

    @Autowired
    TokenUtil tokenUtil;

    void generateKey() throws NoSuchAlgorithmException {
        SecretKey secretKey = KeyGenerator.getInstance("HMACSHA256").generateKey();
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());

        System.out.println(encodedKey);
    }

    @Test
    void testGetBusinessUserToken() {
        String token = tokenUtil.getBusinessUserToken(1L,"dev");
        System.out.println(token);
        Assertions.assertNotNull(token);
    }

    @Test
    void testVerifyToken() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJCVVNJTkVTU1VTRVItMSIsImVtYWlsIjoiZGV2IiwiZXhwIjoxNzE5NjY0MDgyfQ.Sfh1O3zu53h8Aj3oE-9M9vzQprZ3RsBdQ_lTxpUjvj0";
        Claims claims = tokenUtil.verifyToken(token);

        Assertions.assertEquals("dev", claims.get("email"));
    }

}
