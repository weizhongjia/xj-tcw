package com.msh.tcw.service.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.msh.tcw.core.ServiceException;
import com.msh.tcw.dto.AliyunToken;
import com.msh.tcw.service.AliyunSTSService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class AliyunSTSSTSServiceImpl implements AliyunSTSService {

    private DefaultAcsClient acsClient;
    @Value("${aliyun.sts.region}")
    private String REGION;
    @Value("${aliyun.access.key}")
    private String accessKeyId;
    @Value("${aliyun.access.secret}")
    private String accessKeySecret;

    @PostConstruct
    public void init() {
        IClientProfile profile = DefaultProfile.getProfile(REGION, accessKeyId, accessKeySecret);
        acsClient = new DefaultAcsClient(profile);
    }


    @Override
    public AliyunToken genToken(String user) {
        final AssumeRoleRequest request = new AssumeRoleRequest();
        request.setMethod(MethodType.POST);
        request.setProtocol(ProtocolType.HTTPS);
        request.setRoleArn("acs:ram::1459513109952723:role/oss");
        request.setRoleSessionName(user);
        final AssumeRoleResponse response;
        try {
            response = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            throw new ServiceException("获取阿里云临时token报错", e);
        }
        AssumeRoleResponse.Credentials credentials = response.getCredentials();
        return new AliyunToken(credentials.getSecurityToken(), credentials.getExpiration());
    }
}
