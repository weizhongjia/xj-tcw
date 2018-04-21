package com.msh.tcw.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by weizhongjia on 2017/12/23.
 */
public class TokenAuthenticationUtils {
    private static final String SECRET = "P@ssw02d";            // JWT密码
    private static final String CLAIM_KEY_SESSIONKEY = "session_key";
    private static final String CLAIM_KEY_OPENID = "openid";
    private static final String CLAIM_KEY_UNIONID = "unionid";


    public static String generateToken(WxSessionToken token) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_OPENID, token.getOpenid());
        claims.put(CLAIM_KEY_SESSIONKEY, token.getSession_key());
        claims.put(CLAIM_KEY_UNIONID, token.getUnionid());
        return generateToken(claims);
    }

    private static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    private static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public static WxSessionToken getSessionFromToken(String token){
        try {
            WxSessionToken sessionToken = new WxSessionToken((Collection<GrantedAuthority>)null);
            final Claims claims = getClaimsFromToken(token);
            sessionToken.setOpenid((String)claims.get(CLAIM_KEY_OPENID));
            sessionToken.setSession_key((String)claims.get(CLAIM_KEY_SESSIONKEY));
            sessionToken.setUnionid((String)claims.get(CLAIM_KEY_UNIONID));
            return sessionToken;
        } catch (Exception e) {
            return null;
        }
    }
}
