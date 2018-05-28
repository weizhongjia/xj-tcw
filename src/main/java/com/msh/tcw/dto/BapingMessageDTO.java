package com.msh.tcw.dto;

import com.msh.tcw.model.Gift;
import com.msh.tcw.model.GiftMessageDetail;
import lombok.Data;

@Data
public class BapingMessageDTO {
    private Integer id;
    private String rid;
    private String is_admin= "0";
    private String openid;
    private String nickname;
    private String avatar;
    private String sex;
    private String isshow= "1";
    private String type= "text";
    private String content= "新婚快乐！！！";
    private String image= "";
    private String fee= "0.00";
    private String send_nums= "0";
    private String hb_detail= "";
    private String bp_time= "0";
    private String forwho= "";
    private ExtendParams extend_params;
    private String is_pay= "1";
    private String is_del= "0";
    private String del_user= "";
    private long createtime;
    private String back_redpack= "0";
    private String theme= "";
    private String songli_fans_fee= "0.00";
    private String had_save= "0";
    private String had_show= "1";
    private String had_resaved= "0";

    public BapingMessageDTO() {
    }

    public BapingMessageDTO (MessageDTO messageDTO) {
        this.id = messageDTO.getMessage().getId();
        this.openid = messageDTO.getUser().getOpenid();
        this.avatar = messageDTO.getUser().getAvatarurl();
        this.content = messageDTO.getMessage().getDetail();
        this.createtime = messageDTO.getMessage().getSendTime();
        this.nickname = messageDTO.getUser().getNickname();
        this.sex = messageDTO.getUser().getGender()?"1":"0";
        this.type = messageDTO.getMessage().getType().toString().toLowerCase();
        switch (messageDTO.getMessage().getType()) {
            case GIFT:
                GiftMessageDetail detail = messageDTO.getMessage().getGiftMessageDetail();
                this.extend_params = new GiftExtendParams(detail);
                break;
            default:
                break;
        }
    }
}
