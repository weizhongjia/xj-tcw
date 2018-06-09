package com.msh.tcw.service;


import com.msh.tcw.domain.Order;
import com.msh.tcw.dto.RedpackDTO;
import weixin.popular.bean.paymch.UnifiedorderResult;

import java.util.List;

public interface OrderService {
    Order createGiftOrder(int giftId, int number, int roomId);
    Order createRedpackOrder(int money, int number, int roomId);
    int getRedpackPosition(int redpackId);
    List<RedpackDTO> openRedpack(int redpackId, int positionId);
    UnifiedorderResult createWechatUnifiedOrder(int money, String ip, String outTradeNo);
}
