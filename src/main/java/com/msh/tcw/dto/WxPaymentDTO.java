package com.msh.tcw.dto;

import lombok.Data;

@Data
public class WxPaymentDTO {
    private String paySign;
    private String timeStamp;
    private String nonceStr;
    private String ppackage;
    private String signType;

    public WxPaymentDTO(String paySign, String timeStamp, String nonceStr, String ppackage, String signType) {
        this.paySign = paySign;
        this.timeStamp = timeStamp;
        this.nonceStr = nonceStr;
        this.ppackage = ppackage;
        this.signType = signType;
    }
}
