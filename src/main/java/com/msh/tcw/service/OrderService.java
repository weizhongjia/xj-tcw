package com.msh.tcw.service;

import weixin.popular.bean.paymch.UnifiedorderResult;

public interface OrderService {
    UnifiedorderResult createGiftOrder(int giftId, int number, String ip, int roomId);
    UnifiedorderResult createRedpackOrder(int money, int number, String ip, int roomId);
    int getRedpackOrder(int redpackId);
}
