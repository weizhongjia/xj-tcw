package com.msh.tcw.dto;

import com.msh.tcw.dao.pojo.RedpackDO;
import lombok.Data;

@Data
public class RedpackOrderDTO {
    private RedpackDO redpack;
    private WxPaymentDTO paymentDTO;

    public RedpackOrderDTO(RedpackDO redpack, WxPaymentDTO paymentDTO) {
        this.redpack = redpack;
        this.paymentDTO = paymentDTO;
    }
}
