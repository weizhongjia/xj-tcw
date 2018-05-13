package com.msh.tcw.model.MessageDetail;

import lombok.Data;

@Data
public class TextDetail extends MessageDetail{
    private String text;

    public TextDetail(String detail) {
        super(detail);
        this.text = detail;
    }

    @Override
    public String toDetail() {
        return text;
    }


}
