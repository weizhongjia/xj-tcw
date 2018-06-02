package com.msh.tcw.service;

import com.msh.tcw.dao.pojo.RedpackDO;
import com.msh.tcw.dto.RedpackDTO;
import com.msh.tcw.model.GiftOrder;
import weixin.popular.bean.paymch.UnifiedorderResult;

import java.util.List;

public interface OrderService {
    GiftOrder createGiftOrder(int giftId, int number, int roomId);
    RedpackDO createRedpackOrder(int money, int number, int roomId);
    int getRedpackPosition(int redpackId);
    List<RedpackDTO> openRedpack(int redpackId, int positionId);
    UnifiedorderResult createWechatUnifiedOrder(int money, String ip, String outTradeNo);
}
