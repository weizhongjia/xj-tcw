package com.msh.tcw.service.impl;

import com.msh.tcw.dao.GiftMapper;
import com.msh.tcw.dao.GiftOrderMapper;
import com.msh.tcw.dao.WxOrderMapper;
import com.msh.tcw.dao.pojo.GiftOrderDO;
import com.msh.tcw.dao.pojo.WxOrderDO;
import com.msh.tcw.model.Gift;
import com.msh.tcw.model.WxOrderStatus;
import com.msh.tcw.security.WxSession;
import com.msh.tcw.security.WxSessionToken;
import com.msh.tcw.service.OrderService;
import com.msh.tcw.service.WechatService;
import com.msh.tcw.utils.WxUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import weixin.popular.bean.paymch.UnifiedorderResult;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private WechatService wechatService;
    @Autowired
    private WxOrderMapper wxOrderMapper;
    @Autowired
    private GiftOrderMapper giftOrderMapper;
    @Autowired
    private GiftMapper giftMapper;

    @Override
    public UnifiedorderResult createGiftOrder(int giftId, int number, String ip, int roomId) {
        WxSessionToken sessionToken = (WxSessionToken) SecurityContextHolder.getContext().getAuthentication();
        WxSession session = sessionToken.getDetails();
        Gift gift = giftMapper.selectById(giftId);
        String outTradeNo = WxUtils.getOrderId();
        GiftOrderDO giftOrderDO = new GiftOrderDO(outTradeNo, session.getOpenid(), giftId, gift.getPrice(), number, roomId, number * gift.getCostTime());
        giftOrderMapper.insert(giftOrderDO);
        int money = gift.getPrice() * number;
        WxOrderDO wxOrderDO = new WxOrderDO(outTradeNo, session.getOpenid(), WxOrderStatus.CREATED.toString(), money);
        wxOrderMapper.insert(wxOrderDO);
        return wechatService.payUnifiedorder(money, ip, outTradeNo);
    }
}
