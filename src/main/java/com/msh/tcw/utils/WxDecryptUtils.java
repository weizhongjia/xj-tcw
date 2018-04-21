package com.msh.tcw.utils;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;

/**
 * Created by weizhongjia on 2017/12/27.
 */
public class WxDecryptUtils {

    private static boolean initialized = false;

    public static String decrypt(String content, String key, String iv) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        initialize();
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.decodeBase64(key), "AES");
        AlgorithmParameterSpec paramSpec = new IvParameterSpec(Base64.decodeBase64(iv));
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, paramSpec);
        byte[] result = cipher.doFinal(Base64.decodeBase64(content));
        return new String(result, "UTF-8");
    }

    private static void initialize(){
        if (initialized){
            return;
        }
        Security.addProvider(new BouncyCastleProvider());
        initialized = true;
    }
}
