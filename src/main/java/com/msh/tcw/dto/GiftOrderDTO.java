package com.msh.tcw.dto;


import com.msh.tcw.domain.Order;

public class GiftOrderDTO {
    private Order giftOrder;
    private WxPaymentDTO paymentDTO;

    public GiftOrderDTO(Order giftOrder, WxPaymentDTO paymentDTO) {
        this.giftOrder = giftOrder;
        this.paymentDTO = paymentDTO;
    }
}
