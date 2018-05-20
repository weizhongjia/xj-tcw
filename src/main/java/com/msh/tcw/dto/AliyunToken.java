package com.msh.tcw.dto;

import lombok.Data;

@Data
public class AliyunToken {
    private String securityToken;
    private String expireTime;

    public AliyunToken(String securityToken, String expireTime) {
        this.securityToken = securityToken;
        this.expireTime = expireTime;
    }
}
