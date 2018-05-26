package com.msh.tcw.utils;

import java.util.Random;

/**
 * Created by weizhongjia on 2018/5/24.
 */
public class WxUtils {
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static String getOrderId() {
        long timeStamp = System.currentTimeMillis();
        Random random = new Random();
        return String.valueOf(timeStamp) + String.valueOf(random.nextInt(1000000));
    }
}
