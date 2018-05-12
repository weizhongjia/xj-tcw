package com.msh.tcw.dto;


import com.msh.tcw.model.WxUser;

public class WxUserDTO {

    private String openId;

    private String nickName;

    private Boolean gender;

    private String language;

    private String city;

    private String province;

    private String country;

    private String avatarUrl;

    private WxUserWatermark watermark;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public WxUserWatermark getWatermark() {
        return watermark;
    }

    public void setWatermark(WxUserWatermark watermark) {
        this.watermark = watermark;
    }

    public WxUser instance(){
        WxUser user = new WxUser();
        user.setOpenid(openId);
        user.setTimestamp(watermark.getTimestamp());
        user.setAppid(watermark.getAppid());
        user.setAvatarurl(avatarUrl);
        user.setCity(city);
        user.setCountry(country);
        user.setProvince(province);
        user.setGender(gender);
        user.setLanguage(language);
        user.setNickname(nickName);
        return user;
    }
}

