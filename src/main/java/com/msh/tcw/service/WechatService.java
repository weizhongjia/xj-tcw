package com.msh.tcw.service;

import com.alibaba.fastjson.JSONObject;
import com.msh.tcw.dto.WxPaymentDTO;
import weixin.popular.bean.paymch.UnifiedorderResult;

/**
 * Created by weizhongjia on 2018/5/24.
 */
public interface WechatService {

    JSONObject loginByCode(String code);

    UnifiedorderResult payUnifiedorder(int money, String ip, String outTradeNo);

    WxPaymentDTO genWxPaymentDTO(UnifiedorderResult result);

}
