package com.msh.tcw.dto;

import com.msh.tcw.model.Message;
import com.msh.tcw.model.WxUser;

/**
 * Created by weizhongjia on 2018/5/6.
 */
public class MessageDTO {
    private Message message;
    private WxUser use;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public WxUser getUse() {
        return use;
    }

    public void setUse(WxUser use) {
        this.use = use;
    }
}
