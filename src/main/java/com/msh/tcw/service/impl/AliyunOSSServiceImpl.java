package com.msh.tcw.service.impl;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.PolicyConditions;
import com.msh.tcw.core.ServiceException;
import com.msh.tcw.dto.AliyunUploadFormData;
import com.msh.tcw.service.AliyunOSSService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class AliyunOSSServiceImpl implements AliyunOSSService {

    private OSSClient client;
    @Value("${aliyun.endpoint}")
    private String endpoint;
    @Value("${aliyun.access.key}")
    private String accessKeyId;
    @Value("${aliyun.access.secret}")
    private String accessSecret;

    @PostConstruct
    public void init() {
        ClientConfiguration conf = new ClientConfiguration();
        conf.setSupportCname(true);
        client = new OSSClient(endpoint, accessKeyId, accessSecret, conf);
    }

    @Override
    public AliyunUploadFormData genFormData() {
        long expireTime = 30;
        long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
        Date expiration = new Date(expireEndTime);
        PolicyConditions policyConds = new PolicyConditions();
        policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);

        String postPolicy = client.generatePostPolicy(expiration, policyConds);
        byte[] binaryData = new byte[0];
        try {
            binaryData = postPolicy.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new ServiceException("oss policy 解码失败", e);
        }
        String encodedPolicy = BinaryUtil.toBase64String(binaryData);
        String postSignature = client.calculatePostSignature(postPolicy);
        AliyunUploadFormData formData = new AliyunUploadFormData(accessKeyId, encodedPolicy, postSignature, endpoint, expireEndTime / 1000);
        return formData;
    }

}
