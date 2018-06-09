package com.msh.tcw.dto;

import com.msh.tcw.domain.Order;
import lombok.Data;

@Data
public class RedpackOrderDTO {
    private Order redpack;
    private WxPaymentDTO paymentDTO;

    public RedpackOrderDTO(Order redpack, WxPaymentDTO paymentDTO) {
        this.redpack = redpack;
        this.paymentDTO = paymentDTO;
    }
}
