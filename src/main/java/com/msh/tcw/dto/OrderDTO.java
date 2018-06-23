package com.msh.tcw.dto;


import com.msh.tcw.domain.Order;
import lombok.Data;

@Data
public class OrderDTO {
    private Order order;
    private WxPaymentDTO paymentDTO;

    public OrderDTO(Order order, WxPaymentDTO paymentDTO) {
        this.order = order;
        this.paymentDTO = paymentDTO;
    }
}
