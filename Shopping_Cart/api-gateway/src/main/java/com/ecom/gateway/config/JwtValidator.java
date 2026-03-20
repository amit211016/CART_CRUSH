package com.ecom.gateway.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class JwtValidator {

    // Hardcoded Base64 RSA Public Key for seamless project execution
    private static final String RSA_PUBLIC_KEY = 
        "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAs7C/u+0pPeKd4iX7jDchx+/VKPnWPN1tk2Xrr" +
        "cjLtqBF4q+hhC//zM30+CDjmc9x/fbyxYjX+jjHb2RdSNaaqUU4r1exY6ksD1+759fTxUcwBg4/iOBOWX" +
        "dLaXEDS05WKZc7GFjtxqnr9CWmgpXZb+7DFOFcsByrVDnLdO+9FESsYsGlrl5Sq8SZnW5YhzoO3G7Xdcg" +
        "WfC2lqAUEqYXVhNQrwvpINh6slRquynGJC6nje02bTHE/2UaKl/41CD+GH4fx7gkjw1jjLr42rVSnWzxc" +
        "Fb0/3DjfF32QKOI25apj14FrQy0geafJU2WfwE5atOF4TNEPNetzbBqRTLS0uQIDAQAB";

    private PublicKey getPublicKey() {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(RSA_PUBLIC_KEY);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(spec);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load RSA Public Key", e);
        }
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getPublicKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
