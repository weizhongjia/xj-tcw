package com.msh.tcw.dto;


import com.msh.tcw.domain.GiftMessageDetail;
import com.msh.tcw.domain.Message;
import com.msh.tcw.domain.Order;
import com.msh.tcw.domain.WxUser;
import com.msh.tcw.domain.enums.ShowtimeType;
import lombok.Data;

import static com.msh.tcw.domain.enums.MessageType.GIFT;

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
        Message message = messageDTO.getMessage();
        WxUser user = messageDTO.getUser();
        this.id = message.getId();
        this.openid = user.getOpenid();
        this.avatar = user.getAvatarurl();
        this.content = message.getDetail();
        this.createtime = message.getSendTime();
        this.nickname = user.getNickname();
        this.sex = user.getGender()?"1":"0";
        this.type = message.getType().toString().toLowerCase();
        Order detail = message.getOrderDetail();
        switch (message.getType()) {
            case GIFT:
                this.extend_params = new GiftExtendParams(detail);
                this.bp_time = String.valueOf(detail.getCostTime());
                this.content = detail.getBlessing();
                break;
            case SHOWTIME:
                this.type = "bp";
                this.bp_time = String.valueOf(detail.getCostTime());
                if (detail.getShowtimeType().equals(ShowtimeType.IMAGE)) {
                    this.image = detail.getShowtimeSrc();
                    this.extend_params = new ShowtimeExtendParams();
                } else if (ShowtimeType.VIDEO.equals(detail.getShowtimeType())) {
                    ShowtimeExtendParams params = new ShowtimeExtendParams();
                    params.setVideo(detail.getShowtimeSrc());
                    this.extend_params = params;
                }
                this.content = detail.getBlessing();
            default:
                break;
        }
    }
}
