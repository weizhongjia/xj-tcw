package com.msh.tcw.dto;


import com.msh.tcw.domain.Order;
import lombok.Data;

@Data
public class GiftOrderDTO {
    private Order giftOrder;
    private WxPaymentDTO paymentDTO;

    public GiftOrderDTO(Order giftOrder, WxPaymentDTO paymentDTO) {
        this.giftOrder = giftOrder;
        this.paymentDTO = paymentDTO;
    }
}
