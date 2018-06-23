package com.msh.tcw.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.msh.tcw.dao.*;
import com.msh.tcw.domain.*;
import com.msh.tcw.domain.enums.OrderType;
import com.msh.tcw.domain.enums.RedpackStatus;
import com.msh.tcw.domain.enums.WxOrderStatus;
import com.msh.tcw.dto.RedpackDTO;
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

import java.util.*;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService{

    @Autowired
    private WechatService wechatService;
    @Autowired
    private WxOrderMapper wxOrderMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private GiftMapper giftMapper;
    @Autowired
    private RedpackSendHistoryMapper redpackSendHistoryMapper;
    @Autowired
    private RedpackSendLockMapper redpackSendLockMapper;

    @Override
    public Order createGiftOrder(int giftId, int number, int roomId) {
        Gift gift = giftMapper.selectById(giftId);
        Order order = new Order(OrderType.GIFT, giftId, gift.getPrice(), gift.getCostTime() * number, number, gift.getPrice() * number, roomId);
        createOrder(order);
        return order;
    }



    @Override
    public Order createRedpackOrder(int money, int number, int roomId) {
        Order order = new Order(OrderType.REDPACK, null, null, null, number, money, roomId);
        createOrder(order);
        RedPackage redPackage = new RedPackage(number, (double)money / 100);
        List<RedpackSendHistory> redpackSendListDOList = new ArrayList<>(number);
        int i = 0;
        while (redPackage.remainSize != 0) {
            int redpackMoney = (int)(getRandomMoney(redPackage) * 100);
            RedpackSendHistory redpackSendListDO = new RedpackSendHistory(order.getId(), redpackMoney, RedpackStatus.CREATED, i++);
            redpackSendListDOList.add(redpackSendListDO);
        }
        redpackSendHistoryMapper.insertBatch(redpackSendListDOList);
        return order;
    }

    @Override
    public Order createShowtimeOrder(int money, int time, int roomId) {
        Order order = new Order(OrderType.SHOWTIME, null, null, time, null, money, roomId);
        createOrder(order);
        return order;
    }

    private void createOrder(Order order){
        WxSessionToken sessionToken = (WxSessionToken) SecurityContextHolder.getContext().getAuthentication();
        WxSession session = sessionToken.getDetails();
        String outTradeNo = WxUtils.getOrderId();
        order.setOpenid(session.getOpenid());
        order.setOutTradeNo(outTradeNo);
        orderMapper.insert(order);
    }

    @Override
    public int getRedpackPosition(int redpackId){
        WxSessionToken sessionToken = (WxSessionToken) SecurityContextHolder.getContext().getAuthentication();
        WxSession session = sessionToken.getDetails();
        RedpackSendLock sendLockDO = new RedpackSendLock(redpackId, session.getOpenid());
        try {
            redpackSendLockMapper.insert(sendLockDO);
        } catch (Exception e) {
            log.error("抢红包失败", e);
            return -1;
        }
        return redpackSendLockMapper.selectCount(new EntityWrapper<RedpackSendLock>().eq("redpack_id", redpackId).gt("id", sendLockDO.getId()));
    }

    @Override
    public List<RedpackDTO> openRedpack(int redpackId, int positionId) {
        RedpackSendHistory redpackSendListDO = new RedpackSendHistory();
        WxSessionToken sessionToken = (WxSessionToken) SecurityContextHolder.getContext().getAuthentication();
        WxSession session = sessionToken.getDetails();
        redpackSendListDO.setOpenid(session.getOpenid());
        redpackSendListDO.setAcceptTime(System.currentTimeMillis());
        redpackSendListDO.setStatus(RedpackStatus.ACCEPTED);
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("redpack_position", positionId);
        queryParam.put("redpack_id", redpackId);
        queryParam.put("status", RedpackStatus.CREATED);
        int num = redpackSendHistoryMapper.update(redpackSendListDO, new EntityWrapper<RedpackSendHistory>().allEq(queryParam));
        if(num != 1) {
            log.error("抽红包数据出现问题，需要分析！！！redpackId:{},positionId:{},openId:{}", redpackId, positionId, session.getOpenid());
        }
        List<RedpackSendHistory> sendListDOs =  redpackSendHistoryMapper.selectRedpacksAccepted(redpackId, RedpackStatus.ACCEPTED);
        List<RedpackDTO> redpackDTOs = new ArrayList<>(sendListDOs.size());
        for (RedpackSendHistory sendListDO : sendListDOs) {
            RedpackDTO redpackDTO = new RedpackDTO(sendListDO);
            redpackDTOs.add(redpackDTO);
        }
        return redpackDTOs;
    }

    @Override
    public UnifiedorderResult createWechatUnifiedOrder(int money, String ip, String outTradeNo) {
        WxSessionToken sessionToken = (WxSessionToken) SecurityContextHolder.getContext().getAuthentication();
        WxSession session = sessionToken.getDetails();
        WxOrder wxOrderDO = new WxOrder(outTradeNo, session.getOpenid(), WxOrderStatus.CREATED.toString(), money);
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
