package com.msh.tcw.service;

import weixin.popular.bean.paymch.UnifiedorderResult;

public interface OrderService {
    UnifiedorderResult createGiftOrder(int giftId, int number, String ip, int roomId);
}
