package com.msh.tcw.security;

/**
 * Created by weizhongjia on 2018/4/23.
 */
public class WxSession {
    private String openid;

    private String session_key;

    private String unionid;

    public WxSession(String openid, String session_key) {
        this.openid = openid;
        this.session_key = session_key;
    }

    public WxSession() {

    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }


}
