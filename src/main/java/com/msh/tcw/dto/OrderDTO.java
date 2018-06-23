package com.msh.tcw.dto;


import com.msh.tcw.domain.Order;
import lombok.Data;

@Data
public class OrderDTO {
    private Order giftOrder;
    private WxPaymentDTO paymentDTO;

    public OrderDTO(Order giftOrder, WxPaymentDTO paymentDTO) {
        this.giftOrder = giftOrder;
        this.paymentDTO = paymentDTO;
    }
}
