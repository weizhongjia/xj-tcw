package com.msh.tcw.dto;

import lombok.Data;

@Data
public class AliyunUploadFormData {
    private String ossAccessKeyId;
    private String policy;
    private String signature;
    private String endPoint;
    private long expire;

    public AliyunUploadFormData(String ossAccessKeyId, String policy, String signature, String endPoint, long expire) {
        this.ossAccessKeyId = ossAccessKeyId;
        this.policy = policy;
        this.signature = signature;
        this.endPoint = endPoint;
        this.expire = expire;
    }
}
