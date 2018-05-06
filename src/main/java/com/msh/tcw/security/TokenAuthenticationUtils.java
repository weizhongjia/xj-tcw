package com.msh.tcw.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msh.tcw.model.AuthorityName;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

/**
 * Created by weizhongjia on 2017/12/23.
 */
public class TokenAuthenticationUtils {
    private final Logger log = LoggerFactory.getLogger(TokenAuthenticationUtils.class);
    private static final String SECRET = "P@ssw02d";            // JWT密码
    private static final String CLAIM_KEY_PRINCIPAL = "principal";
    private static final String CLAIM_KEY_CREDENTIALS = "credentials";
    private static final String CLAIM_KEY_DETAILS = "details";


    public static String generateToken(AbstractAuthenticationToken token) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_CREDENTIALS, token.getCredentials());
        claims.put(CLAIM_KEY_PRINCIPAL, token.getPrincipal());
        claims.put(CLAIM_KEY_DETAILS, token.getDetails());
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
            List<GrantedAuthority> list = new ArrayList<>(1);
            list.add(new SimpleGrantedAuthority(AuthorityName.USER.name()));
            WxSessionToken sessionToken = new WxSessionToken(list);
            final Claims claims = getClaimsFromToken(token);
            final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
            final WxSession session = mapper.convertValue(claims.get(CLAIM_KEY_DETAILS), WxSession.class);
            sessionToken.setDetails(session);
            return sessionToken;
        } catch (Exception e) {

            return null;
        }
    }

    public static UsernamePasswordAuthenticationToken getAdminSessionFromToken (String token) {
        try {
            List<GrantedAuthority> list = new ArrayList<>(1);
            list.add(new SimpleGrantedAuthority(AuthorityName.ADMIN.name()));
            final Claims claims = getClaimsFromToken(token);
            UsernamePasswordAuthenticationToken adminToken = new UsernamePasswordAuthenticationToken(
                    claims.get(CLAIM_KEY_PRINCIPAL),
                    claims.get(CLAIM_KEY_CREDENTIALS),
                    list);
            return adminToken;
        } catch (Exception e) {
            return null;
        }
    }
}
