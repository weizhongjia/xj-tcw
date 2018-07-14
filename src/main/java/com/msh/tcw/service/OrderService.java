package com.msh.tcw.service;


import com.msh.tcw.domain.Order;
import com.msh.tcw.domain.enums.ShowtimeType;
import com.msh.tcw.dto.RedpackDTO;
import com.msh.tcw.dto.VideoDTO;
import weixin.popular.bean.paymch.UnifiedorderResult;

import java.util.List;

public interface OrderService {
    Order createGiftOrder(int giftId, int number, int roomId, String blessing, String gif, String avatar, String name);
    Order createRedpackOrder(int money, int number, int roomId, String blessing);
    Order createShowtimeOrder(int money, int time, int roomId, String blessing, ShowtimeType type, String src);
    int getRedpackPosition(int redpackId);
    List<RedpackDTO> openRedpack(int redpackId, int positionId);
    UnifiedorderResult createWechatUnifiedOrder(int money, String ip, String outTradeNo);
    List<VideoDTO> getHistoryVideos(int orderId, int pageSize);
    int getUserAccount();
}
