package com.msh.tcw.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.msh.tcw.dto.WxPaymentDTO;
import com.msh.tcw.security.WxSession;
import com.msh.tcw.security.WxSessionToken;
import com.msh.tcw.service.WechatService;
import com.msh.tcw.utils.WxPath;
import com.msh.tcw.utils.WxUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import weixin.popular.api.PayMchAPI;
import weixin.popular.bean.paymch.Unifiedorder;
import weixin.popular.bean.paymch.UnifiedorderResult;
import weixin.popular.util.SignatureUtil;

import java.util.HashMap;
import java.util.Map;

@Service
public class WechatServiceImpl implements WechatService {

    @Value("${wx.appid}")
    private String wxAppid;
    @Value("${wx.secret}")
    private String wxSecret;
    @Value("${wx.mch.id}")
    private String wxMchId;
    @Value("${wx.notify.url}")
    private String notifyUrl;
    @Value("${wx.pay.key}")
    private String key;
    @Autowired
    RestTemplate restTemplate;
    private static final String DEVICE_INFO = "XCX";
    private static final String SIGN_TYPE = "MD5";
    private static final String BODY = "慢生活-礼品";
    private static final String TRADE_TYPE = "JSAPI";

    private static final String APPID_KEY = "paySign";
    private static final String NONCESTR_KEY = "nonceStr";
    private static final String PACKAGE_KEY = "package";
    private static final String SIGNTYPE_KEY = "signType";
    private static final String TIMESTAMP_KEY = "timeStamp";



    @Override
    public JSONObject loginByCode(String code) {
        String code2sessionUrl = String.format(WxPath.JSCODE2SESSION, wxAppid, wxSecret, code);
        JSONObject result = restTemplate.getForEntity(code2sessionUrl, JSONObject.class).getBody();
        if (result.get("session_key") == null) {
            throw new BadCredentialsException("微信登录失败:" + result.get("errcode") + "," + result.get("errmsg"));
        }
        return result;
    }

    @Override
    public UnifiedorderResult payUnifiedorder(int money, String ip, String outTradeNo) {
        WxSessionToken sessionToken = (WxSessionToken) SecurityContextHolder.getContext().getAuthentication();
        WxSession session = sessionToken.getDetails();
        Unifiedorder unifiedorder = new Unifiedorder();
        unifiedorder.setOpenid(session.getOpenid());
        unifiedorder.setAppid(wxAppid);
        unifiedorder.setMch_id(wxMchId);
        unifiedorder.setDevice_info(DEVICE_INFO);
        unifiedorder.setNonce_str(WxUtils.getRandomStringByLength(32));
        unifiedorder.setSign_type(SIGN_TYPE);
        unifiedorder.setBody(BODY);
        unifiedorder.setOut_trade_no(outTradeNo);
        unifiedorder.setTotal_fee(String.valueOf(money));
        unifiedorder.setSpbill_create_ip(ip);
        unifiedorder.setNotify_url(notifyUrl);
        unifiedorder.setTrade_type(TRADE_TYPE);
        return PayMchAPI.payUnifiedorder(unifiedorder, key);
    }

    @Override
    public WxPaymentDTO genWxPaymentDTO(UnifiedorderResult result) {
        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
        Map<String, String> map = new HashMap<>();
        map.put(APPID_KEY, result.getAppid());
        map.put(NONCESTR_KEY, result.getNonce_str());
        map.put(PACKAGE_KEY, "prepay_id=" + result.getPrepay_id());
        map.put(SIGNTYPE_KEY, SIGN_TYPE);
        map.put(TIMESTAMP_KEY, timeStamp);
        String sign = SignatureUtil.generateSign(map, SIGN_TYPE, key);
        return new WxPaymentDTO(sign, timeStamp, result.getNonce_str(), map.get(PACKAGE_KEY), SIGN_TYPE);
    }

}
