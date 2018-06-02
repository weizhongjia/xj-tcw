package com.msh.tcw.dto;

import com.msh.tcw.model.GiftOrder;

public class GiftOrderDTO {
    private GiftOrder giftOrder;
    private WxPaymentDTO paymentDTO;

    public GiftOrderDTO(GiftOrder giftOrder, WxPaymentDTO paymentDTO) {
        this.giftOrder = giftOrder;
        this.paymentDTO = paymentDTO;
    }
}
