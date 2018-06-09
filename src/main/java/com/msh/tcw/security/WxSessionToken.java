package com.msh.tcw.security;


import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class WxSessionToken extends AbstractAuthenticationToken {

    private String code;

    public WxSessionToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    public WxSessionToken(String code) {
        super((Collection)null);
        this.code = code;
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getPrincipal() {
        WxSession session = (WxSession) getDetails();
        if (session != null) {
            return session.getSession_key();
        }
        return "";
    }

    public WxSession getDetails() {
        return (WxSession) super.getDetails();
    }

    public String getCode() {
        return code;
    }
}
