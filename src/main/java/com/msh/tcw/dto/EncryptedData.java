package com.msh.tcw.dto;

/**
 * Created by weizhongjia on 2017/12/28.
 */
public class EncryptedData {

    private String encryptedData;

    private String iv;

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }
}
