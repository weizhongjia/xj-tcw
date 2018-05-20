package com.msh.tcw.service;

import com.msh.tcw.dto.AliyunToken;
import com.msh.tcw.dto.AliyunUploadFormData;

/**
 * Created by weizhongjia on 2018/5/19.
 */
public interface AliyunSTSService {
    AliyunToken genToken(String user);
}
