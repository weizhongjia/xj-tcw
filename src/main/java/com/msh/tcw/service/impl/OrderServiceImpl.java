package com.msh.tcw.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.msh.tcw.dao.*;
import com.msh.tcw.dao.pojo.*;
import com.msh.tcw.dto.RedpackDTO;
import com.msh.tcw.model.Gift;
import com.msh.tcw.model.GiftOrder;
import com.msh.tcw.model.RedpackStatus;
import com.msh.tcw.model.WxOrderStatus;
import com.msh.tcw.security.WxSession;
import com.msh.tcw.security.WxSessionToken;
import com.msh.tcw.service.OrderService;
import com.msh.tcw.service.WechatService;
import com.msh.tcw.utils.WxUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import weixin.popular.bean.paymch.UnifiedorderResult;

import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService{

    @Autowired
    private WechatService wechatService;
    @Autowired
    private WxOrderMapper wxOrderMapper;
    @Autowired
    private GiftOrderMapper giftOrderMapper;
    @Autowired
    private GiftMapper giftMapper;
    @Autowired
    private RedpackMapper redpackMapper;
    @Autowired
    private RedpackSendListMapper redpackSendListMapper;
    @Autowired
    private RedpackSendLockMapper redpackSendLockMapper;

    @Override
    public GiftOrder createGiftOrder(int giftId, int number, int roomId) {
        WxSessionToken sessionToken = (WxSessionToken) SecurityContextHolder.getContext().getAuthentication();
        WxSession session = sessionToken.getDetails();
        Gift gift = giftMapper.selectById(giftId);
        String outTradeNo = WxUtils.getOrderId();
        GiftOrderDO giftOrderDO = new GiftOrderDO(outTradeNo, session.getOpenid(), giftId, gift.getPrice(), number, roomId, number * gift.getCostTime());
        giftOrderMapper.insert(giftOrderDO);
        return new GiftOrder(giftOrderDO);
    }



    @Override
    public RedpackDO createRedpackOrder(int money, int number, int roomId) {
        WxSessionToken sessionToken = (WxSessionToken) SecurityContextHolder.getContext().getAuthentication();
        WxSession session = sessionToken.getDetails();
        String outTradeNo = WxUtils.getOrderId();
        RedpackDO redpackDO = new RedpackDO(money, number, roomId, session.getOpenid(), outTradeNo);
        redpackMapper.insert(redpackDO);
        RedPackage redPackage = new RedPackage(number, (double)money / 100);
        List<RedpackSendListDO> redpackSendListDOList = new ArrayList<>(number);
        int i = 0;
        while (redPackage.remainSize != 0) {
            int redpackMoney = (int)(getRandomMoney(redPackage) * 100);
            RedpackSendListDO redpackSendListDO = new RedpackSendListDO(redpackDO.getId(), redpackMoney, RedpackStatus.CREATED, i++);
            redpackSendListDOList.add(redpackSendListDO);
        }
        redpackSendListMapper.insertBatch(redpackSendListDOList);
        return redpackDO;
    }

    @Override
    public int getRedpackPosition(int redpackId){
        WxSessionToken sessionToken = (WxSessionToken) SecurityContextHolder.getContext().getAuthentication();
        WxSession session = sessionToken.getDetails();
        RedpackSendLockDO sendLockDO = new RedpackSendLockDO(redpackId, session.getOpenid());
        try {
            redpackSendLockMapper.insert(sendLockDO);
        } catch (Exception e) {
            log.error("抢红包失败", e);
            return -1;
        }
        return redpackSendLockMapper.selectCount(new EntityWrapper<RedpackSendLockDO>().eq("redpack_id", redpackId).gt("id", sendLockDO.getId()));
    }

    @Override
    public List<RedpackDTO> openRedpack(int redpackId, int positionId) {
        RedpackSendListDO redpackSendListDO = new RedpackSendListDO();
        WxSessionToken sessionToken = (WxSessionToken) SecurityContextHolder.getContext().getAuthentication();
        WxSession session = sessionToken.getDetails();
        redpackSendListDO.setOpenid(session.getOpenid());
        redpackSendListDO.setAcceptTime(System.currentTimeMillis());
        redpackSendListDO.setStatus(RedpackStatus.ACCEPTED);
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("redpack_position", positionId);
        queryParam.put("redpack_id", redpackId);
        queryParam.put("status", RedpackStatus.CREATED);
        int num =redpackSendListMapper.update(redpackSendListDO, new EntityWrapper<RedpackSendListDO>().allEq(queryParam));
        if(num != 1) {
            log.error("抽红包数据出现问题，需要分析！！！redpackId:{},positionId:{},openId:{}", redpackId, positionId, session.getOpenid());
        }
        List<RedpackSendListDO> sendListDOs =  redpackSendListMapper.selectRedpacksAccepted(redpackId, RedpackStatus.ACCEPTED);
        List<RedpackDTO> redpackDTOs = new ArrayList<>(sendListDOs.size());
        for (RedpackSendListDO sendListDO : sendListDOs) {
            RedpackDTO redpackDTO = new RedpackDTO(sendListDO);
            redpackDTOs.add(redpackDTO);
        }
        return redpackDTOs;
    }

    @Override
    public UnifiedorderResult createWechatUnifiedOrder(int money, String ip, String outTradeNo) {
        WxSessionToken sessionToken = (WxSessionToken) SecurityContextHolder.getContext().getAuthentication();
        WxSession session = sessionToken.getDetails();
        WxOrderDO wxOrderDO = new WxOrderDO(outTradeNo, session.getOpenid(), WxOrderStatus.CREATED.toString(), money);
        wxOrderMapper.insert(wxOrderDO);
        return wechatService.payUnifiedorder(money, ip, outTradeNo);
    }

    private static double getRandomMoney(RedPackage _redPackage) {
        // remainSize 剩余的红包数量
        // remainMoney 剩余的钱
        if (_redPackage.remainSize == 1) {
            _redPackage.remainSize--;
            return (double) Math.round(_redPackage.remainMoney * 100) / 100;
        }
        Random r     = new Random();
        double min   = 0.01; //
        double max   = _redPackage.remainMoney / _redPackage.remainSize * 2;
        double money = r.nextDouble() * max;
        money = money <= min ? 0.01: money;
        money = Math.floor(money * 100) / 100;
        _redPackage.remainSize--;
        _redPackage.remainMoney -= money;
        return money;
    }

    public static void main(String[] args) {
        RedPackage redPackage = new RedPackage(10, 0.1);
        for(int i = 0; i < 10; i++){
            System.out.format("%.2f\n",getRandomMoney(redPackage));
        }
    }

    private static class RedPackage {
        int remainSize;
        double remainMoney;

        public RedPackage(int remainSize, double remainMoney) {
            this.remainSize = remainSize;
            this.remainMoney = remainMoney;
        }
    }
}
