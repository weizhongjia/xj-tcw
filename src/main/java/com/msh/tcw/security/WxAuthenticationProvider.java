package com.msh.tcw.security;

import com.alibaba.fastjson.JSONObject;
import com.msh.tcw.model.AuthorityName;
import com.msh.tcw.utils.WxPath;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by weizhongjia on 2017/12/23.
 */
// 自定义身份认证验证组件
public class WxAuthenticationProvider implements AuthenticationProvider {

    @Value("${wx.appid}")
    private String wxAppid;
    @Value("${wx.secret}")
    private String wxSecret;
    @Autowired
    RestTemplate restTemplate;
    private final Log logger = LogFactory.getLog(this.getClass());

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if (!(authentication instanceof WxSessionToken)) {
            return null;
        }

        WxSessionToken token = (WxSessionToken) authentication;

        if (token.getDetails() != null) {
            return token;
        }
        if (token.getCode() != null) {
            String code2sessionUrl = String.format(WxPath.JSCODE2SESSION, wxAppid, wxSecret, token.getCode());
            logger.info(code2sessionUrl);
            JSONObject result = restTemplate.getForEntity(code2sessionUrl, JSONObject.class).getBody();
            if (result.get("session_key") == null) {
                throw new BadCredentialsException("微信登录失败:" + result.get("errcode") + "," + result.get("errmsg"));
            }
            List<GrantedAuthority> list = new ArrayList<>(1);
            list.add(new SimpleGrantedAuthority(AuthorityName.USER.name()));
            WxSessionToken newToken = new WxSessionToken(list);
            WxSession session = new WxSession((String) result.get("openid"), (String) result.get("session_key"));
            newToken.setDetails(session);
            return newToken;
        }
        return null;
    }

    // 是否可以提供输入类型的认证服务
    @Override
    public boolean supports(Class<?> authentication) {
        return WxSessionToken.class
                .isAssignableFrom(authentication);
    }
}
