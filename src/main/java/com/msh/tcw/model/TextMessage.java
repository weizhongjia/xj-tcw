package com.msh.tcw.model;

/**
 * Created by weizhongjia on 2018/5/6.
 */
public class TextMessage extends Message{
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
